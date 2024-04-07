package com.shop.vegetable.dto.response;

import java.util.List;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserInfoResponse {
    private long id;

    private String username;

    // private String email;

    private List<String> roles;
}
