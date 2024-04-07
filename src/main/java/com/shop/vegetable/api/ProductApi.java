package com.shop.vegetable.api;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    @GetMapping("/allAdmin")
    public ResponseEntity<List<Product>> findAllTypeAD() {
        return ResponseEntity.ok(productService.findAllAdmin());
    }

    @GetMapping("/typeAdmin")
    public ResponseEntity<?> findAllTypeliAD(@RequestParam(value = "id", required = false) Long id) {
        if (id == null) {
            List<Product> allProducts = productService.findAllAdmin();
            return ResponseEntity.ok(allProducts);
        } else {
            List<Product> productsByType = productService.findByTypeAdmin(id);
            return ResponseEntity.ok(productsByType);
        }
    }
    
    @GetMapping("/Disable/{id}")
    public ResponseEntity<Product> findAllTypeAAD(@PathVariable("id") Long id) {
        Product product=productService.getByIdNotDto(id);
        if(product.getStatus().equals("Enable")){
            product.setStatus("Disable");
            return ResponseEntity.ok(productService.updateEnable(product));
        }else{
            product.setStatus("Enable");
            return ResponseEntity.ok(productService.updateEnable(product));
        }
        
    }


    @GetMapping("/all/asc")
    public ResponseEntity<List<Product>> findAllTypeasc() {
        return ResponseEntity.ok(productService.findAllAdmin().stream()
                .sorted(Comparator.comparingDouble(Product::getPrice))
                .collect(Collectors.toList()));
    }

    @GetMapping("/all/desc")
    public ResponseEntity<List<Product>> findAllTypedesc() {
        return ResponseEntity.ok(productService.findAllAdmin().stream()
                .sorted(Comparator.comparingDouble(Product::getPrice).reversed())
                .collect(Collectors.toList()));
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

    @GetMapping("/type")
    public ResponseEntity<?> findAllTypeli(@RequestParam(value = "id", required = false) Long id) {
        if (id == null) {
            List<Product> allProducts = productService.findAll();
            return ResponseEntity.ok(allProducts);
        } else {
            List<Product> productsByType = productService.findByType(id);
            return ResponseEntity.ok(productsByType);
        }
    }

    @GetMapping("/find/{key}")
    public ResponseEntity<?> findAllTypei(@PathVariable String key) {
        List<Product> lt = productService.findName(key);
        return ResponseEntity.ok(lt);
    }

    @GetMapping("/page/{page}")
    public ResponseEntity<Page<ProductDto>> findTypeByPage(@PathVariable int page,
            @RequestParam(value = "id", required = false) Long id,
            @RequestParam(value = "sort", required = false) String sort,
            @RequestParam(value = "key", required = false) String key) {
        if (key != null) {
            List<ProductDto> productsByType = productService.allProduct().stream()
                    .filter(product -> product.getName().contains(key)
                            || product.getDescription().contains(key)
                            || String.valueOf(product.getPrice()).contains(key))
                    .collect(Collectors.toList());

            Page<ProductDto> products = productService.getAllProductsPage(page, productsByType);

            return ResponseEntity.ok(products);
        }
        if (sort != null) {

            if (sort.equals("asc")) {
                if (id == null) {

                    // Page<ProductDto> products = productService.getAllProducts(page);
                    // return ResponseEntity.ok(products);
                    List<ProductDto> productsByType = productService.allProduct().stream()
                            .sorted(Comparator.comparingDouble(ProductDto::getPrice))
                            .collect(Collectors.toList());

                    Page<ProductDto> products = productService.getAllProductsPage(page, productsByType);

                    return ResponseEntity.ok(products);
                } else {
                    List<ProductDto> productsByTypeH = productService.findByTypeDto(id).stream()
                            .sorted(Comparator.comparingDouble(ProductDto::getPrice))
                            .collect(Collectors.toList());

                    Page<ProductDto> products = productService.getAllProductsPage(page, productsByTypeH);
                    return ResponseEntity.ok(products);
                }
            }

            else if (sort.equals("desc")) {
                if (id == null) {

                    // Page<ProductDto> products = productService.getAllProducts(page);
                    // return ResponseEntity.ok(products);
                    List<ProductDto> productsByType = productService.allProduct().stream()
                            .sorted(Comparator.comparingDouble(ProductDto::getPrice).reversed())
                            .collect(Collectors.toList());

                    Page<ProductDto> products = productService.getAllProductsPage(page, productsByType);

                    return ResponseEntity.ok(products);
                } else {
                    List<ProductDto> productsByType = productService.findByTypeDto(id).stream()
                            .sorted(Comparator.comparingDouble(ProductDto::getPrice).reversed())
                            .collect(Collectors.toList());

                    Page<ProductDto> products = productService.getAllProductsPage(page, productsByType);
                    return ResponseEntity.ok(products);
                }
            }

        }

        if (id == null) {
            Page<ProductDto> products = productService.getAllProducts(page);
            return ResponseEntity.ok(products);
        } else {
            List<ProductDto> productsByType = productService.findByTypeDto(id);
            Page<ProductDto> products = productService.getAllProductsPage(page, productsByType);
            return ResponseEntity.ok(products);
        }

    }

    @GetMapping("/minmax/{page}")
    public ResponseEntity<Page<ProductDto>> findTypeByMinMax(@PathVariable int page,
            @RequestParam(value = "min", required = true) float min,
            @RequestParam(value = "max", required = true) float max) {
            List<ProductDto> productsByType = productService.allProduct().stream()
            .filter(product -> product.getPrice() <= max
                            && product.getPrice() >=min)
                    .collect(Collectors.toList());

            Page<ProductDto> products = productService.getAllProductsPage(page, productsByType);
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
