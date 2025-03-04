package com.example.my_app.Mapper.Products;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;
import org.springframework.stereotype.Component;

import com.example.my_app.DTO.Products.ProductsDTO;
import com.example.my_app.model.Product.Products;

@Component
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ProductMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "products_support", ignore = true)
    @Mapping(target = "products_img", ignore = true)
    @Mapping(target = "products_order", ignore = true)
    @Mapping(target = "productsCategory", ignore = true)
    @Mapping(target = "products_Brands_id", ignore = true)
    Products toEntity(ProductsDTO request);

    void updateEntity(@MappingTarget Products products, ProductsDTO productsDTO);

}
