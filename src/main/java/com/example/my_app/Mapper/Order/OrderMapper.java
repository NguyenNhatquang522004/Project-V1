package com.example.my_app.Mapper.Order;

import org.mapstruct.Mapper;

import org.mapstruct.ReportingPolicy;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface OrderMapper {
    // @Mapping(target = "id", ignore = true)
    // @Mapping(target = "order_User", ignore = true)
    // @Mapping(target = "order_products", ignore = true)
    // void UpdateEntity(@MappingTarget Order order, OrderDTO request);
}
