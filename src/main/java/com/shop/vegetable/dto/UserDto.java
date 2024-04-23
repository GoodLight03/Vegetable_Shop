package com.shop.vegetable.dto;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    @Size(min = 3, max = 10, message = "User name contains 3-10 characters")
    private String username;
    @Size(min = 3, max = 10, message = "Address contains 3-10 characters")
    private String address;
    @Size(min= 10, max = 11, message = "Phone contains 10- 11 characters")
    @Pattern(regexp = "^0[0-9]{9,10}$", message = "Phone number must start with 0 and have a length of 10-11 characters")
    private String phone;
  
    @Size(min = 3, max = 15, message = "Password contains 3-10 characters")
    private String password;
    private String repeatPassword;
    private String role;

}
