package com.example.my_app.DTO.Purchasing;

import java.math.BigDecimal;
import java.util.Set;
import java.util.UUID;

import com.example.my_app.Configuration.EnumDeserializer;
import com.example.my_app.DTO.Admin.EmployeeDTO;
import com.example.my_app.Enum.StatusPurchasing;


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
// nhập hàng
public class Purchase_TransactionDTO {

    UUID id;
    BigDecimal totalPrice;
    @JsonDeserialize(using = EnumDeserializer.class)
    StatusPurchasing description ;
    Set<Purchase_Transaction_DetailDTO> purchase_Transaction_toDetail;
    Set<SupplierDTO> purchase_Transaction_Supplier;
    EmployeeDTO employee_id;
    Purchase_Transaction_ReturnDTO purchase_Transaction_Purchase_Transaction_Return;

}
