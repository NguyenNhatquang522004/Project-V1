package com.example.my_app.model.Order;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import com.example.my_app.Enum.StatusPayment;
import com.example.my_app.model.Base.TimeBase;

import com.example.my_app.model.User.User;
import com.example.my_app.model.Warehouse.inventory_cards;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
@Table(name = "Orders")
@AllArgsConstructor
@NoArgsConstructor(force = true)
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
// đặt hàng
public class Order extends TimeBase {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID id;

    @Column(name = "order_date", nullable = false)
    LocalDateTime orderDate;

    @Column(name = "total_amount", nullable = false)
    BigDecimal totalAmount;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_status", nullable = false)
    StatusPayment paymentStatus;

    @Column(columnDefinition = "nvarchar(255)", nullable = true)
    String Country;
    @Column(columnDefinition = "nvarchar(255)", nullable = true)
    String Province;
    @Column(columnDefinition = "nvarchar(255)", nullable = true)
    String City;
    @Column(columnDefinition = "nvarchar(255)", nullable = true)
    String Ward;

    @Column(name = "notes", length = 500)
    String notes;
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "order_id", cascade = CascadeType.ALL, orphanRemoval = true)
    Set<Order_Products> order_products = new HashSet<>();

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "User_id", nullable = false)
    User order_User;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "Products_id", nullable = false)
    Order_Payment order_Payment_id;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    @JoinColumn(name = "orders_inventory_cards_id", referencedColumnName = "id")
    inventory_cards orders_inventory_cards;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    @JoinColumn(name = "Order_Bill_id", referencedColumnName = "id")
    Order_Bill orders_bill;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    @JoinColumn(name = "Order_Status_History_id", referencedColumnName = "id")
    OrderStatusHistory orders_OrderStatusHistory;

}
