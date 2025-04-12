package com.example.my_app.modules.Order.Request;

import java.util.UUID;

import com.example.my_app.Configuration.AutoDeserializer;
import com.fasterxml.jackson.annotation.JsonProperty;
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
   int receivedAmount;
    @JsonProperty("Country")
    String Country;
    @JsonProperty("Province")
    String Province;
    @JsonProperty("City")
    String City;
    @JsonProperty("Ward")
    String Ward;
    String notes;
    @JsonDeserialize(using = AutoDeserializer.class)
    UUID order_id;
    String type;
}
