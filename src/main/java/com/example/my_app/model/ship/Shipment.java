package com.example.my_app.model.ship;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import com.example.my_app.Enum.StatusShipment;
import com.example.my_app.model.Base.TimeBase;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
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
@Table(name = "Shipment")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
// cách đối tác ship
public class Shipment extends TimeBase {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID id;

    @Enumerated(EnumType.STRING)
    StatusShipment description;

    @Column(columnDefinition = "nvarchar(255)", nullable = true)
    String Country;
    @Column(columnDefinition = "nvarchar(255)", nullable = true)
    String Province;
    @Column(columnDefinition = "nvarchar(255)", nullable = true)
    String City;
    @Column(columnDefinition = "nvarchar(255)", nullable = true)
    String Ward;
    @Column(columnDefinition = "nvarchar(255)", nullable = true)
    String Number;

    @OneToOne(mappedBy = "shipment_id", fetch = FetchType.EAGER)
    @JoinColumn(name = "shipment_Info_id", referencedColumnName = "id")
    @JsonIgnore
    ShipmentInfo shipment_Info;

    
}
