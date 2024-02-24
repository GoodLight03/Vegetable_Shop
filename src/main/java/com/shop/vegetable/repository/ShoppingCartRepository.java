package com.shop.vegetable.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.shop.vegetable.entity.ShoppingCart;



@Repository
public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, Long> {
}
