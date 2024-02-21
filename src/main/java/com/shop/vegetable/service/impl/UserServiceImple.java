package com.shop.vegetable.service.impl;

import java.util.Arrays;
import java.util.List;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.shop.vegetable.dto.UserDto;

import com.shop.vegetable.entity.Users;
import com.shop.vegetable.repository.RoleRepository;
import com.shop.vegetable.repository.UsersRepository;
import com.shop.vegetable.service.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImple implements UserService{
    private final UsersRepository userRepository;
    private final RoleRepository roleRepository;

    private final BCryptPasswordEncoder passwordEncoder;

    @Override
    public Users save(UserDto userDto) {
        Users user = new Users();
        user.setUsername(userDto.getUsername());
        user.setAddress(userDto.getAddress());
        user.setPhone(userDto.getPhone());
        user.setPassword(userDto.getPassword());
        user.setRoles(Arrays.asList(roleRepository.findByName("CUSTOMER")));
        return userRepository.save(user);
    }

    @Override
    public Users saveAD() {
        Users user = new Users();
        user.setUsername("Admin");
        user.setAddress("Admin");
        user.setPhone("098765432");
        user.setPassword(passwordEncoder.encode("123456"));
        user.setRoles(Arrays.asList(roleRepository.findByName("ADMIN")));
        return userRepository.save(user);
    }

    
    @Override
    public Users findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public List<Users> findALl() {
        return userRepository.findALl();
    }

    // @Override
    // public Users findByUsers(String name) {
    //     return userRepository.findByUsers(name);
    // }

    // @Override
    // public List<Users> findGV() {
    //     return userRepository.findIdGV();
    // }

}
