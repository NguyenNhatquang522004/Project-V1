package com.example.my_app.DTO.Products;

import java.util.UUID;

import com.example.my_app.Configuration.AutoDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode( onlyExplicitlyIncluded = true)
public class ProductsImgDTO {
    @JsonDeserialize(using = AutoDeserializer.class)
    UUID id;
    String url;
}
