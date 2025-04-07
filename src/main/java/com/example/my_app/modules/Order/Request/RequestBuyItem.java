package com.example.my_app.modules.Order.Request;

import jakarta.persistence.Column;
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

}
