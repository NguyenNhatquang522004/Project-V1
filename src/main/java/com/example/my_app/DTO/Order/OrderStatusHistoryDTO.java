package com.example.my_app.DTO.Order;

import java.util.UUID;

import org.springframework.data.domain.jaxb.SpringDataJaxb.OrderDto;

import com.example.my_app.DTO.User.UserDTO;

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
public class OrderStatusHistoryDTO {
    UUID id;
    OrderDto order_id;
    UserDTO user_id;
}
