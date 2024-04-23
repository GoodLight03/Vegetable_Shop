package com.shop.vegetable.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shop.vegetable.entity.OrderProcess;
import com.shop.vegetable.service.OrderProcessService;

@RestController
@RequestMapping("api/orderprocess")
public class OrderProcessApi {
    @Autowired
    private OrderProcessService orderProcessService;
    
    @GetMapping("/all/{id}")
    public ResponseEntity<?> findAllOrder(@PathVariable Long id) {
        return ResponseEntity.ok(orderProcessService.findbyship(id));
    }
}
