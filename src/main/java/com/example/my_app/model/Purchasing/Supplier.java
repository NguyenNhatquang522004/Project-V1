package com.example.my_app.model.Purchasing;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import com.example.my_app.model.Base.TimeBase;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
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
@Table(name = "Supplier")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
public class Supplier extends TimeBase {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID id;

    @Column(name = "supplier_code", nullable = false, unique = true)
    String supplierCode;

    @Column(name = "name", nullable = false)
    String name;

    @Column(name = "address")
    String address;

    @Column(name = "region")
    String region;

    @Column(name = "ward")
    String ward;

    @Column(name = "phone")
    String phone;

    @Column(name = "email")
    String email;

    @Column(name = "company")
    String company;

    @Column(name = "tax_code")
    String taxCode;

    @Column(name = "supplier_group")
    String supplierGroup;

    @ManyToMany(mappedBy = "purchase_Transaction_Supplier", fetch = FetchType.EAGER, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    Set<Purchase_Transaction> Supplier_Purchase_Transaction  = new HashSet<>();

    @ManyToMany(mappedBy = "purchase_Transaction_Return_Supplier", fetch = FetchType.EAGER, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    Set<Purchase_Transaction_Return> Supplier_Purchase_Transaction_Return  = new HashSet<>();
}
