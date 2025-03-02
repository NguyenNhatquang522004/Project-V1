package com.example.my_app.DTO.Products;

import java.math.BigDecimal;
import java.util.List;

import com.example.my_app.Enum.Products.StatusBrandsProducts;
import com.example.my_app.Enum.Products.StatusCategory;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
public class ProductsDTO {

    String name;

    int quantity;

    BigDecimal minPrice;

    BigDecimal maxPrice;

    int totalBUY;

    Boolean isActive;

    String brand;

    String category;

    List<String> url;
    List<ProductsSupportDTO> ProductsSupport;
}
