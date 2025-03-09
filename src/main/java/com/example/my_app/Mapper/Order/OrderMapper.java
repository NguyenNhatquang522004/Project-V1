package com.example.my_app.Mapper.Order;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;
import org.springframework.stereotype.Component;

import com.example.my_app.DTO.Order.OrderDTO;
import com.example.my_app.model.Order.Order;

@Component
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface OrderMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "order_Payment_id", ignore = true)
    @Mapping(target = "orders_inventory_cards", ignore = true)
    @Mapping(target = "orders_bill", ignore = true)
    @Mapping(target = "orders_OrderStatusHistory", ignore = true)
    @Mapping(target = "order_User", ignore = true)
    @Mapping(target = "order_products", ignore = true)
    void UpdateEntity(@MappingTarget Order order, OrderDTO request);
}
