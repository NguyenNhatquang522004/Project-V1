package com.example.my_app.DTO.Purchasing;

import java.math.BigDecimal;

import java.util.Set;
import java.util.UUID;


import com.example.my_app.model.Product.Products_Supports;


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
// nhập hàng chi tiết
public class Purchase_Transaction_DetailDTO {

    UUID id;    
    int quantity;
    BigDecimal billprice;
    BigDecimal importprice;
    BigDecimal totalprice;
    Set<Products_Supports> purchase_Transaction_Detail_Products_Support;
    Purchase_TransactionDTO purchase_Transaction_id;

}
