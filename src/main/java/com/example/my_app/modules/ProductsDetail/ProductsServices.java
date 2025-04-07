package com.example.my_app.modules.ProductsDetail;

import java.util.List;
import java.util.Set;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.my_app.Repository.Products.BrandsRepository;
import com.example.my_app.Repository.Products.CategoryRepository;
import com.example.my_app.Repository.Products.ProductRepositoryPanigation;
import com.example.my_app.Repository.Products.SupportsRepository;
import com.example.my_app.common.ResponedGlobal;
import com.example.my_app.model.Product.Products;
import com.example.my_app.modules.ProductsDetail.Responed.BrandResponed;
import com.example.my_app.modules.ProductsDetail.Responed.CartResponed;
import com.example.my_app.modules.ProductsDetail.Responed.CategoryResponed;
import com.example.my_app.modules.ProductsDetail.Responed.ColorResponed;

@Service
public class ProductsServices {
    @Autowired
    ProductRepositoryPanigation productRepositoryPanigation;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    BrandsRepository brandsRepository;

    @Autowired
    SupportsRepository supportsRepository;

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
            return ResponedGlobal.builder().code("1").data("").messages("").build();
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
}
