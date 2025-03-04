package com.example.my_app.DTO.CustomerCare;

import java.util.UUID;

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
// thẻ tích điểm
public class Loyalty_TransactionDTO {
    UUID id;
    UserDTO role_user;
}
