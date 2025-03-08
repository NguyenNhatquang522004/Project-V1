package com.example.my_app.DTO.Products;

import java.math.BigDecimal;
import java.util.Set;
import java.util.UUID;

import com.example.my_app.DTO.Order.OrderDTO;
import com.fasterxml.jackson.annotation.JsonProperty;

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
    @JsonProperty("isActive")
    boolean isActive;
    Set<Products_SupportsDTO> products_support;
    Set<ProductsImgDTO> products_img;
    Set<OrderDTO> products_order;
    ProductsCategoryDTO productsCategory;
    ProductsCategoryDTO products_Brands_id;
}
