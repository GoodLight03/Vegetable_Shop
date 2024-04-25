package com.shop.vegetable.api;


import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shop.vegetable.dto.OrderProcessDto;
import com.shop.vegetable.entity.OrderProcess;
import com.shop.vegetable.service.OrderProcessService;
import com.shop.vegetable.service.OrderService;
import com.shop.vegetable.service.UserService;

import io.swagger.v3.oas.annotations.parameters.RequestBody;

@RestController
@RequestMapping("api/orderprocess")
public class OrderProcessApi {
    @Autowired
    private OrderProcessService orderProcessService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private UserService userService;
    
    @GetMapping("/all/{id}/{idOr}")
    public ResponseEntity<?> findAllOrder(@PathVariable Long id, @PathVariable Long idOr) {
        return ResponseEntity.ok(orderProcessService.findbyship(id,idOr));
    }

    @PostMapping("/save")
    public ResponseEntity<OrderProcess> addType(@RequestBody OrderProcessDto orderProcessDto) {
       OrderProcess orderProcess=new OrderProcess();
       orderProcess.setProcessday(new Date());
       orderProcess.setDescription(orderProcessDto.getDescription());
       orderProcess.setStatus(orderProcessDto.getStatus());
       orderProcess.setOrder(orderService.findbyId(orderProcessDto.getIdOr()));
       orderProcess.setUsers(userService.findbyId(orderProcessDto.getIdUs()));
       orderService.updateStatus(orderProcessDto.getIdOr(), orderProcessDto.getStatus(),orderProcessDto.getIdUs());
       orderProcessService.save(orderProcess);
       return ResponseEntity.ok().build();
    }
}
