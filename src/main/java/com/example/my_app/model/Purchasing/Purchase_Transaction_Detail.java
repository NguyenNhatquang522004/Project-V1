package com.example.my_app.model.Purchasing;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import com.example.my_app.model.Base.TimeBase;
import com.example.my_app.model.Product.Products_Supports;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
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
@Table(name = "Purchase_Transaction_Detail")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
// nhập hàng chi tiết
public class Purchase_Transaction_Detail extends TimeBase {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID id;
    int quantity;
    BigDecimal billprice;
    BigDecimal importprice;
    BigDecimal totalprice;
    @ManyToMany(fetch = FetchType.EAGER, cascade = { CascadeType.ALL })
    @JoinTable(name = "Purchase_Transaction_Detail_Products_Supports", joinColumns = @JoinColumn(name = "Purchase_Transaction_Detail_id"), inverseJoinColumns = @JoinColumn(name = "Products_Support_id"))
    Set<Products_Supports> purchase_Transaction_Detail_Products_Support = new HashSet<>();

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "Purchase_Transaction_Id", nullable = false)
    Purchase_Transaction purchase_Transaction_id;

}
