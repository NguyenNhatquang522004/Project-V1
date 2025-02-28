package com.example.my_app.model.Product;

import java.math.BigDecimal;
import java.util.UUID;

import com.example.my_app.Enum.Products.StutusSizeProducts;
import com.example.my_app.model.Base.TimeBase;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
@Table(name = "Products_Support_Attribute")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
// thông tin chi tiết một sản phẩm con
public class Products_Support_Attribute extends TimeBase {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID id;

    StutusSizeProducts size;

    BigDecimal sellingPrice;

    BigDecimal costPrice;

    int quantity;
}
