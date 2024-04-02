package com.shop.vegetable.api;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.shop.vegetable.dto.ProductDto;
import com.shop.vegetable.dto.TypeDto;
import com.shop.vegetable.entity.Product;
import com.shop.vegetable.entity.Type;
import com.shop.vegetable.service.ProductService;
import com.shop.vegetable.service.TypeService;

import jakarta.persistence.EntityNotFoundException;

@RestController
@RequestMapping("api/product")
public class ProductApi {
    @Autowired
    private ProductService productService;

    @GetMapping("/all")
    public ResponseEntity<List<Product>> findAllType() {
        return ResponseEntity.ok(productService.findAll());
    }

    @GetMapping("/all/{id}")
    public ResponseEntity<Product> findTypeById(@PathVariable long id) {
        Optional<Product> m = productService.findById(id);
        if (m.isPresent()) {
            return ResponseEntity.ok(m.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);

        }

    }

    @GetMapping("/page/{page}")
    public ResponseEntity<Page<ProductDto>> findTypeByPage(@PathVariable int page) {
        Page<ProductDto> products = productService.getAllProducts(page);

        return ResponseEntity.ok(products);

    }

    @PostMapping("/save")
    public ResponseEntity<Product> addType(@ModelAttribute ProductDto productDto) {
        Product mcd = productService.save(productDto.getFile(), productDto);
        try {
            return ResponseEntity.created(new URI("/api/product/save/" + mcd.getId())).body(mcd);

        } catch (URISyntaxException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PutMapping("/all")
    public ResponseEntity<Void> updateType(@ModelAttribute ProductDto productDto) {
        try {
            productService.update(productDto.getFile(), productDto);
            return ResponseEntity.ok().build();
        } catch (EntityNotFoundException ex) {
            return ResponseEntity.notFound().build();
        }
    }

    // @PatchMapping("/all/{id}")
    // public ResponseEntity<Void> updateMedicineName(@RequestBody String
    // nameString, @PathVariable long id) {
    // try {
    // typeService.updateName(id, nameString);
    // return ResponseEntity.ok().build();
    // } catch (EntityNotFoundException ex) {
    // return ResponseEntity.notFound().build();
    // }
    // }

    @DeleteMapping(path = "/all/{id}")
    public ResponseEntity<Void> deleteTypeById(@PathVariable long id) {
        try {
            productService.delete(id);
            return ResponseEntity.ok().build();
        } catch (EntityNotFoundException ex) {
            return ResponseEntity.notFound().build();
        }
    }

}
