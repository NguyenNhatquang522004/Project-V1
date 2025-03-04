package com.example.my_app.model.Order;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import com.example.my_app.model.Admin.Employee;
import com.example.my_app.model.Base.TimeBase;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
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
@Table(name = "Order_Bill")
@AllArgsConstructor
@NoArgsConstructor(force = true)
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
// hóa đơn
public class Order_Bill extends TimeBase {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID id;

    @Column(name = "time", nullable = false)
    LocalDateTime time;

    @Column(name = "profit_code")
    String profitCode;

    @Column(name = "total_amount", nullable = false)
    BigDecimal totalAmount;

    @Column(name = "discount")
    BigDecimal discount;

    @Column(name = "received_amount")
    BigDecimal receivedAmount;

    @OneToOne(fetch = FetchType.EAGER)
    Order orders_Bill_Order;

    @OneToOne(cascade = { CascadeType.ALL }, fetch = FetchType.EAGER, orphanRemoval = true)
    @JoinColumn(name = "Order_WayBill_id", referencedColumnName = "id")
    Order_WayBill orders_WayBill;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "Employee_id", nullable = false)
    Employee employee_id;

}
