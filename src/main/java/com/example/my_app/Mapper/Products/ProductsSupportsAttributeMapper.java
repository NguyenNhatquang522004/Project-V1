package com.example.my_app.Mapper.Products;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;
import org.springframework.stereotype.Component;

import com.example.my_app.DTO.Products.ProductsSupportAttributeDTO;

import com.example.my_app.model.Product.Products_Support_Attribute;

@Component
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ProductsSupportsAttributeMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "products_Supports_id", ignore = true)
    Products_Support_Attribute toEntity(ProductsSupportAttributeDTO request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "products_Supports_id", ignore = true)
    void updateEntity(@MappingTarget Products_Support_Attribute products_Support_Attribute,
            ProductsSupportAttributeDTO productsSupportAttributeDTO);

 
    @Mapping(target = "products_Supports_id", ignore = true)
    ProductsSupportAttributeDTO toDTO(Products_Support_Attribute products_Support_Attribute);

}
