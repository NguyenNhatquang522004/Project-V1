package com.example.my_app.DTO.Products;

import java.math.BigDecimal;
import java.util.Set;
import java.util.UUID;

import com.example.my_app.DTO.Order.OrderDTO;

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
    private UUID id;
    private String name;
    private int quantity;
    private BigDecimal minPrice;
    private BigDecimal maxPrice;
    private int totalBUY;
    private Boolean isActive;
    private Set<ProductsSupportDTO> products_support;
    private Set<ProductsImgDTO> products_img;
    private Set<OrderDTO> products_order;
    private ProductsCategoryDTO productsCategory;
    private ProductsCategoryDTO products_Brands_id;
}
