package com.shop.vegetable.api;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.shop.vegetable.dto.TypeDto;
import com.shop.vegetable.entity.Order;
import com.shop.vegetable.entity.OrderProcess;
import com.shop.vegetable.entity.ShoppingCart;
import com.shop.vegetable.entity.Type;
import com.shop.vegetable.entity.Users;
import com.shop.vegetable.service.OrderProcessService;
import com.shop.vegetable.service.OrderService;
import com.shop.vegetable.service.TypeService;
import com.shop.vegetable.service.UserService;

import jakarta.persistence.EntityNotFoundException;
@RestController
@RequestMapping("api/order")
public class OrderApi {
    @Autowired
    private OrderService orderService;

    @Autowired
    private UserService userService;

    @Autowired
    private OrderProcessService orderProcessService;

    

    @GetMapping("/all")
    public ResponseEntity<?> findAllOrder(@RequestParam(value = "idShip", required = false) Long idShip) {
        if(idShip!=null){
            return ResponseEntity.ok(orderService.findAllByShipperId(idShip));
        }
        return ResponseEntity.ok(orderService.findALlOrders());
    }

    // @GetMapping("/all/{id}")
    // public ResponseEntity<Type> findTypeById(@PathVariable long id) {
    //     Optional<Type> m = typeService.findById(id);
    //     if (m.isPresent()) {
    //         return ResponseEntity.ok(m.get());
    //     } else {
    //         return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);

    //     }

    // }

    @PostMapping("/save")
    public ResponseEntity<Order> addOrder(@RequestBody ShoppingCart shoppingCart) {
        Order mcd = orderService.save(shoppingCart);
        try {
            return ResponseEntity.created(new URI("/api/order/save/" + mcd.getId())).body(mcd);

        } catch (URISyntaxException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    // @PutMapping("/all/{id}")
    // public ResponseEntity<Void> updateType(@RequestBody Type type, @PathVariable long id) {
    //     try {
    //         orderService.update(type);
    //         return ResponseEntity.ok().build();
    //     } catch (EntityNotFoundException ex) {
    //         return ResponseEntity.notFound().build();
    //     }
    // }

    @PatchMapping("/all/{id}")
    public ResponseEntity<Void> updateStatus(@RequestParam String status,@RequestParam(value = "idShip", required = false) Long idShip, @PathVariable long id) {
        if(idShip!=null){
            Users user=userService.findbyId(idShip);
            Order order=orderService.findbyId(id);
            OrderProcess orderProcess=new OrderProcess();
            orderProcess.setUsers(user);
            orderProcess.setOrder(order);
            orderProcess.setStatus(status);
            orderProcess.setDescription("The order has been delivered to the shipping unit");
            orderProcess.setProcessday(new Date());
            orderProcessService.save(orderProcess);
            orderService.updateStatus(id, status,idShip);
            return ResponseEntity.ok().build();
        }
        
        orderService.updateStatus(id, status,null);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping(path = "/all/{id}")
    public ResponseEntity<Void> deleteOrderById(@PathVariable long id) {
        try {
            orderService.cancelOrder(id);
            return ResponseEntity.ok().build();
        } catch (EntityNotFoundException ex) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/report")
	public ResponseEntity<List<Object>> test() {
		return ResponseEntity.ok(orderService.getOrderByMonthvsYear());
	}

}
