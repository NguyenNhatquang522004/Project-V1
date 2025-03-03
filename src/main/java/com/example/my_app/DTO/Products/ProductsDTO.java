package com.example.my_app.DTO.Products;

import java.math.BigDecimal;

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
}
