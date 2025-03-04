package com.example.my_app.DTO.Order;

import java.util.UUID;

import com.example.my_app.Configuration.EnumDeserializer;
import com.example.my_app.Enum.Products.StutusSizeProducts;
import com.example.my_app.model.Product.Products;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

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
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID id;

    String color;
    int quantity;
    @JsonDeserialize(using = EnumDeserializer.class)
    StutusSizeProducts stutusSizeProducts;

    OrderDTO order_id;
    Products products_id;
}
