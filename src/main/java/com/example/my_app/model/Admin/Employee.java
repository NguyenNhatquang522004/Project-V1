package com.example.my_app.model.Admin;

import com.example.my_app.model.Base.TimeBase;
import com.example.my_app.model.Order.Order_Bill;
import com.example.my_app.model.Order.Order_WayBill;
import com.example.my_app.model.Purchasing.Purchase_Transaction;
import com.example.my_app.model.Purchasing.Purchase_Transaction_Return;
import com.example.my_app.model.Role_Permisson_Admin.Admin_Role;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

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

    @Column(columnDefinition = "nvarchar(255)", nullable = true)
    String name;

    @Column(columnDefinition = "nvarchar(255)", nullable = true)
    String email;

    @Column(columnDefinition = "nvarchar(255)", nullable = true)
    String phone;

    @Column(columnDefinition = "nvarchar(255)", nullable = true)
    String position;

    @Column(columnDefinition = "nvarchar(255)", nullable = true)
    String Country;
    @Column(columnDefinition = "nvarchar(255)", nullable = true)
    String Province;
    @Column(columnDefinition = "nvarchar(255)", nullable = true)
    String City;
    @Column(columnDefinition = "nvarchar(255)", nullable = true)
    String Ward;

    @ManyToMany(fetch = FetchType.EAGER, cascade = { CascadeType.ALL })
    @JoinTable(name = "Employee_Schedule", joinColumns = @JoinColumn(name = "Employee_id"), inverseJoinColumns = @JoinColumn(name = "Schedule_id"))
    Set<Work_Schedule> employee_Schedule = new HashSet<>();

    @OneToMany(fetch = FetchType.EAGER, cascade = { CascadeType.ALL }, orphanRemoval = true, mappedBy = "employee_id")
    @JsonIgnore
    Set<LeaveRequests> employee_LeaveRequests = new HashSet<>();

    @OneToMany(fetch = FetchType.EAGER, cascade = { CascadeType.ALL }, orphanRemoval = true, mappedBy = "employee_id")
    @JsonIgnore
    Set<Attendance> employee_Attendance = new HashSet<>();

    @OneToMany(fetch = FetchType.EAGER, cascade = { CascadeType.ALL }, orphanRemoval = true, mappedBy = "employee_id")
    @JsonIgnore
    Set<Payroll> employee_Payroll = new HashSet<>();

    @OneToMany(fetch = FetchType.EAGER, cascade = { CascadeType.ALL }, orphanRemoval = true, mappedBy = "employee_id")
    @JsonIgnore
    Set<Order_Bill> employee_Order_Bill = new HashSet<>();

    @OneToMany(fetch = FetchType.EAGER, cascade = { CascadeType.ALL }, orphanRemoval = true, mappedBy = "employee_id")
    @JsonIgnore
    Set<Order_WayBill> employee_Order_WayBill = new HashSet<>();

    @OneToMany(cascade = { CascadeType.ALL }, fetch = FetchType.EAGER, orphanRemoval = true, mappedBy = "role_Employee")
    @JsonIgnore
    Set<Admin_Role> employee_Role = new HashSet<>();

    @OneToMany(fetch = FetchType.EAGER, cascade = { CascadeType.ALL }, orphanRemoval = true, mappedBy = "employee_id")
    @JsonIgnore
    Set<Purchase_Transaction> employee_Purchase_Transaction = new HashSet<>();

    @OneToMany(fetch = FetchType.EAGER, cascade = { CascadeType.ALL }, orphanRemoval = true, mappedBy = "employee_id")
    @JsonIgnore
    Set<Purchase_Transaction_Return> employee_Purchase_Transaction_Return = new HashSet<>();
}
