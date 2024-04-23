package com.shop.vegetable.service;

import java.util.List;

import com.shop.vegetable.entity.OrderProcess;

public interface OrderProcessService {
    OrderProcess save(OrderProcess orderProcess);
    List<OrderProcess> findbyship(Long id);
}
