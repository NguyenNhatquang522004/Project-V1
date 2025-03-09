package com.example.my_app.DTO.Purchasing;

import java.util.Set;
import java.util.UUID;

import com.example.my_app.Configuration.AutoDeserializer;
import com.example.my_app.DTO.Admin.EmployeeDTO;
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
// trả hàng
public class Purchase_Transaction_ReturnDTO {
    @JsonDeserialize(using = AutoDeserializer.class)

    UUID id;

    String time;

    Double totalReturnAmount;

    Double discount;

    Double supplierDue;

    Double supplierPaid;

    String status;

    Purchase_TransactionDTO purchase_Transaction_Return_Purchase_Transaction;

    Set<SupplierDTO> purchase_Transaction_Return_Supplier;
    EmployeeDTO employee_id;
}
