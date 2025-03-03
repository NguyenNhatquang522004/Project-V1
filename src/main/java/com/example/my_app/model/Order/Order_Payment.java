package com.example.my_app.model.Order;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import com.example.my_app.Enum.StatusPaymentMethod;
import com.example.my_app.model.Base.TimeBase;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
@Table(name = "Order_Products")
@AllArgsConstructor
@NoArgsConstructor(force = true)
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
// phương thức thanh toán
public class Order_Payment extends TimeBase {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID id;

    @Enumerated(EnumType.STRING)
    StatusPaymentMethod statusPaymentMethod;

    @OneToMany(fetch = FetchType.EAGER, cascade = {
            CascadeType.ALL }, mappedBy = "order_Payment_id", orphanRemoval = true)
    Set<Order> order_payment = new HashSet<>();
}
