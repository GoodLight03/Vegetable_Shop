package com.shop.vegetable.service;

import com.shop.vegetable.dto.ProductDto;
import com.shop.vegetable.dto.ShoppingCartDto;
import com.shop.vegetable.entity.ShoppingCart;

public interface ShoppingCartService {
    ShoppingCart addItemToCart(ProductDto productDto, int quantity, String username);

    ShoppingCart updateCart(ProductDto productDto, int quantity, String username);

    ShoppingCart removeItemFromCart(ProductDto productDto, String username);

    ShoppingCartDto addItemToCartSession(ShoppingCartDto cartDto, ProductDto productDto, int quantity);

    ShoppingCartDto updateCartSession(ShoppingCartDto cartDto, ProductDto productDto, int quantity);

    ShoppingCartDto removeItemFromCartSession(ShoppingCartDto cartDto, ProductDto productDto, int quantity);

    ShoppingCart combineCart(ShoppingCartDto cartDto, ShoppingCart cart);

    void deleteCartById(Long id);

    ShoppingCart FindById(Long id);

    ShoppingCart getCart(String username);

    ShoppingCart saveCart(ShoppingCart shoppingCart);
}
