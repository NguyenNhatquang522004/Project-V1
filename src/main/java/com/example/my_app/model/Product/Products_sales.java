package com.example.my_app.model.Product;

import java.util.UUID;

import com.example.my_app.Enum.Products.StatusSalesPer;
import com.example.my_app.model.Base.TimeBase;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

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

    // @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.ALL})
    // @JoinColumn(name = "Products_id", nullable = false)
    // Products products_id;
}
