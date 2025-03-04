package com.example.my_app.DTO.ship;


import java.util.Set;
import java.util.UUID;

import com.example.my_app.Configuration.EnumDeserializer;
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

    UUID id;

    @JsonDeserialize(using = EnumDeserializer.class)
    StatusShipment description;

    Set<ShipmentInfoDTO> user_address;
}
