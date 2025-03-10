package com.example.my_app.model.Admin;

import com.example.my_app.model.Base.TimeBase;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

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

    int baseSalary;
    int bonus;
    int deductions;
    int netSalary;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "Employee_id", nullable = false)
    @JsonIgnore
    Employee employee_id;

}
