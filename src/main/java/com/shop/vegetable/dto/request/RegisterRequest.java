package com.shop.vegetable.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class RegisterRequest {

    private String username;

    private String password;

    private String email;

    private String role;

    private String address;
   
    private String phone;
}
