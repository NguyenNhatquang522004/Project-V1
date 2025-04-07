package com.example.my_app.DTO.Order;

import java.time.LocalDateTime;

import java.util.Set;
import java.util.UUID;

import com.example.my_app.Configuration.AutoDeserializer;
import com.example.my_app.DTO.User.UserDTO;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Builder.Default;
import lombok.experimental.FieldDefaults;

@Builder
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
public class OrderDTO {
    @JsonDeserialize(using = AutoDeserializer.class)
    UUID id;

    boolean isActive;

    @Default
    LocalDateTime orderLocalDateTime = LocalDateTime.now();

    int totalAmount;

    String paymentStatus;

    String Country;

    String Province;

    String City;

    String Ward;

    String notes;

    int quantity;

    Set<Order_ProductsDTO> order_products;

    UserDTO order_User;

    OrderStatusHistoryDTO orders_OrderStatusHistory;
}
