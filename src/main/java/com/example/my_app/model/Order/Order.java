package com.example.my_app.model.Order;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import com.example.my_app.model.Base.TimeBase;

import com.example.my_app.model.User.User;

import com.fasterxml.jackson.annotation.JsonIgnore;

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

    boolean isActive;

    @Column(name = "order_LocalDateTime", nullable = true)
    LocalDateTime orderLocalDateTime;

    @Column(name = "total_amount", nullable = true)
    int totalAmount;

    @Column(name = "receivedAmount", nullable = true)
    int receivedAmount;

    @Column(name = "payment_status", nullable = true)
    String paymentStatus;

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
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "order_id", cascade = { CascadeType.ALL }, orphanRemoval = true)
    @JsonIgnore
    Set<Order_Products> order_products = new HashSet<>();

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "User_id", nullable = false)
    @JsonIgnore
    User order_User;
}
