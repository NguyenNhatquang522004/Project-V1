package com.example.my_app.DTO.Order;

import java.util.UUID;

import com.example.my_app.DTO.Admin.EmployeeDTO;
import com.example.my_app.DTO.ship.ShipStatusHistoryDTO;

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
public class Order_WayBillDTO {
    UUID id;
    Order_BillDTO order_Order_Bill;
    ShipStatusHistoryDTO order_ShipStatusHistory;
    EmployeeDTO employee_id;
}
