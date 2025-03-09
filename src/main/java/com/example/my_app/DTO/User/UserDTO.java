package com.example.my_app.DTO.User;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import java.util.Set;
import java.util.UUID;

import com.example.my_app.Configuration.AutoDeserializer;
import com.example.my_app.DTO.CustomerCare.Loyalty_TransactionDTO;
import com.example.my_app.DTO.Order.OrderDTO;
import com.example.my_app.DTO.Order.OrderStatusHistoryDTO;
import com.example.my_app.DTO.Order.Order_PerOrderDTO;
import com.example.my_app.DTO.Role_Permission.RoleDTO;
import com.example.my_app.Enum.user.GenderUser;
import com.example.my_app.Enum.user.StatusUserEntry;

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
public class UserDTO {
    @JsonDeserialize(using = AutoDeserializer.class)
    UUID id;

    String username;

    String email;

    GenderUser Gender;
    @JsonDeserialize(using = AutoDeserializer.class)
    StatusUserEntry statusEntry;

    String password;

    String code;

    String url;

    String accessToken;

    String refreshToken;

    LocalDateTime code_expired;

    BigDecimal balance;

    Set<AddressDTO> user_address;

    Set<OrderStatusHistoryDTO> user_orderHistory;

    RoleDTO user_role;

    Set<OrderDTO> user_order;

    Loyalty_TransactionDTO user_Loyalty_Transaction;

    Set<Order_PerOrderDTO> user_Order_PerOrder;

}
