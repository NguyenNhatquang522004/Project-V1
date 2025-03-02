package com.example.my_app.DTO;

import java.util.List;

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
public class ProductsDTO {
    String name;
    boolean isActive;
    String brand;
    String category;
    String salePer;
    String Description;
    List<String> url;
    List<ProductsSupportDTO> ProductsSupport;

}
