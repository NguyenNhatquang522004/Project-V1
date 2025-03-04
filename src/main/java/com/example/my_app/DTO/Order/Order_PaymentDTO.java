package com.example.my_app.DTO.Order;


import java.util.Set;
import java.util.UUID;

import com.example.my_app.Configuration.EnumDeserializer;
import com.example.my_app.Enum.StatusPaymentMethod;
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
public class Order_PaymentDTO {

    UUID id;
    @JsonDeserialize(using = EnumDeserializer.class)
    StatusPaymentMethod statusPaymentMethod;

    Set<OrderDTO> order_payment;
}
