package com.shop.vegetable.service.impl;

import java.util.Base64;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.shop.vegetable.dto.ProductDto;

import com.shop.vegetable.entity.Product;

import com.shop.vegetable.repository.ProductRepository;
import com.shop.vegetable.service.ProductService;
import com.shop.vegetable.utils.ImageUpload;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductServiceImple implements ProductService {
    private final ProductRepository ProductRepository;
    private final ImageUpload imageUpload;

    @Override
    public List<Product> findAll() {
        return ProductRepository.findAll();
    }

    @Override
    public Product save(MultipartFile imageProduct, ProductDto coursedto) {
        Product cou = new Product();
        try {
            if (imageProduct == null) {
                cou.setImage(null);
            } else {
                imageUpload.uploadFile(imageProduct);
                cou.setImage(Base64.getEncoder().encodeToString(imageProduct.getBytes()));
            }
            cou.setName(coursedto.getName());
            cou.setDescription(coursedto.getDescription());
            cou.setPrice(coursedto.getPrice());
            cou.setType(coursedto.getType());
            return ProductRepository.save(cou);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    @Override
    public void delete(Long id) {
        Optional<Product> Product = ProductRepository.findById(id);

        if (Product.isPresent()) {
            Product Product2 = Product.get();
            ProductRepository.delete(Product2);
        }

    }

    @Override
    public List<Product> findName(String name) {
        // TODO Auto-generated method stub
        return ProductRepository.findByName(name);
    }

    @Override
    public Optional<Product> findById(Long id) {
        // TODO Auto-generated method stub
        return ProductRepository.findById(id);
    }

    @Override
    public Product update(MultipartFile imageProduct, ProductDto productDto) {
        try {
            Product productUpdate = ProductRepository.getReferenceById(productDto.getId());
            if (imageProduct.getBytes().length > 0) {
                if (imageUpload.checkExist(imageProduct)) {
                    productUpdate.setImage(productUpdate.getImage());
                } else {
                    imageUpload.uploadFile(imageProduct);
                    productUpdate.setImage(Base64.getEncoder().encodeToString(imageProduct.getBytes()));
                }
            }
            productUpdate.setType(productDto.getType());
            productUpdate.setId(productUpdate.getId());
            productUpdate.setName(productDto.getName());
            productUpdate.setDescription(productDto.getDescription());
            productUpdate.setPrice(productDto.getPrice());
            
            return ProductRepository.save(productUpdate);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public ProductDto getById(Long id) {
        ProductDto productDto = new ProductDto();
        Product product = ProductRepository.getById(id);
        productDto.setId(product.getId());
        productDto.setName(product.getName());
        productDto.setDescription(product.getDescription());
        productDto.setPrice(product.getPrice());
        productDto.setType(product.getType());
        productDto.setImage(product.getImage());
        return productDto;
    }

    @Override
    public Product getByIdNotDto(Long id) {
        // TODO Auto-generated method stub
        return  ProductRepository.getById(id);
    }

}
