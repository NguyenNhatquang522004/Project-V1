package com.example.my_app.Mapper.Products;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import com.example.my_app.DTO.Products.ProductsDTO;
import com.example.my_app.model.Product.Products;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ProductMapper {
    @Mapping(target = "id", ignore = true)
    Products toEntity(ProductsDTO request);
}
