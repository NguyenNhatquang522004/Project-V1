package com.example.my_app.modules.ProductsDetail;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.thymeleaf.expression.Arrays;

import com.example.my_app.DTO.Products.ProductsSupportAttributeDTO;
import com.example.my_app.DTO.Products.Products_SupportsDTO;
import com.example.my_app.Repository.Products.BrandsRepository;
import com.example.my_app.Repository.Products.CategoryRepository;
import com.example.my_app.Repository.Products.ProductRepository;
import com.example.my_app.Repository.Products.ProductRepositoryPanigation;
import com.example.my_app.Repository.Products.SupportsRepository;
import com.example.my_app.common.ResponedGlobal;
import com.example.my_app.custom.Helper.Helper;
import com.example.my_app.model.Product.Products;
import com.example.my_app.model.Product.Products_Support_Attribute;
import com.example.my_app.model.Product.Products_Supports;
import com.example.my_app.model.Product.Products_img;
import com.example.my_app.modules.ProductsDetail.Responed.BrandResponed;
import com.example.my_app.modules.ProductsDetail.Responed.CartResponed;
import com.example.my_app.modules.ProductsDetail.Responed.CategoryResponed;
import com.example.my_app.modules.ProductsDetail.Responed.ColorResponed;
import com.example.my_app.modules.ProductsDetail.Responed.DetailResponed;

@Service
public class ProductsServices {
    @Autowired
    ProductRepositoryPanigation productRepositoryPanigation;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    BrandsRepository brandsRepository;

    @Autowired
    SupportsRepository supportsRepository;

    @Autowired
    Helper helper;

    public ResponedGlobal handlePanigationCart(int pageNumber, int pageSize) throws Exception {
        try {
            Pageable list = PageRequest.of(pageNumber, pageSize);
            Page<Products> searchProducts = productRepositoryPanigation.findAll(list);
            List<CartResponed> cartResponeds = searchProducts.stream().map(value -> {
                CartResponed item = new CartResponed();
                item.setTitle(value.getTitle());
                item.setMaxprice(value.getMaxPrice());
                item.setMinprice(value.getMinPrice());
                item.setUrl(value.getUrl());
                List<String> itemcolor = value.getProducts_support().stream().map(e -> {
                    return e.getCodecolor();
                }).collect(Collectors.toList());
                item.setCodeColor(itemcolor);
                return item;
            }).collect(Collectors.toList());
            return ResponedGlobal.builder().code("1").data(cartResponeds).messages("").build();
        } catch (Exception e) {
            return ResponedGlobal.builder().code("0").data("").messages("").build();
        }
    }

    public ResponedGlobal handleGetAllCart() throws Exception {
        try {
            List<CartResponed> cartResponeds = productRepository.findAll().stream().map(value -> {
                CartResponed item = new CartResponed();
                item.setId(value.getId().toString());
                item.setTitle(value.getTitle());
                item.setMaxprice(value.getMaxPrice());
                item.setMinprice(value.getMinPrice());
                item.setUrl(value.getUrl());
                List<String> itemcolor = value.getProducts_support().stream().map(e -> {
                    return e.getCodecolor();
                }).collect(Collectors.toList());
                item.setCodeColor(itemcolor);
                return item;
            }).collect(Collectors.toList());
            return ResponedGlobal.builder().code("1").data(cartResponeds).messages("").build();
        } catch (Exception e) {
            return ResponedGlobal.builder().code("0").data("").messages("").build();
        }
    }

    public ResponedGlobal handleRenderCategory() throws Exception {
        try {
            List<CategoryResponed> categoryResponeds = categoryRepository.findAll().stream().map(p -> {
                CategoryResponed item = new CategoryResponed();
                item.setTitle(p.getCategory());
                return item;
            }).collect(Collectors.toList());
            return ResponedGlobal.builder().code("1").data(categoryResponeds).messages("").build();
        } catch (Exception e) {
            return ResponedGlobal.builder().code("1").data("").messages("").build();
        }
    }

    public ResponedGlobal handleRenderBrand() throws Exception {
        try {
            List<BrandResponed> brandResponeds = brandsRepository.findAll().stream().map(p -> {
                BrandResponed item = new BrandResponed();
                item.setTitle(p.getBrands());
                return item;
            }).collect(Collectors.toList());
            return ResponedGlobal.builder().code("1").data(brandResponeds).messages("").build();
        } catch (Exception e) {
            return ResponedGlobal.builder().code("1").data("").messages("").build();
        }
    }

