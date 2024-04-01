package com.shop.vegetable.service;

import java.util.List;

import com.shop.vegetable.dto.RoleDto;
import com.shop.vegetable.entity.Role;

public interface RoleService {
    Role save(Role role);

    List<Role> findALl();

    Role findByNameRole(String name);
} 