package com.example.my_app.Mapper.Products;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import com.example.my_app.DTO.Products.ProductsSupportDTO;
import com.example.my_app.model.Product.Products_Supports;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ProductsSupportsMapper {
    Products_Supports toEntity(ProductsSupportDTO request);
}
