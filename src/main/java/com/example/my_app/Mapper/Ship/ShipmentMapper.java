package com.example.my_app.Mapper.Ship;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.springframework.stereotype.Component;

import com.example.my_app.DTO.ship.ShipmentDTO;
import com.example.my_app.model.ship.Shipment;

@Component
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ShipmentMapper {
    @Mapping(target = "id", ignore = true)
    Shipment toEntity(ShipmentDTO request);
}
