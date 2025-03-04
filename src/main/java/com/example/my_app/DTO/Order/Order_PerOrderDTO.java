package com.example.my_app.DTO.Order;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import java.util.UUID;

import com.example.my_app.Enum.StatusPreOrder;
import com.example.my_app.model.Product.Products_Supports;
import com.example.my_app.model.User.User;


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

    UUID id;

    LocalDateTime preOrderTime;

    StatusPreOrder statusPreOrder;

    BigDecimal preOrder_NeedPay;

    BigDecimal preOrder_Pay;

    Products_Supports order_PerOrder_Products_Supports;

    User order_PerOrder_User;
}
