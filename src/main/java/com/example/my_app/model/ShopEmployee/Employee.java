package com.example.my_app.model.ShopEmployee;

import com.example.my_app.model.Base.TimeBase;
import com.example.my_app.model.Role_Permission.Role;
import com.example.my_app.model.Role_Permisson_Admin.Admin_Role;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

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

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "Employee_Department", joinColumns = @JoinColumn(name = "Employee_id"), inverseJoinColumns = @JoinColumn(name = "Department_id"))
    Set<Department> employee_Departments;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "Employee_Schedule", joinColumns = @JoinColumn(name = "Employee_id"), inverseJoinColumns = @JoinColumn(name = "Schedule_id"))
    Set<Work_Schedule> employee_Schedule;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "employee_id")
    Set<LeaveRequests> employee_LeaveRequests;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "Employee_Attendance", joinColumns = @JoinColumn(name = "Employee_id"), inverseJoinColumns = @JoinColumn(name = "Attendance_id"))
    Set<Attendance> employee_Attendance;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "employee_id")
    Set<Payroll> employee_Payroll;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    @JoinColumn(name = "Role_id", referencedColumnName = "id")
    Admin_Role employee_Role;

}
