package com.shop.vegetable.config.security_session;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.shop.vegetable.entity.Users;
import com.shop.vegetable.repository.UsersRepository;

import java.util.stream.Collectors;

public class UsersServiceConfig implements UserDetailsService {
    @Autowired
    private UsersRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("Could not find username");
        }
        return new User(
            user.getUsername(),
            user.getPassword(),
            user.getRoles()
                        .stream()
                        .map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList()));
    }
}
