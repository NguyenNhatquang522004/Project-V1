package com.example.my_app.model.Product;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import com.example.my_app.Enum.Products.StatusActiveProducts;
import com.example.my_app.Enum.user.StatusUserEntry;
import com.example.my_app.model.Base.TimeBase;
import com.example.my_app.model.Order.Order;
import com.example.my_app.model.Order.Order_Products;
import com.example.my_app.model.Warehouse.Warehouse_Products;
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
@Table(name = "Products_Supports")
@AllArgsConstructor
@NoArgsConstructor(force = true)
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
public class Products_Supports extends TimeBase {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID id;
    String url;
    BigDecimal price;
    int quantity;
    String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = true)
    StatusActiveProducts status_ActiveProducts;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "Products_id", nullable = false)
    Products products_id;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "products_Supports_id", cascade = CascadeType.ALL, orphanRemoval = true)
    Set<inventory_cards> products_Supports_Inventory_Cards  = new HashSet<>();

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "warehouse_Products_Products_Supports")
    Set<Warehouse_Products> products_Supports_Warehouse_Products  = new HashSet<>();

}
