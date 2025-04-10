package com.example.my_app.model.Product;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import com.example.my_app.model.Base.TimeBase;
import com.example.my_app.model.Order.Order_Products;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;

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
@Table(name = "Products")
@AllArgsConstructor
@NoArgsConstructor(force = true)
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
// sản phẩm gốc
public class Products extends TimeBase {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID id;
    @Column(nullable = true)
    String name;
    @Column(nullable = true)
    int quantity;
    @Column(nullable = true)
    int minPrice;

    @Column(nullable = true)
    int maxPrice;

    @Column(nullable = true)
    int totalBUY;

    @Column(nullable = true)
    Boolean isActive = false;

    @Column(nullable = true)
    String title;

    @Column(nullable = true)
    String url;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "products_id", orphanRemoval = true)
    @JsonIgnore
    Set<Products_Supports> products_support = new HashSet<>();

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "products_id", orphanRemoval = true)
    @JsonIgnore
    Set<Products_img> products_img = new HashSet<>();

    // @OneToMany(fetch = FetchType.EAGER, cascade = {CascadeType.ALL}, mappedBy =
    // "products_id", orphanRemoval = true)
    // Set<Products_sales> products_sales = new HashSet<>();

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "Category_id", nullable = false)
    @JsonIgnore
    ProductsCategory productsCategory;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "Brands_id", nullable = false)
    @JsonIgnore
    Products_Brands products_Brands_id;

}
