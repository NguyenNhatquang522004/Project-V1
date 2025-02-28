package com.example.my_app.model.Warehouse;

import java.time.LocalDateTime;
import java.util.UUID;

import com.example.my_app.model.Base.TimeBase;

import jakarta.persistence.Column;
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
@Table(name = "Inventory_Transaction")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
public class Inventory_Transaction extends TimeBase {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID id;

    @Column(name = "inventory_code", nullable = false)
    private String inventoryCode;

    @Column(name = "time", nullable = false)
    private LocalDateTime time;

    @Column(name = "balance_date", nullable = false)
    private LocalDateTime balanceDate;

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

}
