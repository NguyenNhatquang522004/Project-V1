package com.example.my_app.model.Product;

import java.util.UUID;

import com.example.my_app.Enum.Products.StatusSalesPer;
import com.example.my_app.model.Base.TimeBase;

import jakarta.persistence.CascadeType;
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

// @Entity
// @Data
// @FieldDefaults(level = AccessLevel.PRIVATE)
// @Table(name = "Products_Sales")
// @AllArgsConstructor
// @NoArgsConstructor(force = true)
// @EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
// giảm giá sản phẩm gốc
public class Products_sales extends TimeBase {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID id;

    @Enumerated(EnumType.STRING)
    StatusSalesPer salePer;

    String Description;

    // @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    // @JoinColumn(name = "Products_id", nullable = false)
    // Products products_id;
}
