package com.shop.vegetable.service.impl;

import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Set;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.shop.vegetable.dto.UserDto;
import com.shop.vegetable.entity.Role;
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
        user.setRoles(Arrays.asList(roleRepository.findByName(userDto.getRole())));
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

    @Override
    public UserDto getCustomer(String username) {
        UserDto customerDto = new UserDto();
        Users customer = userRepository.findByUsername(username);
        customerDto.setUsername(customer.getUsername());
        customerDto.setPassword(customer.getPassword());
        customerDto.setAddress(customer.getAddress());
        customerDto.setPhone(customer.getPhone());
        return customerDto;
    }

    @Override
    public Users update(UserDto dto) {
        Users customer = userRepository.findByUsername(dto.getUsername());
        //customer.setId(customer.getId());
        //customer.setUsername(dto.getUsername());
        customer.setAddress(dto.getAddress());
        customer.setPhone(dto.getPhone());
        return userRepository.save(customer);
    }

    @Override
    public Users changePass(UserDto customerDto) {
        Users customer = userRepository.findByUsername(customerDto.getUsername());
        customer.setPassword(customerDto.getPassword());
        return userRepository.save(customer);
    }

    @Override
    public boolean delete(long id) {
        Users existMember = userRepository.findById(id).orElse(null);
        if (existMember == null) {
            return false;
        }
        userRepository.deleteById(id);
        return true;
    }

    @Override
    public List<Users> findByRole(List<Role> roles) {
        return userRepository.findByRoles(roles);
    }



    @Override
    public Users findbyId(Long id) {
        return userRepository.findById(id).get();
    }

}
