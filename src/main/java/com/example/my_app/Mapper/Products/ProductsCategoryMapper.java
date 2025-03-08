package com.example.my_app.Mapper.Products;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.springframework.stereotype.Component;

import com.example.my_app.DTO.Products.ProductsCategoryDTO;
import com.example.my_app.model.Product.ProductsCategory;

@Component
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ProductsCategoryMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "products", ignore = true)
    ProductsCategory toEntity(ProductsCategoryDTO category);

    @Mapping(target = "products", ignore = true)
    ProductsCategoryDTO toDTO(ProductsCategory category);
}
