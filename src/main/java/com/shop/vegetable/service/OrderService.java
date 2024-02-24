package com.shop.vegetable.service;



import java.util.List;

import com.shop.vegetable.entity.Order;
import com.shop.vegetable.entity.OrderDetail;
import com.shop.vegetable.entity.ShoppingCart;

public interface OrderService {
    Order save(ShoppingCart shoppingCart);

    List<Order> findAll(String username);

    List<Order> findALlOrders();

    Order acceptOrder(Long id);

    void cancelOrder(Long id);

    List<OrderDetail> AllOrderDetail(Long id);
}
