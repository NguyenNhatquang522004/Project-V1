package com.example.my_app.DTO.ship;

import java.util.UUID;

import com.example.my_app.Configuration.AutoDeserializer;
import com.example.my_app.Enum.StatusShipment;

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
// cách đối tác ship
public class ShipmentDTO {
    @JsonDeserialize(using = AutoDeserializer.class)
    UUID id;

    @JsonDeserialize(using = AutoDeserializer.class)
    StatusShipment description;
    String Country;
    String Province;
    String City;
    String Ward;
    String Number;

    ShipmentInfoDTO user_address;
}
