package com.example.my_app.DTO.Products;

import java.math.BigDecimal;
import java.util.UUID;

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
    UUID id;
    String name;

    int quantity;

    BigDecimal minPrice;

    BigDecimal maxPrice;

    int totalBUY;

    Boolean isActive;
}
