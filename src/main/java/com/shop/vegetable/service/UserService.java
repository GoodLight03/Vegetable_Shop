package com.shop.vegetable.service;

import java.util.List;
import java.util.Set;

import com.shop.vegetable.dto.UserDto;
import com.shop.vegetable.entity.Role;
import com.shop.vegetable.entity.Users;

public interface UserService {
    Users save(UserDto userDto);

    Users findByUsername(String username);

    boolean delete(long id);

    List<Users> findALl();

    List<Users> findByRole(List<Role> roles);

    Users saveAD();

    UserDto getCustomer(String username);

    Users update(UserDto dto);

    Users findbyId(Long id);

    Users changePass(UserDto customerDto);
}