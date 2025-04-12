package com.example.my_app.modules.Products;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.example.my_app.DTO.Products.ProductsBrandDTO;
import com.example.my_app.DTO.Products.ProductsCategoryDTO;
import com.example.my_app.DTO.Products.ProductsDTO;
import com.example.my_app.DTO.Products.ProductsImgDTO;
import com.example.my_app.DTO.Products.ProductsSupportAttributeDTO;
import com.example.my_app.DTO.Products.Products_SupportsDTO;
import com.example.my_app.Mapper.Products.ProductMapper;
import com.example.my_app.Mapper.Products.ProductsBrandsMapper;
import com.example.my_app.Mapper.Products.ProductsCategoryMapper;
import com.example.my_app.Mapper.Products.ProductsImgMapper;
import com.example.my_app.Mapper.Products.ProductsSupportsAttributeMapper;
import com.example.my_app.Mapper.Products.ProductsSupportsMapper;
import com.example.my_app.Repository.Products.BrandsRepository;
import com.example.my_app.Repository.Products.CategoryRepository;
import com.example.my_app.Repository.Products.ImgRepository;
import com.example.my_app.Repository.Products.ProductRepository;
import com.example.my_app.Repository.Products.ProductRepositoryPanigation;
import com.example.my_app.Repository.Products.SupportsAttributeRepository;
import com.example.my_app.Repository.Products.SupportsRepository;
import com.example.my_app.custom.CustomRepository.ProductCustom;
import com.example.my_app.model.Product.Products;
import com.example.my_app.model.Product.ProductsCategory;
import com.example.my_app.model.Product.Products_Brands;
import com.example.my_app.model.Product.Products_Support_Attribute;
import com.example.my_app.model.Product.Products_Supports;
import com.example.my_app.model.Product.Products_img;
import com.example.my_app.modules.IServices.IProducts;
import com.example.my_app.modules.Products.Request.RequestAdd;
import com.example.my_app.modules.Products.Request.RequestUpdate;
import com.example.my_app.modules.Products.Responed.ResponedProducts;

@Service
public class Admin_ProductsServices implements IProducts {
    private final BrandsRepository brandsRepository;
    private final CategoryRepository categoryRepository;
    private final ImgRepository imgRepository;
    private final ProductRepository productRepository;
    private final SupportsAttributeRepository supportsAttribute;
    private final SupportsRepository supportsRepository;
    private final ProductsBrandsMapper brandsMapper;
    private final ProductsCategoryMapper categoryMapper;
    private final ProductsImgMapper imgMapper;
    private final ProductMapper productMapper;
    private final ProductsSupportsAttributeMapper supportsAttributeMapper;
    private final ProductsSupportsMapper supportsMapper;
    private final ProductCustom productCustom;
    private final ProductRepositoryPanigation productRepositoryPanigation;
    private final com.example.my_app.Repository.Redis.BaseRedis BaseRedis;

    @Autowired
    public Admin_ProductsServices(
            BrandsRepository brandsRepository,
            CategoryRepository categoryRepository,
            ImgRepository imgRepository,
            ProductRepository productRepository,
            SupportsAttributeRepository supportsAttribute,
            SupportsRepository supportsRepository,
            ProductsBrandsMapper brandsMapper,
            ProductsCategoryMapper categoryMapper,
            ProductsImgMapper imgMapper,
            ProductMapper productMapper,
            ProductsSupportsAttributeMapper supportsAttributeMapper,
            ProductsSupportsMapper supportsMapper, ProductCustom productCustom,
            ProductRepositoryPanigation productRepositoryPanigation,
            com.example.my_app.Repository.Redis.BaseRedis BaseRedis) {
        this.brandsRepository = brandsRepository;
        this.categoryRepository = categoryRepository;
        this.imgRepository = imgRepository;
        this.productRepository = productRepository;
        this.supportsAttribute = supportsAttribute;
        this.supportsRepository = supportsRepository;
        this.brandsMapper = brandsMapper;
        this.categoryMapper = categoryMapper;
        this.imgMapper = imgMapper;
        this.productMapper = productMapper;
        this.supportsAttributeMapper = supportsAttributeMapper;
        this.supportsMapper = supportsMapper;
        this.productCustom = productCustom;
        this.productRepositoryPanigation = productRepositoryPanigation;
        this.BaseRedis = BaseRedis;
    }

