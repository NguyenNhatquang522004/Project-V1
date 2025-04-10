package com.example.my_app.model.Product;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import com.example.my_app.model.Base.TimeBase;
import com.example.my_app.model.Order.Order_Products;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;

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
@Table(name = "Products_Supports")
@AllArgsConstructor
@NoArgsConstructor(force = true)
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
// cách sản phẩm con
public class Products_Supports extends TimeBase {
        @Id
        @GeneratedValue(strategy = GenerationType.UUID)
        UUID id;
        String url;
        String color;
        String codecolor;
        boolean isActive;


        @ManyToOne(fetch = FetchType.EAGER)
        @JoinColumn(name = "Products_id", nullable = false)
        @JsonIgnore
        Products products_id;

        @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "products_Supports_id")
        @JsonIgnore
        Set<Products_Support_Attribute> products_Supports_Products_Support_Attribute = new HashSet<>();

}
