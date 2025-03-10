package com.example.my_app.DTO.Purchasing;

import java.util.Set;
import java.util.UUID;

import com.example.my_app.Configuration.AutoDeserializer;
import com.example.my_app.DTO.Products.Products_SupportsDTO;
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
// nhập hàng chi tiết
public class Purchase_Transaction_DetailDTO {
    @JsonDeserialize(using = AutoDeserializer.class)
    UUID id;
    int quantity;
    int billprice;
    int importprice;
    int totalprice;
    Set<Products_SupportsDTO> purchase_Transaction_Detail_Products_Support;
    Purchase_TransactionDTO purchase_Transaction_id;

}