    public ResponedGlobal handleRenderColor() throws Exception {
        try {
            Set<ColorResponed> color = supportsRepository.findAll().stream().map(p -> {
                ColorResponed item = new ColorResponed();
                item.setCodecolor(p.getCodecolor());
                item.setColor(p.getColor());
                return item;
            }).collect(Collectors.toSet());
            return ResponedGlobal.builder().code("1").data(color).messages("").build();
        } catch (Exception e) {
            return ResponedGlobal.builder().code("1").data("").messages("").build();
        }
    }

    public ResponedGlobal handleRenderPriceDes(int pageNumber, int pageSize) throws Exception {
        try {
            Pageable list = PageRequest.of(pageNumber, pageSize, Sort.by("maxPrice").descending());
            Page<Products> searchProducts = productRepositoryPanigation.findAll(list);
            List<CartResponed> cartResponeds = searchProducts.stream().map(value -> {
                CartResponed item = new CartResponed();
                item.setTitle(value.getTitle());
                item.setMaxprice(value.getMaxPrice());
                item.setMinprice(value.getMinPrice());
                item.setUrl(value.getUrl());
                List<String> itemcolor = value.getProducts_support().stream().map(e -> {
                    return e.getCodecolor();
                }).collect(Collectors.toList());
                item.setCodeColor(itemcolor);
                return item;
            }).collect(Collectors.toList());
            return ResponedGlobal.builder().code("1").data(cartResponeds).messages("").build();
        } catch (Exception e) {
            return ResponedGlobal.builder().code("1").data("").messages("").build();
        }
    }

    public ResponedGlobal handleRenderPriceAsc(int pageNumber, int pageSize) throws Exception {
        try {
            Pageable list = PageRequest.of(pageNumber, pageSize, Sort.by("maxPrice").ascending());
            Page<Products> searchProducts = productRepositoryPanigation.findAll(list);
            List<CartResponed> cartResponeds = searchProducts.stream().map(value -> {
                CartResponed item = new CartResponed();
                item.setTitle(value.getTitle());
                item.setMaxprice(value.getMaxPrice());
                item.setMinprice(value.getMinPrice());
                item.setUrl(value.getUrl());
                List<String> itemcolor = value.getProducts_support().stream().map(e -> {
                    return e.getCodecolor();
                }).collect(Collectors.toList());
                item.setCodeColor(itemcolor);
                return item;
            }).collect(Collectors.toList());
            return ResponedGlobal.builder().code("1").data(cartResponeds).messages("").build();
        } catch (Exception e) {
            return ResponedGlobal.builder().code("1").data("").messages("").build();
        }
    }

    public ResponedGlobal handleRenderDetailProducts(UUID id) throws Exception {
        try {
            Optional<Products> searchProducts = helper.handlefind(id, productRepository::findById);
            if (searchProducts.isEmpty()) {
                return ResponedGlobal.builder().code("0").data("").messages("").build();
            }
            DetailResponed item = new DetailResponed();
            item.setId(searchProducts.get().getId().toString());
            item.setMaxprice(searchProducts.get().getMaxPrice());
            item.setMinprice(searchProducts.get().getMinPrice());
            item.setTitle(searchProducts.get().getTitle());
            for (Products_img valeImg : searchProducts.get().getProducts_img()) {
                item.getUrl().add(valeImg.getUrl());
            }
            for (Products_Supports value : searchProducts.get().getProducts_support()) {
                Products_SupportsDTO itemsupport = new Products_SupportsDTO();

                itemsupport.setCodecolor(value.getCodecolor());
                itemsupport.setColor(value.getColor());
                itemsupport.setId(value.getId());
                itemsupport.setUrl(value.getUrl());
                for (Products_Support_Attribute p : value
                        .getProducts_Supports_Products_Support_Attribute()) {

                    ProductsSupportAttributeDTO itematt = new ProductsSupportAttributeDTO();
                    itematt.setCostPrice(p.getCostPrice());
                    itematt.setQuantity(p.getQuantity());
                    itematt.setId(p.getId());
                    itematt.setSize(p.getSize());
                    itematt.setSellingPrice(p.getSellingPrice());
                    itematt.setSupport_id(itemsupport.getId());
                    itemsupport.getProducts_Supports_Products_Support_Attribute().add(itematt);
                }
                item.getSupport().add(itemsupport);
            }
            System.out.println(item.toString());
            return ResponedGlobal.builder().code("1").data(item).messages("").build();
        } catch (Exception e) {
            return ResponedGlobal.builder().code("0").data("").messages(e.toString()).build();
        }
    }
}
