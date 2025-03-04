package com.example.my_app.Modules_Admin.Products.Request;

import java.util.List;

import com.example.my_app.DTO.Products.ProductsBrandDTO;
import com.example.my_app.DTO.Products.ProductsCategoryDTO;
import com.example.my_app.DTO.Products.ProductsDTO;
import com.example.my_app.DTO.Products.ProductsImgDTO;
import com.example.my_app.DTO.Products.ProductsSupportDTO;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
public class RequestUpLocalDateTime {
    ProductsDTO productsData;

    ProductsBrandDTO brandsData;

    ProductsCategoryDTO categoryData;

    List<ProductsImgDTO> urlData;

    List<ProductsSupportDTO> ProductsSupportData;
}
