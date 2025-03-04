package com.example.my_app.model.Order;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import com.example.my_app.Enum.StatusPreOrder;
import com.example.my_app.model.Base.TimeBase;
import com.example.my_app.model.Product.Products_Supports;
import com.example.my_app.model.User.User;


import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
@Table(name = "PreOrder")
@AllArgsConstructor
@NoArgsConstructor(force = true)
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
public class Order_PerOrder extends TimeBase {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID id;

    LocalDateTime preOrderTime;

    @Enumerated(EnumType.STRING)
    StatusPreOrder statusPreOrder;

    BigDecimal preOrder_NeedPay;

    BigDecimal preOrder_Pay;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "Products_Supports_id", nullable = false)
    Products_Supports order_PerOrder_Products_Supports;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "User_id", nullable = false)
    User order_PerOrder_User;

}
