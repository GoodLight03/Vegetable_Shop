package com.shop.vegetable.dto.reponse;

import java.util.List;

import com.shop.vegetable.entity.Role;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class TokenResponse {

    private String token;

    private List<Role> roles;
}
