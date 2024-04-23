package com.shop.vegetable.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shop.vegetable.entity.OrderProcess;
import com.shop.vegetable.repository.OrderProcessRepository;
import com.shop.vegetable.service.OrderProcessService;
import com.shop.vegetable.service.OrderService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderProcessServiceImpl implements OrderProcessService{
    //@Autowired
    private final OrderProcessRepository orderProcessRepository;
    @Override
    public OrderProcess save(OrderProcess orderProcess) {
       return orderProcessRepository.save(orderProcess);
    }
    @Override
    public List<OrderProcess> findbyship(Long id) {
        return orderProcessRepository.findAllByShipperId(id);

    }
    
}
