package com.example.my_app.DTO.ship;

import java.math.BigDecimal;

import java.util.Set;
import java.util.UUID;


import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

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
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID id;

    Integer totalOrders;
    BigDecimal codAmount;
    BigDecimal outstandingCod;
    BigDecimal totalShippingFee;
    BigDecimal outstandingShippingFee;

    ShipmentDTO shipment_id;

    Set<ShipStatusHistoryDTO> shipmentInfo_ShipStatusHistory;

}
