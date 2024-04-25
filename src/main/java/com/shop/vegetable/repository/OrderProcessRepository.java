package com.shop.vegetable.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.shop.vegetable.entity.OrderProcess;

@Repository
public interface OrderProcessRepository extends JpaRepository<OrderProcess, Long> {
    @Query("select o from OrderProcess o where o.users.id = ?1 and o.order.id= ?2")
    List<OrderProcess> findAllByShipperId(Long id,Long idOr);
}
