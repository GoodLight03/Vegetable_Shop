package com.shop.vegetable.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.shop.vegetable.entity.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    @Query("select p from Product p where p.status='Enable'")
    List<Product> findAll();

    @Query("select p from Product p")
    List<Product> findAllAdmin();

    @Query("select p from Product p where p.name like %?1%")
    List<Product> findByName(String name);

    @Query("select p from Product p where p.name like %?1% or p.description like %?1%")
    List<Product> findAllByNameOrDescription(String keyword);

    @Query("select p from Product p where p.type.id = ?1 and p.status='Enable'")
    List<Product> findByType(Long id);

    @Query("select p from Product p where p.type.id = ?1")
    List<Product> findByTypeAdmin(Long id);
}
