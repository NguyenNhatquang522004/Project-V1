package com.example.my_app.DTO.Admin;

import com.example.my_app.model.Order.Order_Bill;
import com.example.my_app.model.Order.Order_WayBill;
import com.example.my_app.model.Purchasing.Purchase_Transaction;
import com.example.my_app.model.Purchasing.Purchase_Transaction_Return;
import com.example.my_app.model.Role_Permisson_Admin.Admin_Role;


import lombok.*;
import lombok.experimental.FieldDefaults;


import java.util.Set;
import java.util.UUID;



@Builder
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
public class Employee {

    UUID id;

    String name;

    String email;

    String phone;

    String position;

    String Country;

    String Province;

    String City;

    String Ward;

    Set<Work_Schedule> employee_Schedule;

    Set<LeaveRequests> employee_LeaveRequests;

    Set<Attendance> employee_Attendance;

    Set<Payroll> employee_Payroll;

    Set<Order_Bill> employee_Order_Bill;

    Set<Order_WayBill> employee_Order_WayBill;

    Set<Admin_Role> employee_Role;

    Set<Purchase_Transaction> employee_Purchase_Transaction;
    Set<Purchase_Transaction_Return> employee_Purchase_Transaction_Return;
}
