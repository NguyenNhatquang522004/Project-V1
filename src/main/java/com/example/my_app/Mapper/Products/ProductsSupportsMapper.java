package com.example.my_app.Mapper.Products;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import com.example.my_app.DTO.Products.ProductsSupportDTO;
import com.example.my_app.model.Product.Products_Supports;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ProductsSupportsMapper {
    @Mapping(target = "id", ignore = true)
    Products_Supports toEntity(ProductsSupportDTO request);
}
