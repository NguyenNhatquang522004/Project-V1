package com.example.my_app.DTO.Admin;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.UUID;

import com.example.my_app.Configuration.AutoDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@Builder
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
public class PayrollDTO {
    @JsonDeserialize(using = AutoDeserializer.class)
    UUID id;

    int baseSalary;
    int bonus;
    int deductions;
    int netSalary;

    EmployeeDTO employee_id;

}
