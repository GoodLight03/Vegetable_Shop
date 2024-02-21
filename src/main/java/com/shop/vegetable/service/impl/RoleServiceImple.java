package com.shop.vegetable.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.shop.vegetable.dto.RoleDto;
import com.shop.vegetable.entity.Role;
import com.shop.vegetable.repository.RoleRepository;
import com.shop.vegetable.service.RoleService;

import lombok.RequiredArgsConstructor;
@Service
@RequiredArgsConstructor
public class RoleServiceImple implements RoleService{
    private final RoleRepository roleRepository;

    @Override
    public Role save(RoleDto roleDto) {
        Role rl=new Role();
        rl.setName(roleDto.getName());
        return roleRepository.save(rl);
    }

    @Override
    public List<Role> findALl() {
        // TODO Auto-generated method stub
        return roleRepository.findALl();
    }
    
}
