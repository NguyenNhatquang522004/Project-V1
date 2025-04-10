package com.example.my_app.model.Order;

import java.util.UUID;

import com.example.my_app.Enum.Products.StutusSizeProducts;
import com.example.my_app.model.Base.TimeBase;
import com.example.my_app.model.Product.Products;
import com.example.my_app.model.Product.Products_Support_Attribute;
import com.example.my_app.model.Product.Products_Supports;
import com.fasterxml.jackson.annotation.JsonIgnore;

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
@Table(name = "Order_Products")
@AllArgsConstructor
@NoArgsConstructor(force = true)
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
// chi tiết sản phẩm order
public class Order_Products extends TimeBase {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID id;
    String color;
    int quantity;

    @Enumerated(EnumType.STRING)
    StutusSizeProducts stutusSizeProducts;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "Order_id", nullable = false)
    @JsonIgnore
    Order order_id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "attribute_id", nullable = false)
    @JsonIgnore
    Products_Support_Attribute attribute_id;

}
