package com.example.my_app.Repository.IRepositoryCustom;

import com.example.my_app.model.Product.Products;
import com.example.my_app.model.Product.Products_Support_Attribute;
import com.example.my_app.model.Product.Products_Supports;
import com.example.my_app.model.Product.Products_img;

public interface IProductCustom {
    public void Helper_Products_Product_Supports(Products products, Products_Supports products_Support)
            throws Exception;

    public void Helper_Product_Supports_Attribute(Products_Supports products_Support,
            Products_Support_Attribute products_Support_Attribute)
            throws Exception;

    public void Helper_Product_Img(Products_img img,
            Products products)
            throws Exception;
}
