package com.example.my_app.Mapper.Ship;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.springframework.stereotype.Component;

import com.example.my_app.DTO.ship.ShipmentInfoDTO;
import com.example.my_app.model.ship.ShipmentInfo;

@Component
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ShipmentInfoMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "shipment_id", ignore = true)
    @Mapping(target = "shipmentInfo_ShipStatusHistory", ignore = true)
    ShipmentInfo toEntity(ShipmentInfoDTO request);
}
