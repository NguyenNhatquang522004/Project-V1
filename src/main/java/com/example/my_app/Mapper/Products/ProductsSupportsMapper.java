package com.example.my_app.Mapper.Products;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.springframework.stereotype.Component;

import com.example.my_app.DTO.Products.ProductsSupportDTO;
import com.example.my_app.model.Product.Products_Supports;
@Component
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ProductsSupportsMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "products_id", ignore = true)
    @Mapping(target = "products_Supports_Products_Support_Attribute", ignore = true)
    @Mapping(target = "products_Supports_Purchase_Transaction_Detail", ignore = true)
    @Mapping(target = "products_Supports_Inventory_Cards", ignore = true)
    @Mapping(target = "products_Supports_Order_PerOrder", ignore = true)
    @Mapping(target = "products_Supports_Warehouse_Products", ignore = true)
    Products_Supports toEntity(ProductsSupportDTO request);
}
