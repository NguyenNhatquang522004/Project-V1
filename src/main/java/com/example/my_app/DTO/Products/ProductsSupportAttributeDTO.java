package com.example.my_app.DTO.Products;

import java.math.BigDecimal;
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
public class ProductsSupportAttributeDTO {
    UUID id;
    String size;

    BigDecimal sellingPrice;

    BigDecimal costPrice;

    int quantity;

    Products_SupportsDTO products_Supports_id;
}
