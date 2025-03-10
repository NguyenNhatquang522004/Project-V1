package com.example.my_app.DTO.Products;

import java.util.UUID;

import com.example.my_app.Configuration.AutoDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

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
public class ProductsSupportAttributeDTO {
    @JsonDeserialize(using = AutoDeserializer.class)
    UUID id;
    String size;

    int sellingPrice;

    int costPrice;

    int quantity;

    Products_SupportsDTO products_Supports_id;
}
