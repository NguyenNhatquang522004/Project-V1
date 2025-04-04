package com.example.my_app.Mapper.Products;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;
import org.springframework.stereotype.Component;

import com.example.my_app.DTO.Products.Products_SupportsDTO;
import com.example.my_app.model.Product.Products_Supports;

@Component
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ProductsSupportsMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "products_id", ignore = true)
    @Mapping(target = "products_Supports_Products_Support_Attribute", ignore = true)

    Products_Supports toEntity(Products_SupportsDTO request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "products_id", ignore = true)
    @Mapping(target = "products_Supports_Products_Support_Attribute", ignore = true)
 
    void updateEntity(@MappingTarget Products_Supports products_Supports, Products_SupportsDTO products_SupportsDTO);

    @Mapping(target = "products_Supports_Products_Support_Attribute", ignore = true)
  
    Products_SupportsDTO toDTO(Products_Supports products_Supports);

}
