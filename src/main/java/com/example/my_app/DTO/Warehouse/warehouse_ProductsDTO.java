package com.example.my_app.DTO.Warehouse;

import java.util.UUID;

import com.example.my_app.DTO.Products.ProductsSupportDTO;



import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Builder
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
public class warehouse_ProductsDTO {
    UUID id;
    int quantity;
    WarehouseDTO warehouse_Products_Warehouse;
    ProductsSupportDTO warehouse_Products_Products_Supports;
}
