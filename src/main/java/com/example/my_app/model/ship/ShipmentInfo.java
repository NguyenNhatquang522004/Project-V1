package com.example.my_app.model.ship;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import com.example.my_app.model.Base.TimeBase;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
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
@Table(name = "ShipmentInfo")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
// thông tin tổng hợp về giao dịch với đối tác ship
public class ShipmentInfo extends TimeBase {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID id;

    int totalOrders;
    int codAmount;
    int codSuccess;
    int outstandingCod;
    int totalShippingFee;
    int outstandingShippingFee;

    @OneToOne(cascade = { CascadeType.ALL }, fetch = FetchType.EAGER, orphanRemoval = true)
    @JoinColumn(name = "shipment_id", referencedColumnName = "id")
    @JsonIgnore
    Shipment shipment_id;

    @OneToMany(fetch = FetchType.LAZY, cascade = {
            CascadeType.ALL }, mappedBy = "shipmentInfo_id", orphanRemoval = true)
    @JsonIgnore
    Set<ShipStatusHistory> shipmentInfo_ShipStatusHistory = new HashSet<>();

}
