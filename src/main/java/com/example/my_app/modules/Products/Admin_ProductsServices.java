package com.example.my_app.modules.Products;

import java.util.ArrayList;
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
import org.springframework.transaction.annotation.Propagation;
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
import com.example.my_app.common.ResponedGlobal;
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

import lombok.val;

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
    public ResponedGlobal addNewProducts(RequestAdd request) throws Exception {
        try {
            Products products = productMapper.toEntity(request.getProductsData());
            handleProductsSupportAndAttribute(request.getProductsSupportData(), products);
            handleImgProducts(products, request.getUrlData());
            Optional<ProductsCategory> searchCategory = handleFindOneCategory(request.getCategoryData().getCategory());
            if (searchCategory.isEmpty()) {
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                return ResponedGlobal.builder().data("").code("0").messages("lỗi khong tim thấy cate").build();
            }

            Optional<Products_Brands> searchBrands = handleFindOneBrands(request.getBrandsData().getBrands());
            if (searchBrands.isEmpty()) {
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                return ResponedGlobal.builder().data("").code("0").messages("lỗi khong tim thấy brad").build();
            }
            searchCategory.get().getProducts().add(products);
            searchBrands.get().getProducts().add(products);
            products.setProducts_Brands_id(searchBrands.get());
            products.setProductsCategory(searchCategory.get());
            productRepository.save(products);
            return ResponedGlobal.builder().data("").code("1").messages("thành công").build();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponedGlobal.builder().data("").code("0").messages(e.toString()).build();
        }
    }

    @Transactional
    public boolean UpdateProducts(RequestUpdate request, Products products) throws Exception {
        try {
            // Xử lý đồng bộ tất cả các task
            handleUpdateProducts(products, request.getProductsData());
            handleUpdateCategory(products, request.getCategoryData());
            handleUpdateBrands(products, request.getBrandsData());
            handleDeleteManySupport(request.getProductsSupportDataDelete(), products);
            handleDeleteimg(products, request.getUrlDataDelete());
            updateProductsSupport(request.getProductsSupportDataUpdate(), products);
            handleProductsSupportAndAttribute(request.getProductsSupportDataAdd(), products);
            handleImgProducts(products, request.getUrlDataNew());

            productRepository.save(products);
            return true;
        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return false;
        }
    }

    @Transactional(propagation = Propagation.NESTED)
    public void handleUpdateProducts(Products products, ProductsDTO request) throws Exception {
        productMapper.updateEntity(products, request);
    }

    @Transactional(propagation = Propagation.NESTED)
    public void handleUpdateCategory(Products products, ProductsCategoryDTO request) throws Exception {
        if (!products.getProductsCategory().getCategory().equals(request.getCategory())) {
            products.getProductsCategory().getProducts().remove(products);
            Optional<ProductsCategory> searchCategory = handleFindOneCategory(request.getCategory());
            if (searchCategory.isEmpty()) {
                throw new RuntimeException("Category not found");
            }
            products.setProductsCategory(searchCategory.get());
        }
    }

    @Transactional(propagation = Propagation.NESTED)
    public void handleUpdateBrands(Products products, ProductsBrandDTO request) throws Exception {
        if (products.getProducts_Brands_id().getBrands() != request.getBrands()) {
            products.getProducts_Brands_id().getProducts().remove(products);
            Optional<Products_Brands> searchBrands = handleFindOneBrands(request.getBrands());
            if (searchBrands.isEmpty()) {
                throw new RuntimeException("Brand not found");
            }
            products.setProducts_Brands_id(searchBrands.get());
        }
    }

    @Transactional(propagation = Propagation.NESTED)
    public void handleDeleteManySupport(List<UUID> request, Products products) throws Exception {
        if (request.size() > 0) {
            List<Products_Supports> searchProducts_Supports = supportsRepository.findAllById(request);
            products.getProducts_support().removeAll(searchProducts_Supports);
        }
    }
    
    @Transactional(propagation = Propagation.NESTED)
    public void handleDeleteimg(Products products, List<UUID> request) throws Exception {
        if (request.size() > 0) {
            List<Products_img> searchImg = imgRepository.findAllById(request);
            products.getProducts_img().removeAll(searchImg);
        }
    }

    @Transactional(propagation = Propagation.NESTED)
    public void updateProductsSupport(List<Products_SupportsDTO> request, Products products) throws Exception {
        for (Products_SupportsDTO value : request) {

            Optional<Products_Supports> searchSupport = products.getProducts_support()
                    .stream()
                    .filter(data -> data.getId().equals(value.getId()))
                    .findFirst();
            if (searchSupport.isEmpty())
                continue;
            supportsMapper.updateEntity(searchSupport.get(), value);
        }
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

    @Transactional(propagation = Propagation.NESTED)
    public void handleImgProductsAsync(Products products, List<ProductsImgDTO> request) throws Exception {
        for (ProductsImgDTO value : request) {
            Products_img products_img = imgMapper.toEntity(value);
            productCustom.Helper_Product_Img(products_img, products);
        }
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

    @Transactional(propagation = Propagation.NESTED)
    public void handleProductsSupportAndAttributeAsync(List<Products_SupportsDTO> request, Products products)
            throws Exception {
        for (Products_SupportsDTO value : request) {
            Products_Supports products_Supports = supportsMapper.toEntity(value);
            productCustom.Helper_Products_Product_Supports(products, products_Supports);
            for (ProductsSupportAttributeDTO value1 : value.getProducts_Supports_Products_Support_Attribute()) {
                Products_Support_Attribute products_Support_Attribute = supportsAttributeMapper.toEntity(value1);
                productCustom.Helper_Product_Supports_Attribute(products_Supports, products_Support_Attribute);
            }
        }
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

    public ResponedGlobal handlePanigation() throws Exception {
        try {

            List<Products> searchProducts = productRepository.findAll();
            List<ResponedProducts> responedProducts = new ArrayList<>();
            for (Products value : searchProducts) {
                List<ProductsImgDTO> setimg = new ArrayList<>();
                System.out.println(value.getId().toString());
                ResponedProducts itemp = new ResponedProducts();
                ProductsDTO item = new ProductsDTO();
                item.setId(value.getId());
                item.setActive(value.getIsActive());
                item.setMaxPrice(value.getMaxPrice());
                item.setMinPrice(value.getMinPrice());
                item.setQuantity(value.getQuantity());
                item.setTitle(value.getTitle());
                item.setTotalBUY(value.getTotalBUY());
                item.setUrl(value.getUrl());
                item.setActive(true);
                ProductsBrandDTO item2 = new ProductsBrandDTO();
                item2.setBrands(value.getProducts_Brands_id().getBrands());
                item2.setId(value.getProducts_Brands_id().getId());
                ProductsCategoryDTO item3 = new ProductsCategoryDTO();
                item3.setId(value.getProductsCategory().getId());
                item3.setCategory(value.getProductsCategory().getCategory());

                for (Products_img value1 : value.getProducts_img()) {
                    ProductsImgDTO item4 = new ProductsImgDTO();
                    item4.setId(value1.getId());
                    item4.setUrl(value1.getUrl());
                    setimg.add(item4);
                }
                for (Products_Supports value2 : value.getProducts_support()) {
                    Products_SupportsDTO item5 = new Products_SupportsDTO();
                    item5.setId(value2.getId());
                    item5.setCodecolor(value2.getCodecolor());
                    item5.setActive(true);
                    item5.setColor(value2.getColor());
                    item5.setUrl(value2.getUrl());
                    Set<ProductsSupportAttributeDTO> productsSupportAttributeDTOs = new HashSet<>();
                    for (Products_Support_Attribute value3 : value2.getProducts_Supports_Products_Support_Attribute()) {
                        ProductsSupportAttributeDTO item6 = new ProductsSupportAttributeDTO();
                        item6.setId(value3.getId());
                        item6.setCostPrice(value3.getCostPrice());
                        item6.setQuantity(value3.getQuantity());
                        item6.setSize(value3.getSize());
                        item6.setSellingPrice(value3.getSellingPrice());
                        productsSupportAttributeDTOs.add(item6);
                    }
                    item5.setProducts_Supports_Products_Support_Attribute(productsSupportAttributeDTOs);
                    itemp.getProductsSupportData().add(item5);
                    itemp.setProductsData(item);
                    itemp.setBrandsData(item2);
                    itemp.setCategoryData(item3);
                    itemp.setUrlData(setimg);

                }
                responedProducts.add(itemp);
            }
            return ResponedGlobal.builder().code("1").data(responedProducts).messages("").build();
        } catch (Exception e) {
            return ResponedGlobal.builder().code("0").data("").messages(e.toString()).build();
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