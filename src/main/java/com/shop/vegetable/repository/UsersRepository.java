package com.shop.vegetable.repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.shop.vegetable.entity.Role;
import com.shop.vegetable.entity.Users;

@Repository
public interface UsersRepository extends JpaRepository<Users, Long> {
    Users findByUsername(String username);

    @Query("select o from Users o where o.username = ?1")
    Users findByUsers(String name);

    @Query("select p from Users p")
    List<Users> findALl();

    // @Query("SELECT u FROM Users u WHERE u.roles IN :roles")
    // List<Users> findByRoles(List<Role> roles);
    @Query("SELECT u FROM Users u JOIN u.roles r WHERE r IN :roles")
    List<Users> findByRoles(@Param("roles") List<Role> roles);
}
