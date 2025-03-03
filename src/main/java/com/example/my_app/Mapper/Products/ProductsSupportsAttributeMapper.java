package com.example.my_app.Mapper.Products;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import com.example.my_app.DTO.Products.ProductsSupportAttributeDTO;
import com.example.my_app.model.Product.Products_Support_Attribute;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ProductsSupportsAttributeMapper {
    @Mapping(target = "id", ignore = true)
    Products_Support_Attribute toEntity(ProductsSupportAttributeDTO request);
}
