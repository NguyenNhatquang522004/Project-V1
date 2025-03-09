package com.example.my_app.DTO.Warehouse;

import java.math.BigDecimal;
import java.util.UUID;

import com.example.my_app.Configuration.AutoDeserializer;
import com.example.my_app.DTO.Products.Products_SupportsDTO;

import com.example.my_app.Enum.inventory_cards.StatusTransactionType;

import com.example.my_app.model.Order.Order;

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
public class inventory_cardsDTO {
    @JsonDeserialize(using = AutoDeserializer.class)
    UUID id;

    String document;

    String partner;
    @JsonDeserialize(using = AutoDeserializer.class)
    StatusTransactionType transactionType;

    BigDecimal unitPrice;

    int quantity;

    BigDecimal costPrice;

    BigDecimal totalPrice;

    Order order_id;

    Products_SupportsDTO products_Supports_id;

}
