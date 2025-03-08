package com.example.my_app.model.ship;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import com.example.my_app.model.Base.TimeBase;
import com.example.my_app.model.Order.Order_WayBill;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Entity
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "ShipStatusHistory")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
// lịch sử giao hàng của từng hóa đơn vận đơn
public class ShipStatusHistory extends TimeBase {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID id;

    String invoiceCode;
    String shippingPartnerCode;
    String trackingNumber;
    LocalDateTime time;
    BigDecimal invoiceValue;
    BigDecimal outstandingCod;
    BigDecimal shippingFee;
    String status;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ShipmentInfo_id", nullable = false)
    @JsonIgnore
    ShipmentInfo ShipmentInfo_id;

    @OneToOne(cascade = { CascadeType.ALL }, fetch = FetchType.EAGER, orphanRemoval = true)
    @JoinColumn(name = "Order_WayBill_id", referencedColumnName = "id")
    Order_WayBill shipStatusHistory_WayBill;
}
