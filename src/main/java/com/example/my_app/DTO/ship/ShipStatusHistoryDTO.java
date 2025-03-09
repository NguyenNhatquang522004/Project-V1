package com.example.my_app.DTO.ship;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import com.example.my_app.Configuration.AutoDeserializer;
import com.example.my_app.DTO.Order.Order_WayBillDTO;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

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
public class ShipStatusHistoryDTO {
    @JsonDeserialize(using = AutoDeserializer.class)
    UUID id;

    String invoiceCode;
    String shippingPartnerCode;
    String trackingNumber;
    LocalDateTime time;
    BigDecimal invoiceValue;
    BigDecimal outstandingCod;
    BigDecimal shippingFee;
    String status;
    ShipmentInfoDTO ShipmentInfo_id;
    Order_WayBillDTO shipStatusHistory_WayBill;
}
