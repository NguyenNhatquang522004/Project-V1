package com.example.my_app.model.Admin;

import com.example.my_app.model.Base.TimeBase;
import com.example.my_app.model.Purchasing.Purchase_Transaction;
import com.example.my_app.model.Role_Permission.Role;
import com.example.my_app.model.Role_Permisson_Admin.Admin_Role;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "Employee")
@AllArgsConstructor
@NoArgsConstructor(force = true)
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
public class Employee extends TimeBase {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID id;

    @ManyToMany(fetch = FetchType.EAGER, cascade = { CascadeType.ALL })
    @JoinTable(name = "Employee_Department", joinColumns = @JoinColumn(name = "Employee_id"), inverseJoinColumns = @JoinColumn(name = "Department_id"))
    Set<Department> employee_Departments = new HashSet<>();

    @ManyToMany(fetch = FetchType.EAGER, cascade = { CascadeType.ALL })
    @JoinTable(name = "Employee_Schedule", joinColumns = @JoinColumn(name = "Employee_id"), inverseJoinColumns = @JoinColumn(name = "Schedule_id"))
    Set<Work_Schedule> employee_Schedule = new HashSet<>();

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "employee_id")
    Set<LeaveRequests> employee_LeaveRequests = new HashSet<>();

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "employee_id")
    Set<Employee_Attendance> employee_Attendance = new HashSet<>();

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "employee_id")
    Set<Payroll> employee_Payroll = new HashSet<>();

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    @JoinColumn(name = "Role_id", referencedColumnName = "id")
    Admin_Role employee_Role;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "employee_id")
    Set<Purchase_Transaction> employee_Purchase_Transaction = new HashSet<>();
}
