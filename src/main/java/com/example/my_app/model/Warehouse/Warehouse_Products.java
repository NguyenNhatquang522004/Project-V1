package com.example.my_app.model.Warehouse;

import java.util.UUID;

import com.example.my_app.model.Base.TimeBase;
import com.example.my_app.model.Product.Products_Supports;


import jakarta.persistence.Entity;
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
@Table(name = "Warehouse_Products")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
// sản phẩm tồn kho
public class Warehouse_Products extends TimeBase {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID id;

    int quantity;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "Warehouse_id", nullable = false)
    Warehouse warehouse_Products_Warehouse;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "Products_Supports_id", nullable = false)
    Products_Supports warehouse_Products_Products_Supports;
}
