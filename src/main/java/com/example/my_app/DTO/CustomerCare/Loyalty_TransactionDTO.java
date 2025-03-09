package com.example.my_app.DTO.CustomerCare;

import java.util.UUID;

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
// thẻ tích điểm
public class Loyalty_TransactionDTO {
    @JsonDeserialize(using = AutoDeserializer.class)
    UUID id;
    UserDTO role_user;
}
