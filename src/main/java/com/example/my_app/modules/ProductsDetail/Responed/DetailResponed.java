package com.example.my_app.modules.ProductsDetail.Responed;

import java.util.ArrayList;
import java.util.List;

import com.example.my_app.DTO.Products.ProductsSupportAttributeDTO;
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
public class DetailResponed {
    String id;
    List<String> url = new ArrayList<>();
    String title;
    int maxprice;
    int minprice;
    List<Products_SupportsDTO> support = new ArrayList<>();
    List<ProductsSupportAttributeDTO> att;
}
