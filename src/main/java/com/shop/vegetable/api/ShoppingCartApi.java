package com.shop.vegetable.api;

import java.net.URI;
import java.net.URISyntaxException;
import java.security.Principal;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.shop.vegetable.dto.ProductDto;
import com.shop.vegetable.dto.TypeDto;
import com.shop.vegetable.entity.ShoppingCart;
import com.shop.vegetable.entity.Type;
import com.shop.vegetable.entity.Users;
import com.shop.vegetable.service.ProductService;
import com.shop.vegetable.service.ShoppingCartService;
import com.shop.vegetable.service.TypeService;
import com.shop.vegetable.service.UserService;

import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
@RestController
@RequestMapping("api/cart")
public class ShoppingCartApi {
    @Autowired
    private ShoppingCartService shoppingCartService;

    @Autowired
    private  ProductService productService;

    @Autowired
    private  ShoppingCartService cartService;

    @Autowired
    private UserService usersService;


    @GetMapping("/all")
    public ResponseEntity<ShoppingCart> findAllType(Principal principal, HttpSession session) {
        if (principal == null) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        } 
        Users Users = usersService.findByUsername(principal.getName());
        ShoppingCart cart = Users.getCart();
        if (cart == null) {
            cart=new ShoppingCart();
            cart.setUsers(Users);
            cartService.saveCart(cart);
            return ResponseEntity.ok(cart);

        }else{
            return ResponseEntity.ok(cart);
        }
    }


    @GetMapping("/all/{name}")
    public ResponseEntity<ShoppingCart> findCartById(@PathVariable String name) {
        ShoppingCart m = shoppingCartService.getCart(name);
        if (m!=null) {
            return ResponseEntity.ok(m);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);

        }

    }

    @PostMapping("/add-to-cart")
    public ResponseEntity<Void> addItemToCart(@RequestParam("id") Long id,
            @RequestParam(value = "quantity", required = false, defaultValue = "1") int quantity,
            HttpServletRequest request,
            Principal principal,
            HttpSession session) {

        ProductDto productDto = productService.getById(id);
        if (principal == null) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        String username = principal.getName();
        ShoppingCart shoppingCart = cartService.addItemToCart(productDto, quantity, username);
        session.setAttribute("totalItems", shoppingCart.getTotalItems());
        return ResponseEntity.ok().build();
    }
    
    @GetMapping("/update")
    public ResponseEntity<ShoppingCart> updateCart(@RequestParam("id") Long id,
    @RequestParam("quantity") int quantity,
    HttpServletRequest request,
    Principal principal,
    HttpSession session) {
        try {
            ProductDto productDto = productService.getById(id);
            String username = principal.getName();
            ShoppingCart shoppingCart = cartService.updateCart(productDto, quantity, username);
            return ResponseEntity.ok(shoppingCart);
        } catch (EntityNotFoundException ex) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<ShoppingCart> deleteCartById(@RequestParam("id") Long id,Principal principal) {
        try {
            ProductDto productDto = productService.getById(id);
            String username = principal.getName();
            ShoppingCart shoppingCart = cartService.removeItemFromCart(productDto, username);
            return ResponseEntity.ok(shoppingCart);
        } catch (EntityNotFoundException ex) {
            return ResponseEntity.notFound().build();
        }
    }
}
