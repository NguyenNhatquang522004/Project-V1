package com.example.my_app.Mapper.Products;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import com.example.my_app.model.Product.Products_img;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ProductsImgMapper {
    Products_img toEntity(String url);
}
