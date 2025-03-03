package com.example.my_app.Modules_Admin.Products;

import java.util.Optional;

import javax.print.DocFlavor.STRING;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.example.my_app.DTO.Products.ProductsDTO;
import com.example.my_app.DTO.Products.ProductsSupportAttributeDTO;
import com.example.my_app.DTO.Products.ProductsSupportDTO;
import com.example.my_app.Enum.Products.StatusBrandsProducts;
import com.example.my_app.Enum.Products.StatusCategory;

import com.example.my_app.Mapper.Products.ProductMapper;
import com.example.my_app.Mapper.Products.ProductsBrandsMapper;
import com.example.my_app.Mapper.Products.ProductsCategoryMapper;
import com.example.my_app.Mapper.Products.ProductsImgMapper;
import com.example.my_app.Mapper.Products.ProductsSupportsAttributeMapper;
import com.example.my_app.Mapper.Products.ProductsSupportsMapper;
import com.example.my_app.Modules_Admin.IServices.IProducts;
import com.example.my_app.Modules_Admin.Products.Request.RequestAdd;
import com.example.my_app.Repository.CustomRepository.ProductCustom;
import com.example.my_app.Repository.Products.BrandsRepository;
import com.example.my_app.Repository.Products.CategoryRepository;
import com.example.my_app.Repository.Products.ImgRepository;
import com.example.my_app.Repository.Products.ProductRepository;
import com.example.my_app.Repository.Products.SupportsAttributeRepository;
import com.example.my_app.Repository.Products.SupportsRepository;
import com.example.my_app.model.Product.Products;
import com.example.my_app.model.Product.ProductsCategory;
import com.example.my_app.model.Product.Products_Brands;
import com.example.my_app.model.Product.Products_Support_Attribute;
import com.example.my_app.model.Product.Products_Supports;

import jakarta.transaction.Transactional;

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
            ProductsSupportsMapper supportsMapper, ProductCustom productCustom) {
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
    }

    @Transactional
    public boolean addNewProducts(RequestAdd request) throws Exception {
        try {
            Products products = productMapper.toEntity(request.getProductsData());
            handleProductsSupportAndAttribute(request, products);
            System.out.println(StatusCategory.valueOf(request.getCategoryData().getCategory()));
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
    public Optional<ProductsCategory> handleFindOneCategory(String data) throws Exception {
        return categoryRepository.findByCategory(data);
    }

    @Transactional
    public Optional<Products_Brands> handleFindOneBrands(String data) throws Exception {
        return brandsRepository.findByBrands(data);
    }

    @Transactional
    public void handleProductsSupportAndAttribute(RequestAdd request, Products products) throws Exception {
        try {
            for (ProductsSupportDTO value : request.getProductsSupportData()) {
                Products_Supports products_Supports = supportsMapper.toEntity(value);
                productCustom.Products_Product_Supports(products, products_Supports);
                for (ProductsSupportAttributeDTO value1 : value.getProducts_Supports_Products_Support_Attribute()) {
                    Products_Support_Attribute products_Support_Attribute = supportsAttributeMapper.toEntity(value1);
                    productCustom.Product_Supports_Attribute(products_Supports, products_Support_Attribute);
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }

    }

}