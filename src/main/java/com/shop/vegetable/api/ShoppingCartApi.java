package com.shop.vegetable.api;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shop.vegetable.dto.ProductDto;
import com.shop.vegetable.dto.TypeDto;
import com.shop.vegetable.entity.ShoppingCart;
import com.shop.vegetable.entity.Type;
import com.shop.vegetable.service.ShoppingCartService;
import com.shop.vegetable.service.TypeService;

import jakarta.persistence.EntityNotFoundException;
@RestController
@RequestMapping("api/cart")
public class ShoppingCartApi {
    @Autowired
    private ShoppingCartService shoppingCartService;

    // @GetMapping("/all")
    // public ResponseEntity<List<ShoppingCart>> findAllType() {
    //     return ResponseEntity.ok(shoppingCartService.findAll());
    // }

    @GetMapping("/all/{name}")
    public ResponseEntity<ShoppingCart> findCartById(@PathVariable String name) {
        ShoppingCart m = shoppingCartService.getCart(name);
        if (m!=null) {
            return ResponseEntity.ok(m);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);

        }

    }

    @PostMapping("/save")
    public ResponseEntity<ShoppingCart> addCart(@RequestBody ShoppingCart shoppingCart) {
        ShoppingCart mcd = shoppingCartService.saveCart(shoppingCart);
        try {
            return ResponseEntity.created(new URI("/api/cart/save/" + mcd.getId())).body(mcd);

        } catch (URISyntaxException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PutMapping("/all/{name}")
    public ResponseEntity<Void> updateCart(@RequestBody ProductDto productDto,@RequestBody int quantity, @PathVariable String name) {
        try {
            shoppingCartService.updateCart(productDto,quantity,name);
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
    public ResponseEntity<Void> deleteCartById(@PathVariable long id) {
        try {
            shoppingCartService.deleteCartById(id);
            return ResponseEntity.ok().build();
        } catch (EntityNotFoundException ex) {
            return ResponseEntity.notFound().build();
        }
    }
}
