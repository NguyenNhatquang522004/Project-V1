package com.example.my_app.DTO.Warehouse;


import java.util.Set;
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
// kho
public class WarehouseDTO {
        UUID id;
        Set<Inventory_TransactionDTO> warehouse_Inventory_Transaction;
        Set<warehouse_ProductsDTO> warehouse_Warehouse_Products;
}
