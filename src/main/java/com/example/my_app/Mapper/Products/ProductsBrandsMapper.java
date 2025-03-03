package com.example.my_app.Mapper.Products;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import com.example.my_app.Enum.Products.StatusBrandsProducts;
import com.example.my_app.model.Product.Products_Brands;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ProductsBrandsMapper {
    @Mapping(target = "id", ignore = true)
    Products_Brands toEntity(StatusBrandsProducts brands);
}
