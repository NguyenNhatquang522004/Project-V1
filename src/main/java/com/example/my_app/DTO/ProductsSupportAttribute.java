package com.example.my_app.DTO;

import java.math.BigDecimal;

import com.example.my_app.Enum.Products.StutusSizeProducts;

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
public class ProductsSupportAttribute {

    String size;

    BigDecimal sellingPrice;

    BigDecimal costPrice;

    int quantity;
}
