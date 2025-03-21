package com.example.my_app.model.Warehouse;

import java.util.UUID;

import com.example.my_app.Enum.inventory_cards.StatusPartner;
import com.example.my_app.Enum.inventory_cards.StatusTransactionType;
import com.example.my_app.model.Base.TimeBase;
import com.example.my_app.model.Order.Order;
import com.example.my_app.model.Product.Products_Supports;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

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
@Table(name = "inventory_cards")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
// thẻ xuất nhập kho của mỗi sản phẩm
public class inventory_cards extends TimeBase {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID id;

    String document;

    @Enumerated(EnumType.STRING)
    @Column(nullable = true)
    StatusPartner partner;

    @Enumerated(EnumType.STRING)
    @Column(nullable = true)
    StatusTransactionType transactionType;

    int unitPrice;

    int quantity;

    int costPrice;

    int totalPrice;
    @OneToOne(fetch = FetchType.EAGER)
    Order order_id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "User_id", nullable = false)
    @JsonIgnore
    Products_Supports products_Supports_id;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "Warehouse_id", nullable = false)
    @JsonIgnore
    Warehouse Warehouse_id;

}
