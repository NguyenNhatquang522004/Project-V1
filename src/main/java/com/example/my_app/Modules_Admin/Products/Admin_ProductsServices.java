package com.example.my_app.Modules_Admin.Products;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.my_app.Modules_Admin.IServices.IProducts;
import com.example.my_app.Repository.Products.BrandsRepository;
import com.example.my_app.Repository.Products.CategoryRepository;
import com.example.my_app.Repository.Products.ImgRepository;
import com.example.my_app.Repository.Products.ProductRepository;
import com.example.my_app.Repository.Products.SupportsAttribute;
import com.example.my_app.Repository.Products.SupportsRepository;

@Service
public class Admin_ProductsServices implements IProducts {
    @Autowired
    BrandsRepository brandsRepository;
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    ImgRepository imgRepository;
    @Autowired
    ProductRepository productRepository;
    @Autowired
    SupportsAttribute supportsAttribute;
    @Autowired
    SupportsRepository supportsRepository;

}