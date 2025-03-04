package com.example.my_app.model.Admin;

import com.example.my_app.model.Base.TimeBase;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

import java.util.UUID;

@Entity
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "Payroll")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
public class Payroll extends TimeBase {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID id;

    BigDecimal baseSalary;
    BigDecimal bonus;
    BigDecimal deductions;
    BigDecimal netSalary;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "Employee_id", nullable = false)
    Employee employee_id;

}
