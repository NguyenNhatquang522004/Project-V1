package com.example.my_app.Mapper.Ship;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.springframework.stereotype.Component;

import com.example.my_app.DTO.ship.ShipStatusHistoryDTO;
import com.example.my_app.model.ship.ShipStatusHistory;

@Component
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ShipStatusHistoryMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "shipmentInfo_id", ignore = true)
    @Mapping(target = "shipStatusHistory_WayBill", ignore = true)
    ShipStatusHistory toEntity(ShipStatusHistoryDTO request);
}
