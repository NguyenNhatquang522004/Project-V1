package com.example.my_app.DTO.Order;

import java.util.UUID;

import org.springframework.data.domain.jaxb.SpringDataJaxb.OrderDto;

import com.example.my_app.Configuration.AutoDeserializer;
import com.example.my_app.DTO.User.UserDTO;
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
public class OrderStatusHistoryDTO {
    @JsonDeserialize(using = AutoDeserializer.class)
    UUID id;
    OrderDto order_id;
    UserDTO user_id;
}
