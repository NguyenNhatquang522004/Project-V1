package com.example.my_app.modules.Order.Request;

import com.example.my_app.Configuration.AutoDeserializer;
import com.example.my_app.Enum.StatusPaymentMethod;
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
    @JsonDeserialize(using = AutoDeserializer.class)
    StatusPaymentMethod paymentMethod;
    String profitCode;
    int discount;
    int receivedAmount;
}
