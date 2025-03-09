package com.example.my_app.DTO.Admin;

import com.example.my_app.Configuration.AutoDeserializer;
import com.example.my_app.DTO.Order.Order_BillDTO;
import com.example.my_app.DTO.Purchasing.Purchase_TransactionDTO;
import com.example.my_app.DTO.Purchasing.Purchase_Transaction_ReturnDTO;
import com.example.my_app.DTO.Role_Permisson_Admin.Admin_RoleDTO;
import com.example.my_app.model.Admin.Attendance;

import com.example.my_app.model.Order.Order_WayBill;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Set;
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

    Set<Work_ScheduleDTO> employee_Schedule;

    Set<LeaveRequestsDTO> employee_LeaveRequests;

    Set<Attendance> employee_Attendance;

    Set<PayrollDTO> employee_Payroll;

    Set<Order_BillDTO> employee_Order_Bill;

    Set<Order_WayBill> employee_Order_WayBill;

    Set<Admin_RoleDTO> employee_Role;

    Set<Purchase_TransactionDTO> employee_Purchase_Transaction;
    Set<Purchase_Transaction_ReturnDTO> employee_Purchase_Transaction_Return;
}
