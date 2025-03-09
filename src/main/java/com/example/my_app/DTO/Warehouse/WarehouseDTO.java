package com.example.my_app.DTO.Warehouse;

import java.util.Set;
import java.util.UUID;

import com.example.my_app.Configuration.AutoDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

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
        @JsonDeserialize(using = AutoDeserializer.class)
        UUID id;
        Set<Inventory_TransactionDTO> warehouse_Inventory_Transaction;
        Set<warehouse_ProductsDTO> warehouse_Warehouse_Products;
}
