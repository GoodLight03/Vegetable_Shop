package com.shop.vegetable.service;

import java.util.List;
import java.util.Optional;

import org.springframework.web.multipart.MultipartFile;

import com.shop.vegetable.dto.ProductDto;
import com.shop.vegetable.entity.Product;

public interface ProductService {
    Product save(MultipartFile imageProduct, ProductDto Productdto);

    List<Product> findAll();

    void delete(Long id);

    List<Product> findName(String name);

    Optional<Product> findById(Long id);

    Product update(MultipartFile imageProduct, ProductDto productDto);

    ProductDto getById(Long id);
}
