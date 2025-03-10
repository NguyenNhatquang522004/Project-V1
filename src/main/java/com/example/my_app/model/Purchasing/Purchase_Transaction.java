package com.example.my_app.model.Purchasing;

import java.util.Set;
import java.util.UUID;

import com.example.my_app.Enum.StatusPurchasing;
import com.example.my_app.model.Admin.Employee;

import com.example.my_app.model.Base.TimeBase;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

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
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
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
@Table(name = "Purchase_Transaction")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
// nhập hàng
public class Purchase_Transaction extends TimeBase {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID id;

    int totalPrice;

    @Enumerated(EnumType.STRING)
    @Column(nullable = true)
    StatusPurchasing description = StatusPurchasing.in;

    @OneToMany(fetch = FetchType.LAZY, cascade = {
            CascadeType.ALL }, mappedBy = "purchase_Transaction_id", orphanRemoval = true)
    @JsonIgnore
    Set<Purchase_Transaction_Detail> purchase_Transaction_toDetail;

    @ManyToMany(fetch = FetchType.EAGER, cascade = { CascadeType.ALL })
    @JoinTable(name = "Supplier_Purchase_Transaction", joinColumns = @JoinColumn(name = "Purchase_Transaction_id"), inverseJoinColumns = @JoinColumn(name = "Supplier_id"))
    Set<Supplier> purchase_Transaction_Supplier;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "Employee_id", nullable = false)
    @JsonIgnore
    Employee employee_id;

    @OneToOne(cascade = { CascadeType.ALL }, fetch = FetchType.EAGER, orphanRemoval = true)
    @JoinColumn(name = "Purchase_Transaction_Return_id", referencedColumnName = "id")
    Purchase_Transaction_Return purchase_Transaction_Purchase_Transaction_Return;

}
