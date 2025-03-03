package com.example.my_app.DTO.Products;

import java.math.BigDecimal;

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

    String size;

    BigDecimal sellingPrice;

    BigDecimal costPrice;

    int quantity;
}
