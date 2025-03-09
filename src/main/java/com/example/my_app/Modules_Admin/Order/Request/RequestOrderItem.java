package com.example.my_app.Modules_Admin.Order.Request;

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
public class RequestOrderItem {
    int quantity;
    @JsonDeserialize(using = AutoDeserializer.class)
    UUID products_id;
    @JsonDeserialize(using = AutoDeserializer.class)
    UUID support_id;
    @JsonDeserialize(using = AutoDeserializer.class)
    UUID attribute_id;
    @JsonDeserialize(using = AutoDeserializer.class)
    UUID user_id;
}
