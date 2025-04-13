package com.example.my_app.modules.Products.Responed;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.example.my_app.DTO.Products.ProductsBrandDTO;
import com.example.my_app.DTO.Products.ProductsCategoryDTO;
import com.example.my_app.DTO.Products.ProductsDTO;
import com.example.my_app.DTO.Products.ProductsImgDTO;
import com.example.my_app.DTO.Products.Products_SupportsDTO;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
public class ResponedProducts {
    ProductsDTO productsData;

    ProductsBrandDTO brandsData;

    ProductsCategoryDTO categoryData;

    List<ProductsImgDTO> urlData;

    List<Products_SupportsDTO> ProductsSupportData = new ArrayList<>();
}
