package com.example.my_app.model.Order;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import com.example.my_app.model.Base.TimeBase;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
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
    int totalAmount;

    @Column(name = "discount")
    int discount;

    @Column(name = "received_amount")
    int receivedAmount;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "orders_bill", cascade = { CascadeType.ALL }, orphanRemoval = true)
    Set<Order> orders_Bill_Order = new HashSet<>();

    @OneToOne(cascade = { CascadeType.ALL }, fetch = FetchType.EAGER, orphanRemoval = true)
    @JoinColumn(name = "Order_WayBill_id", referencedColumnName = "id")
    Order_WayBill orders_WayBill;

    @OneToOne(cascade = { CascadeType.ALL }, fetch = FetchType.EAGER, orphanRemoval = true)
    @JoinColumn(name = "Order_Status_History_id", referencedColumnName = "id")
    OrderStatusHistory orders_OrderStatusHistory;

}
