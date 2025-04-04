package com.example.my_app.DTO.Admin;

import com.example.my_app.Configuration.AutoDeserializer;


import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import lombok.*;
import lombok.experimental.FieldDefaults;


import java.util.UUID;

@Builder
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeDTO {
    @JsonDeserialize(using = AutoDeserializer.class)
    UUID id;

    String name;

    String email;

    String phone;

    String position;

    String Country;

    String Province;

    String City;

    String Ward;
}