    @Transactional
    public boolean addNewProducts(RequestAdd request) throws Exception {
        try {
            Products products = productMapper.toEntity(request.getProductsData());
            handleProductsSupportAndAttribute(request.getProductsSupportData(), products);
            handleImgProducts(products, request.getUrlData());
            Optional<ProductsCategory> searchCategory = handleFindOneCategory(request.getCategoryData().getCategory());
            if (searchCategory.isEmpty()) {
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                return false;
            }

            Optional<Products_Brands> searchBrands = handleFindOneBrands(request.getBrandsData().getBrands());
            if (searchBrands.isEmpty()) {
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                return false;
            }
            searchCategory.get().getProducts().add(products);
            searchBrands.get().getProducts().add(products);
            products.setProducts_Brands_id(searchBrands.get());
            products.setProductsCategory(searchCategory.get());
            productRepository.saveAndFlush(products);
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    @Transactional
    public boolean UpdateProducts(RequestUpdate request, Products products) throws Exception {
        try {
            CompletableFuture.allOf(
                    handleUpdateProducts(products, request.getProductsData()),
                    handleUpdateCategory(products, request.getCategoryData()),
                    handleUpdateBrands(products, request.getBrandsData()),
                    handleDeleteManySupport(request.getProductsSupportDataDelete(), products),
                    handleDeleteimg(products, request.getUrlDataDelete()),
                    updateProductsSupport(request.getProductsSupportDataUpdate(), products),
                    handleProductsSupportAndAttributeAsync(request.getProductsSupportDataAdd(), products),
                    handleImgProductsAsync(products, request.getUrlDataNew()))
                    .thenRun(() -> productRepository.saveAndFlush(products))
                    .join();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Async("asyncTaskExecutor")
    @Transactional
    public CompletableFuture<Void> handleUpdateProducts(Products products, ProductsDTO request)
            throws Exception {
        try {
            System.out.println(Thread.currentThread());
            productMapper.updateEntity(products, request);
        } catch (Exception e) {
            System.out.println(e);
        }
        return CompletableFuture.completedFuture(null);
    }

    @Async("asyncTaskExecutor")
    @Transactional
    public CompletableFuture<Void> handleUpdateCategory(Products products, ProductsCategoryDTO request)
            throws Exception {
        try {
            System.out.println(Thread.currentThread());

            if (products.getProductsCategory().getCategory() != request.getCategory()) {
                products.getProductsCategory().getProducts().remove(products);
                Optional<ProductsCategory> searchCategory = handleFindOneCategory(
                        request.getCategory());
                if (searchCategory.isEmpty()) {
                    TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                }
                products.setProductsCategory(searchCategory.get());
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return CompletableFuture.completedFuture(null);
    }

    @Async("asyncTaskExecutor")
    @Transactional
    public CompletableFuture<Void> handleUpdateBrands(Products products, ProductsBrandDTO request) throws Exception {
        try {
            if (products.getProducts_Brands_id().getBrands() != request.getBrands()) {
                products.getProducts_Brands_id().getProducts().remove(products);
                Optional<Products_Brands> searchBrands = handleFindOneBrands(request.getBrands());
                if (searchBrands.isEmpty()) {
                    TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                }
                products.setProducts_Brands_id(searchBrands.get());
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return CompletableFuture.completedFuture(null);
    }

    @Async("asyncTaskExecutor")
    @Transactional
    public CompletableFuture<Void> handleDeleteManySupport(List<UUID> request, Products products) throws Exception {
        try {
            System.out.println(Thread.currentThread());

            if (request.size() > 0) {
                List<Products_Supports> searchProducts_Supports = supportsRepository
                        .findAllById(request);
                products.getProducts_support().removeAll(searchProducts_Supports);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return CompletableFuture.completedFuture(null);
    }

    @Async("asyncTaskExecutor")
    @Transactional
    public CompletableFuture<Void> handleDeleteimg(Products products, List<UUID> request) throws Exception {
        try {
            if (request.size() > 0) {
                List<Products_img> searchImg = imgRepository.findAllById(request);
                products.getProducts_img().removeAll(searchImg);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return CompletableFuture.completedFuture(null);
    }

    @Async("asyncTaskExecutor")
    @Transactional
    public CompletableFuture<Void> updateProductsSupport(List<Products_SupportsDTO> request, Products products)
            throws Exception {
        try {
            for (Products_SupportsDTO value : request) {
                Optional<Products_Supports> searchSupport = products.getProducts_support()
                        .stream()
                        .filter(data -> data.getId().equals(value.getId()))
                        .findFirst();
                if (searchSupport.isEmpty()) {
                    continue;
                }
                supportsMapper.updateEntity(searchSupport.get(), value);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return CompletableFuture.completedFuture(null);
    }

    @Transactional(readOnly = true)
    public Optional<ProductsCategory> handleFindOneCategory(String data) throws Exception {
        return categoryRepository.findByCategory(data);
    }

    @Transactional(readOnly = true)
    public Optional<Products_Brands> handleFindOneBrands(String data) throws Exception {
        return brandsRepository.findByBrands(data);
    }

    public void handleImgProducts(Products products, List<ProductsImgDTO> request) throws Exception {
        try {
            for (ProductsImgDTO value : request) {
                Products_img products_img = imgMapper.toEntity(value);
                productCustom.Helper_Product_Img(products_img, products);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    @Async("asyncTaskExecutor")
    @Transactional
    public CompletableFuture<Void> handleImgProductsAsync(Products products, List<ProductsImgDTO> request)
            throws Exception {
        try {
            for (ProductsImgDTO value : request) {
                Products_img products_img = imgMapper.toEntity(value);
                productCustom.Helper_Product_Img(products_img, products);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return CompletableFuture.completedFuture(null);
    }

    public void handleProductsSupportAndAttribute(List<Products_SupportsDTO> request, Products products)
            throws Exception {
        try {
            for (Products_SupportsDTO value : request) {
                Products_Supports products_Supports = supportsMapper.toEntity(value);

                productCustom.Helper_Products_Product_Supports(products, products_Supports);
                for (ProductsSupportAttributeDTO value1 : value.getProducts_Supports_Products_Support_Attribute()) {
                    Products_Support_Attribute products_Support_Attribute = supportsAttributeMapper.toEntity(value1);
                    productCustom.Helper_Product_Supports_Attribute(products_Supports, products_Support_Attribute);
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }

    }

    @Async("asyncTaskExecutor")
    @Transactional
    public CompletableFuture<Void> handleProductsSupportAndAttributeAsync(List<Products_SupportsDTO> request,
            Products products)
            throws Exception {
        try {
            System.out.println(Thread.currentThread());
            for (Products_SupportsDTO value : request) {
                Products_Supports products_Supports = supportsMapper.toEntity(value);
                productCustom.Helper_Products_Product_Supports(products, products_Supports);
                for (ProductsSupportAttributeDTO value1 : value.getProducts_Supports_Products_Support_Attribute()) {

                    Products_Support_Attribute products_Support_Attribute = supportsAttributeMapper.toEntity(value1);
                    productCustom.Helper_Product_Supports_Attribute(products_Supports, products_Support_Attribute);
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return CompletableFuture.completedFuture(null);
    }

    @Transactional
    public Optional<Products> handleFindOneProducts(UUID request) throws Exception {
        return productRepository.findById(request);
    }

    @Transactional
    public Optional<Products_Supports> handleFindOneProductsSupports(UUID request) throws Exception {
        return supportsRepository.findById(request);
    }

    @Transactional
    public Optional<Products_Support_Attribute> handleFindOneProductsSupportsAttribute(UUID request) throws Exception {
        return supportsAttribute.findById(request);
    }

    @Transactional
    public boolean handleDeleteProducts(Products request) throws Exception {
        try {
            request.getProductsCategory().getProducts().remove(request);
            request.getProducts_Brands_id().getProducts().remove(request);
            productRepository.deleteById(request.getId());
            return true;
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }

    @Transactional
    public boolean handleDeleteProductsSupports(Products_Supports request) throws Exception {
        try {
            request.getProducts_id().getProducts_support().remove(request);
            supportsRepository.deleteById(request.getId());
            return true;
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }

    @Transactional
    public boolean handleDeleteProductsSupportsAttribute(Products_Support_Attribute request) throws Exception {
        try {
            request.getProducts_Supports_id().getProducts_Supports_Products_Support_Attribute().remove(request);
            supportsAttribute.deleteById(request.getId());
            return true;
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }

    @Transactional
    public ResponedProducts handleRenderSpecific(Products products) throws Exception {
        try {
            ProductsDTO productsDTO = productMapper.toDTO(products);
            Set<Products_SupportsDTO> products_supportDTO = Support_Attibute_DTO(
                    products.getProducts_support());
            productsDTO.setProducts_support(products_supportDTO);
            Set<ProductsImgDTO> imgDTOs = productCustom.mapToDTOSet(products.getProducts_img(),
                    imgMapper::toDTO);
            productsDTO.setProducts_img(imgDTOs);

            return new ResponedProducts();
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }

    @Transactional
    public Set<Products_SupportsDTO> Support_Attibute_DTO(Set<Products_Supports> products_Supports)
            throws Exception {
        try {
            Set<Products_SupportsDTO> pSupportsDTOs = new HashSet<>();
            for (Products_Supports value : products_Supports) {
                Products_SupportsDTO data = supportsMapper.toDTO(value);
                for (Products_Support_Attribute products_Support_Attribute : value
                        .getProducts_Supports_Products_Support_Attribute()) {
                    ProductsSupportAttributeDTO productsSupportAttributeDTO = supportsAttributeMapper
                            .toDTO(products_Support_Attribute);
                    data.getProducts_Supports_Products_Support_Attribute().add(productsSupportAttributeDTO);
                    productsSupportAttributeDTO.setProducts_Supports_id(data);
                }
            }
            return pSupportsDTOs;

        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }

    public Page<Products> handlePanigation(int page, int size) throws Exception {
        try {
            Pageable pageRequest = PageRequest.of(page, size);
            return productRepositoryPanigation.findAll(pageRequest);
        } catch (Exception e) {
            return null;
        }
    }

    public void test() {
        List<Products> lproducts = productRepository.findAll();
        for (Products value : lproducts) {
            BaseRedis.set(value.getId().toString(), value.getProducts_support());
            break;
        }

    }
}