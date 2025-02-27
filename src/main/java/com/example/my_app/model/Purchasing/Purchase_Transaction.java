package com.example.my_app.model.Purchasing;

import java.util.Set;
import java.util.UUID;

import com.example.my_app.Enum.StatusPurchasing;
import com.example.my_app.model.Admin.Employee;
import com.example.my_app.model.Admin.Payroll;
import com.example.my_app.model.Base.TimeBase;
import com.example.my_app.model.Role_Permisson_Admin.Admin_Permisson;

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
public class Purchase_Transaction extends TimeBase {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID id;
    @Enumerated(EnumType.STRING)
    @Column(nullable = true)
    StatusPurchasing description;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "purchase_Transaction_id", orphanRemoval = true)
    Set<Purchase_Transaction_Detail> purchase_Transaction_toDetail;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "Supplier_Purchase_Transaction", joinColumns = @JoinColumn(name = "Purchase_Transaction_id"), inverseJoinColumns = @JoinColumn(name = "Supplier_id"))
    Set<Supplier> purchase_Transaction_Supplier;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "Employee_id", nullable = false)
    Employee employee_id;

}
