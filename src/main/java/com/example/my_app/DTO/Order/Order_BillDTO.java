package com.example.my_app.DTO.Order;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import java.util.UUID;

import com.example.my_app.DTO.Admin.EmployeeDTO;



import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Builder
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
public class Order_BillDTO {
    UUID id;

    LocalDateTime time;

    String profitCode;

    BigDecimal totalAmount;

    BigDecimal discount;

    BigDecimal receivedAmount;

    OrderDTO orders_Bill_Order;

    Order_WayBillDTO orders_WayBill;
    EmployeeDTO employee_id;
}
