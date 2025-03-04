package com.example.my_app.DTO.Warehouse;

import java.time.LocalDateTime;
import java.util.UUID;


import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Builder
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor

public class Inventory_TransactionDTO {

    UUID id;

    String inventoryCode;
    LocalDateTime time;
    LocalDateTime balanceLocalDateTime;
    int actualQuantity;
    int totalActualQuantity;
    int totalDiscrepancy;
    int increasedDiscrepancyQuantity;
    int decreasedDiscrepancyQuantity;
    String notes;

    WarehouseDTO inventory_Transaction_Warehouse;

}
