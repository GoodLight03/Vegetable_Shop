package com.shop.vegetable.service;



import java.io.IOException;
import java.util.List;

import com.shop.vegetable.entity.Order;
import com.shop.vegetable.entity.OrderDetail;
import com.shop.vegetable.entity.ShoppingCart;

import jakarta.servlet.http.HttpServletResponse;

public interface OrderService {
    Order save(ShoppingCart shoppingCart);

    List<Order> findAll(String username);

    List<Order> findALlOrders();

    List<Object> getOrderByMonthvsYear();

    Order acceptOrder(Long id);

    void cancelOrder(Long id);

    List<OrderDetail> AllOrderDetail(Long id);

    void exportToExcel(List<Order> objectList, HttpServletResponse response) throws IOException;
}
