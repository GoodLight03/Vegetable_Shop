package com.shop.vegetable.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.shop.vegetable.entity.Users;

@Repository
public interface UsersRepository extends JpaRepository<Users, Long> {
    Users findByUsername(String username);

    @Query("select o from Users o where o.username = ?1")
    Users findByUsers(String name);

    @Query("select p from Users p")
    List<Users> findALl();
    // @Query("select p from Users p where p.roles.role_id=3")
    // @Query("SELECT u FROM Users u JOIN u.roles r WHERE r.role_id = 3")
    // List<Users> findIdGV();
}
