package com.example.my_app.modules.Order.Request;

import java.util.UUID;

import com.example.my_app.Configuration.AutoDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
public class RequestBuyItem {
    int discount;
    int receivedAmount;
    int totalAmount;
    String Country;
    String Province;
    String City;
    String Ward;
    String notes;
    @JsonDeserialize(using = AutoDeserializer.class)
    UUID user_id;
    @JsonDeserialize(using = AutoDeserializer.class)
    UUID order_id;
}
