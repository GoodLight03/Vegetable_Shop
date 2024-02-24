package com.shop.vegetable.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.shop.vegetable.entity.Order;
import com.shop.vegetable.entity.OrderDetail;

import java.util.List;

@Repository
public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long> {
    @Query("select o from Order o where o.users.id = ?1")
    List<Order> findAllByCustomerId(Long id);

    @Query("select o from OrderDetail o where o.order.id = ?1")
    List<OrderDetail> findAllByOrderId(Long id);
}
