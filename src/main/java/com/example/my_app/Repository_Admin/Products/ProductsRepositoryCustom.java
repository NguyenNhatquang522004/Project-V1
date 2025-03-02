package com.example.my_app.Repository_Admin.Products;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class ProductsRepositoryCustom implements IProducts {

    @Autowired
    RepositoryProducts repositoryProducts;

}
