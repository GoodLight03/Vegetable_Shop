package com.shop.vegetable.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
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

    Product updateEnable( Product product);

    ProductDto getById(Long id);

    Product getByIdNotDto(Long id);

    Page<ProductDto> searchProducts(int pageNo, String keyword);

    Page<ProductDto> getAllProducts(int pageNo);

    List<ProductDto> allProduct();

    List<Product> findByType(Long id);

    List<ProductDto> findByTypeDto(Long id);

    Page<ProductDto> getAllProductsPage(int pageNo,List<ProductDto> productDtoLists);

    List<Product> findByTypeAdmin(Long id);

    List<Product> findAllAdmin();
}
