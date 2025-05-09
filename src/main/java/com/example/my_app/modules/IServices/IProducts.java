package com.example.my_app.modules.IServices;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.example.my_app.DTO.Products.ProductsImgDTO;
import com.example.my_app.DTO.Products.Products_SupportsDTO;
import com.example.my_app.common.ResponedGlobal;
import com.example.my_app.model.Product.Products;
import com.example.my_app.model.Product.ProductsCategory;
import com.example.my_app.model.Product.Products_Brands;
import com.example.my_app.model.Product.Products_Support_Attribute;
import com.example.my_app.model.Product.Products_Supports;
import com.example.my_app.modules.Products.Request.RequestAdd;
import com.example.my_app.modules.Products.Request.RequestUpdate;

public interface IProducts {
    ResponedGlobal addNewProducts(RequestAdd request) throws Exception;

    boolean UpdateProducts(RequestUpdate request, Products products) throws Exception;

    Optional<ProductsCategory> handleFindOneCategory(String data) throws Exception;

    Optional<Products_Brands> handleFindOneBrands(String data) throws Exception;

    void handleImgProducts(Products products, List<ProductsImgDTO> request) throws Exception;

    void handleProductsSupportAndAttribute(List<Products_SupportsDTO> request, Products products) throws Exception;

    Optional<Products> handleFindOneProducts(UUID request) throws Exception;

    Optional<Products_Supports> handleFindOneProductsSupports(UUID request) throws Exception;

    Optional<Products_Support_Attribute> handleFindOneProductsSupportsAttribute(UUID request) throws Exception;

    boolean handleDeleteProducts(Products request) throws Exception;

    boolean handleDeleteProductsSupports(Products_Supports request) throws Exception;

    boolean handleDeleteProductsSupportsAttribute(Products_Support_Attribute request) throws Exception;
}
