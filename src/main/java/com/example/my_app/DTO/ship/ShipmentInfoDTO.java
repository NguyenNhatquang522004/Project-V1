package com.example.my_app.DTO.ship;

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
// thông tin tổng hợp về giao dịch với đối tác ship
public class ShipmentInfoDTO {
    @JsonDeserialize(using = AutoDeserializer.class)
    UUID id;

    int totalOrders;
    int codAmount;
    int outstandingCod;
    int codSuccess;
    int totalShippingFee;
    int outstandingShippingFee;

    ShipmentDTO shipment_id;

    Set<ShipStatusHistoryDTO> shipmentInfo_ShipStatusHistory;

}
