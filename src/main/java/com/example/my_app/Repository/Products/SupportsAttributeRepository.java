package com.example.my_app.Repository.Products;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.my_app.model.Product.Products_Support_Attribute;

@Repository
public interface SupportsAttributeRepository extends JpaRepository<Products_Support_Attribute, UUID> {

}
