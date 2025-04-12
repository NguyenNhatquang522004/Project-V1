package com.example.my_app.DTO.Products;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import com.example.my_app.Configuration.AutoDeserializer;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Builder
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Products_SupportsDTO {
  @JsonDeserialize(using = AutoDeserializer.class)
  UUID id;
  String url;
  String color;
  String codecolor;
  @JsonProperty("isActive")
  boolean isActive;
  ProductsDTO products_id;
  Set<ProductsSupportAttributeDTO> products_Supports_Products_Support_Attribute = new HashSet<>();

}
