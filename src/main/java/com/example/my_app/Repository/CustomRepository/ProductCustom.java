package com.example.my_app.Repository.CustomRepository;

import org.springframework.stereotype.Component;

import com.example.my_app.Repository.IRepositoryCustom.IProductCustom;
import com.example.my_app.model.Product.Products;
import com.example.my_app.model.Product.Products_Support_Attribute;
import com.example.my_app.model.Product.Products_Supports;
import com.example.my_app.model.Product.Products_img;

import jakarta.transaction.Transactional;

@Component
public class ProductCustom implements IProductCustom {

    @Override
    @Transactional
    public void Helper_Products_Product_Supports(Products products, Products_Supports products_Support)
            throws Exception {

        try {
            products.getProducts_support().add(products_Support);
            products_Support.setProducts_id(products);

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    @Override
    @Transactional
    public void Helper_Product_Supports_Attribute(Products_Supports products_Support,
            Products_Support_Attribute products_Support_Attribute) throws Exception {

        try {
            products_Support.getProducts_Supports_Products_Support_Attribute().add(products_Support_Attribute);
            products_Support_Attribute.setProducts_Supports_id(products_Support);

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    @Override
    public void Helper_Product_Img(Products_img img, Products products) throws Exception {
        try {
            products.getProducts_img().add(img);
            img.setProducts_id(products);
        } catch (Exception e) {
            System.out.println(e);
        }

    }

}
