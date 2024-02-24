package com.shop.vegetable.service;

import com.shop.vegetable.entity.Order;
import com.shop.vegetable.entity.ShoppingCart;

public interface CommentService {
    Order save(ShoppingCart shoppingCart);
}
