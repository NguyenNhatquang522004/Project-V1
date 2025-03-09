package com.example.my_app.DTO.Purchasing;

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
// nhà cung cấp
public class SupplierDTO {
    @JsonDeserialize(using = AutoDeserializer.class)
    UUID id;
    String supplierCode;
    String name;
    String address;
    String region;
    String ward;
    String phone;
    String email;
    String company;
    String taxCode;
    String supplierGroup;
    String totalbuy;
    String accounts_payable;
    Set<Purchase_TransactionDTO> Supplier_Purchase_Transaction;
    Set<Purchase_Transaction_ReturnDTO> Supplier_Purchase_Transaction_Return;
}
