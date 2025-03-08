package com.example.my_app.model.Warehouse;

import java.time.LocalDateTime;
import java.util.UUID;

import com.example.my_app.model.Base.TimeBase;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
@Table(name = "Inventory_Transaction")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
// thẻ kiểm kho
public class Inventory_Transaction extends TimeBase {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID id;

    @Column(name = "inventory_code", nullable = false)
    private String inventoryCode;

    @Column(name = "time", nullable = false)
    private LocalDateTime time;

    @Column(name = "balance_LocalDateTime", nullable = false)
    private LocalDateTime balanceLocalDateTime;

    @Column(name = "actual_quantity", nullable = false)
    private int actualQuantity;

    @Column(name = "total_actual_quantity", nullable = false)
    private int totalActualQuantity;

    @Column(name = "total_discrepancy", nullable = false)
    private int totalDiscrepancy;

    @Column(name = "increased_discrepancy_quantity", nullable = false)
    private int increasedDiscrepancyQuantity;

    @Column(name = "decreased_discrepancy_quantity", nullable = false)
    private int decreasedDiscrepancyQuantity;

    @Column(name = "notes")
    private String notes;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "Warehouse_id", nullable = false)
    @JsonIgnore
    Warehouse inventory_Transaction_Warehouse;

}
