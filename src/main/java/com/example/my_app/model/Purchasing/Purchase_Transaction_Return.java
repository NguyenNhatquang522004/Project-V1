package com.example.my_app.model.Purchasing;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import com.example.my_app.model.Admin.Employee;
import com.example.my_app.model.Base.TimeBase;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;

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
@Table(name = "Purchase_Transaction_Return")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
// trả hàng
public class Purchase_Transaction_Return extends TimeBase {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID id;

    @Column(name = "time")
    String time;

    @Column(name = "total_return_amount")
    Double totalReturnAmount;

    @Column(name = "discount")
    Double discount;

    @Column(name = "supplier_due")
    Double supplierDue;

    @Column(name = "supplier_paid")
    Double supplierPaid;

    @Column(name = "status")
    String status;

    @OneToOne(mappedBy = "purchase_Transaction_Purchase_Transaction_Return", fetch = FetchType.EAGER)
    Purchase_Transaction purchase_Transaction_Return_Purchase_Transaction;

    @ManyToMany(fetch = FetchType.EAGER, cascade = { CascadeType.ALL })
    @JoinTable(name = "Supplier_Purchase_Transaction_Return", joinColumns = @JoinColumn(name = "Purchase_Transaction_Retrun_id"), inverseJoinColumns = @JoinColumn(name = "Supplier_id"))
    Set<Supplier> purchase_Transaction_Return_Supplier = new HashSet<>();

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "Employee_id", nullable = false)
    @JsonIgnore
    Employee employee_id;
}
