package com.example.my_app.Mapper.Products;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;
import org.springframework.stereotype.Component;

import com.example.my_app.DTO.Products.ProductsImgDTO;
import com.example.my_app.model.Product.Products_img;

@Component
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ProductsImgMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "products_id", ignore = true)
    Products_img toEntity(ProductsImgDTO url);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "products_id", ignore = true)
    void updateEntity(@MappingTarget Products_img products_img, ProductsImgDTO productsImgDTO);

    ProductsImgDTO toDTO(Products_img products_Supports);
}
