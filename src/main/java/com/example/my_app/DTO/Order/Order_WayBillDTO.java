package com.example.my_app.DTO.Order;

import java.util.UUID;

import com.example.my_app.model.Admin.Employee;
import com.example.my_app.model.Order.Order_Bill;
import com.example.my_app.model.ship.ShipStatusHistory;


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
    Order_Bill order_Order_Bill;
    ShipStatusHistory order_ShipStatusHistory;
    Employee employee_id;
}
