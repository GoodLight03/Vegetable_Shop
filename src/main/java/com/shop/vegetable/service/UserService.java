package com.shop.vegetable.service;

import java.util.List;

import com.shop.vegetable.dto.UserDto;
import com.shop.vegetable.entity.Users;

public interface UserService {
    Users save(UserDto userDto);

    Users findByUsername(String username);

    //Users findByUsers(String name);

    List<Users> findALl();
    // List<Users> findGV();

    Users saveAD();

    UserDto getCustomer(String username);

    Users update(UserDto dto);

    Users changePass(UserDto customerDto);
}