package com.example.my_app.Mapper.Products;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.springframework.stereotype.Component;

import com.example.my_app.model.Product.Products_img;

@Component
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ProductsImgMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "products_id", ignore = true)
    Products_img toEntity(String url);
}
