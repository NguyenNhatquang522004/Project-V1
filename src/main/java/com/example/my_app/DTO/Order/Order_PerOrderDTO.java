package com.example.my_app.DTO.Order;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import java.util.UUID;

import com.example.my_app.Configuration.AutoDeserializer;
import com.example.my_app.DTO.Products.Products_SupportsDTO;
import com.example.my_app.DTO.User.UserDTO;
import com.example.my_app.Enum.StatusPreOrder;
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
public class Order_PerOrderDTO {
    @JsonDeserialize(using = AutoDeserializer.class)
    UUID id;

    LocalDateTime preOrderTime;

    StatusPreOrder statusPreOrder;

    BigDecimal preOrder_NeedPay;

    BigDecimal preOrder_Pay;

    Products_SupportsDTO order_PerOrder_Products_Supports;

    UserDTO order_PerOrder_User;
}
