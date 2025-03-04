package com.example.my_app.DTO.Order;

import java.util.UUID;

import com.example.my_app.Configuration.EnumDeserializer;
import com.example.my_app.DTO.Products.ProductsDTO;
import com.example.my_app.Enum.Products.StutusSizeProducts;

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
public class Order_ProductsDTO {
  
    UUID id;

    String color;
    int quantity;
    @JsonDeserialize(using = EnumDeserializer.class)
    StutusSizeProducts stutusSizeProducts;

    OrderDTO order_id;
    ProductsDTO products_id;
}
