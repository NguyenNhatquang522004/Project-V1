package com.example.my_app.model.Product;


import java.util.Set;
import java.util.UUID;

import com.example.my_app.Enum.StatusCategory;
import com.example.my_app.model.Base.TimeBase;
import com.example.my_app.model.Order.Order_Products;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
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
@Table(name = "Products")
@AllArgsConstructor
@NoArgsConstructor(force = true)
@EqualsAndHashCode(callSuper = true)
public class Products extends TimeBase {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID id;
    @Column(nullable = true)
    String name;
    @Column(nullable = true)
    int quantity;
    @Column(nullable = true)
    float price;
    @Enumerated(EnumType.STRING)
    @Column(nullable = true)
    StatusCategory statusCategory;
    Boolean isActive = false;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "products_id", orphanRemoval = true)
    Set<Products_Supports> products_support;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "products_id", orphanRemoval = true)
    Set<Products_img> products_img;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "products_id", orphanRemoval = true)
    Set<Products_sales> products_sales;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "products_id", orphanRemoval = true)
    Set<Order_Products> products_order;
}
