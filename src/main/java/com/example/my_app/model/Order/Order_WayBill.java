package com.example.my_app.model.Order;

import java.util.UUID;

import com.example.my_app.DTO.ship.ShipStatusHistoryDTO;
import com.example.my_app.model.Admin.Employee;
import com.example.my_app.model.Base.TimeBase;
import com.example.my_app.model.ship.ShipStatusHistory;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Entity
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "Order_WayBill")
@AllArgsConstructor
@NoArgsConstructor(force = true)
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
// vận đơn
public class Order_WayBill extends TimeBase {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID id;

    @OneToOne(fetch = FetchType.EAGER)
    Order_Bill order_Order_Bill;

    @OneToOne(fetch = FetchType.EAGER)
    ShipStatusHistory order_ShipStatusHistory;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "Employee_id", nullable = false)
    Employee employee_id;

}
