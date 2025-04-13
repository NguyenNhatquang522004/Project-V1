package com.example.my_app.modules.Products.Request;

import java.util.List;

import java.util.UUID;

import com.example.my_app.DTO.Products.ProductsBrandDTO;
import com.example.my_app.DTO.Products.ProductsCategoryDTO;
import com.example.my_app.DTO.Products.ProductsDTO;
import com.example.my_app.DTO.Products.ProductsImgDTO;
import com.example.my_app.DTO.Products.Products_SupportsDTO;
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
public class RequestUpdate {
    ProductsDTO productsData;

    ProductsBrandDTO brandsData;

    ProductsCategoryDTO categoryData;

    List<UUID> urlDataDelete;

    List<ProductsImgDTO> urlDataNew;
    @JsonProperty("ProductsSupportDataAdd")
    List<Products_SupportsDTO> ProductsSupportDataAdd;
    @JsonProperty("ProductsSupportDataUpdate")
    List<Products_SupportsDTO> ProductsSupportDataUpdate;
    @JsonProperty("ProductsSupportDataDelete")
    List<UUID> ProductsSupportDataDelete;
}
