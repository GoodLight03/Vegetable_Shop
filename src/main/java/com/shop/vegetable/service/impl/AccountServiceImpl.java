// package com.shop.vegetable.service.impl;

// import lombok.RequiredArgsConstructor;
// import org.springframework.security.authentication.AuthenticationManager;
// import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
// import org.springframework.security.crypto.password.PasswordEncoder;
// import org.springframework.stereotype.Service;

// import com.shop.vegetable.config.security_session.UsersDetails;
// import com.shop.vegetable.dto.reponse.MessageResponse;
// import com.shop.vegetable.dto.reponse.TokenResponse;
// import com.shop.vegetable.dto.request.LoginRequest;
// import com.shop.vegetable.dto.request.RegisterRequest;
// import com.shop.vegetable.entity.Role;
// import com.shop.vegetable.entity.Users;
// import com.shop.vegetable.repository.RoleRepository;
// import com.shop.vegetable.repository.UsersRepository;
// import com.shop.vegetable.service.AccountService;
// import com.shop.vegetable.utils.JwtService;

// import java.util.Arrays;
// import java.util.Optional;

// @Service
// @RequiredArgsConstructor
// public class AccountServiceImpl implements AccountService {

//     private final UsersRepository accountRepository;

//     private final RoleRepository roleRepository;

//     private final PasswordEncoder passwordEncoder;

//     private final JwtService jwtService;

//     private final AuthenticationManager authenticationManager;

//     @Override
//     public TokenResponse logink(LoginRequest loginRequest) {
//         authenticationManager.authenticate(
//                 new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
//         Users findByAccount = accountRepository.findByUsername(loginRequest.getUsername());
//         String jwtToken = jwtService.generateToken(new UsersDetails(findByAccount));
//         return TokenResponse
//                 .builder()
//                 .token(jwtToken)
//                 .roles(findByAccount.getRoles())
//                 .build();
//     }

//     @Override
//     public MessageResponse register(RegisterRequest registerRequest) {
//         //Optional<Role> findByRole =roleRepository.findByName(registerRequest.getRole());
//         Users account = Users
//                 .builder()
//                 .username(registerRequest.getUsername())
//                 .password(passwordEncoder.encode(registerRequest.getPassword()))
//                 .address(registerRequest.getAddress())
//                 .phone(registerRequest.getPhone())
//                 .roles(Arrays.asList(roleRepository.findByName("CUSTOMER")))
//                 .build();

//         // Users user = new Users();
//         // user.setUsername(registerRequest.getUsername());
//         // user.setAddress(registerRequest.getAddress());
//         // user.setPhone(registerRequest.getPhone());
//         // user.setPassword(registerRequest.getPassword());
//         // user.setRoles(Arrays.asList(roleRepository.findByName("CUSTOMER")));
//        // return userRepository.save(user);
//         accountRepository.save(account);
//         return MessageResponse.builder().message("Đăng ký thành công").build();
//     }

// }
