package com.example.my_app.DTO.User;

import java.util.UUID;

import com.example.my_app.Configuration.AutoDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import lombok.Builder;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;

import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Builder
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
public class AddressDTO {
    @JsonDeserialize(using = AutoDeserializer.class)
    UUID id;

    String Country;

    String Province;

    String City;
    String Ward;

    String Number;

    UserDTO user;

}
