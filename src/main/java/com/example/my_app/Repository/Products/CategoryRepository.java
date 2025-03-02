package com.example.my_app.Repository.Products;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.my_app.model.Product.ProductsCategory;

@Repository
public interface CategoryRepository extends JpaRepository<ProductsCategory, UUID> {

}
