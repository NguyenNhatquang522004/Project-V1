package com.example.my_app.Mapper.Products;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.springframework.stereotype.Component;

import com.example.my_app.DTO.Products.ProductsBrandDTO;
import com.example.my_app.model.Product.Products_Brands;

@Component
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ProductsBrandsMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "products", ignore = true)
    Products_Brands toEntity(ProductsBrandDTO brands);

    @Mapping(target = "products", ignore = true)
    ProductsBrandDTO toDTO(Products_Brands brands);
}
