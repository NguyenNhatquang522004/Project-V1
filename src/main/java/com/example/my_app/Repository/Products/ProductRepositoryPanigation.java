package com.example.my_app.Repository.Products;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.example.my_app.model.Product.Products;

@Repository
public interface ProductRepositoryPanigation extends PagingAndSortingRepository<Products, UUID> {
    Page<Products> findAll(Pageable pageable);
}
