package com.example.my_app.DTO.Admin;



import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

import java.util.UUID;

@Builder
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
public class Payroll {

    UUID id;

    BigDecimal baseSalary;
    BigDecimal bonus;
    BigDecimal deductions;
    BigDecimal netSalary;

    Employee employee_id;

}
