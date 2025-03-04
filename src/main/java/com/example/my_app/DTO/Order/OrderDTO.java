package com.example.my_app.DTO.Order;

import java.math.BigDecimal;
import java.time.LocalDateTime;


import java.util.Set;
import java.util.UUID;

import com.example.my_app.Configuration.EnumDeserializer;
import com.example.my_app.DTO.Warehouse.inventory_cardsDTO;
import com.example.my_app.Enum.StatusPayment;

import com.example.my_app.model.Order.Order_Products;
import com.example.my_app.model.User.User;

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
public class OrderDTO {

    UUID id;

    LocalDateTime orderLocalDateTime;

    BigDecimal totalAmount;
    @JsonDeserialize(using = EnumDeserializer.class)
    StatusPayment paymentStatus;

    String Country;

    String Province;

    String City;

    String Ward;

    String notes;

    Set<Order_Products> order_products;

    User order_User;

    Order_PaymentDTO order_Payment_id;

    inventory_cardsDTO orders_inventory_cards;

    Order_BillDTO orders_bill;

    OrderStatusHistoryDTO orders_OrderStatusHistory;
}
