package com.shop.vegetable.service.impl;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
    private final ProductRepository productRepository;
    private final ImageUpload imageUpload;

    @Override
    public List<Product> findAll() {
        return productRepository.findAll();
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
            return productRepository.save(cou);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    @Override
    public void delete(Long id) {
        Optional<Product> Product = productRepository.findById(id);

        if (Product.isPresent()) {
            Product Product2 = Product.get();
            productRepository.delete(Product2);
        }

    }

    @Override
    public List<Product> findName(String name) {
        // TODO Auto-generated method stub
        return productRepository.findByName(name);
    }

    @Override
    public Optional<Product> findById(Long id) {
        // TODO Auto-generated method stub
        return productRepository.findById(id);
    }

    @Override
    public Product update(MultipartFile imageProduct, ProductDto productDto) {
        try {
            Product productUpdate = productRepository.getReferenceById(productDto.getId());
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
            
            return productRepository.save(productUpdate);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public ProductDto getById(Long id) {
        ProductDto productDto = new ProductDto();
        Product product = productRepository.getById(id);
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
        return  productRepository.getById(id);
    }

    @Override
    public Page<ProductDto> searchProducts(int pageNo, String keyword) {
        List<Product> products = productRepository.findAllByNameOrDescription(keyword);
        List<ProductDto> productDtoList = transferData(products);
        Pageable pageable = PageRequest.of(pageNo, 6);
        Page<ProductDto> dtoPage = toPage(productDtoList, pageable);
        return dtoPage;
    }

    @Override
    public List<ProductDto> allProduct() {
        List<Product> products = productRepository.findAll();
        List<ProductDto> productDtos = transferData(products);
        return productDtos;
    }

    @Override
    public Page<ProductDto> getAllProducts(int pageNo) {
        Pageable pageable = PageRequest.of(pageNo-1, 6);
        List<ProductDto> productDtoLists = this.allProduct();
        Page<ProductDto> productDtoPage = toPage(productDtoLists, pageable);
        return productDtoPage;
    }

    @Override
    public Page<ProductDto> getAllProductsPage(int pageNo,List<ProductDto> productDtoLists) {
        Pageable pageable = PageRequest.of(pageNo-1, 6);
      
        Page<ProductDto> productDtoPage = toPage(productDtoLists, pageable);
        return productDtoPage;
    }

    private Page toPage(List list, Pageable pageable) {
        if (pageable.getOffset() >= list.size()) {
            return Page.empty();
        }
        int startIndex = (int) pageable.getOffset();
        int endIndex = ((pageable.getOffset() + pageable.getPageSize()) > list.size())
                ? list.size()
                : (int) (pageable.getOffset() + pageable.getPageSize());
        List subList = list.subList(startIndex, endIndex);
        return new PageImpl(subList, pageable, list.size());
    }

    private List<ProductDto> transferData(List<Product> products) {
        List<ProductDto> productDtos = new ArrayList<>();
        for (Product product : products) {
            ProductDto productDto = new ProductDto();
            productDto.setId(product.getId());
            productDto.setName(product.getName());
            productDto.setPrice(product.getPrice());
            productDto.setDescription(product.getDescription());
            productDto.setImage(product.getImage());
            productDto.setType(product.getType());
            productDtos.add(productDto);
        }
        return productDtos;
    }

    @Override
    public List<Product> findByType(Long id) {
        return productRepository.findByType(id);
    }

    @Override
    public List<ProductDto> findByTypeDto(Long id) {
        return transferData(productRepository.findByType(id));
    }

    @Override
    public List<Product> findByTypeAdmin(Long id) {
        return productRepository.findByTypeAdmin(id);
    }

    @Override
    public List<Product> findAllAdmin() {
        return productRepository.findAllAdmin();
    }

    @Override
    public Product updateEnable(Product product) {
        return productRepository.save(product);
    }

}
