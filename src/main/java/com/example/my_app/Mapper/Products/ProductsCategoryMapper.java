package com.example.my_app.Mapper.Products;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import com.example.my_app.Enum.Products.StatusCategory;
import com.example.my_app.model.Product.ProductsCategory;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ProductsCategoryMapper {
    ProductsCategory toEntity(StatusCategory category);
}
