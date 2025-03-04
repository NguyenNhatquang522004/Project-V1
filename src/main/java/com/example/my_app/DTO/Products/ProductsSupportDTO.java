package com.example.my_app.DTO.Products;

import java.util.List;
import java.util.UUID;

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
public class ProductsSupportDTO {
  UUID id;
  String color;
  String url;
  boolean isActive;
  List<ProductsSupportAttributeDTO> products_Supports_Products_Support_Attribute;

}
