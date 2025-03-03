package com.example.my_app.Repository.Products;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import com.example.my_app.model.Product.Products_Brands;

@Repository
public interface BrandsRepository extends JpaRepository<Products_Brands, UUID> {
    Optional<Products_Brands> findByBrands(String brands);

}
