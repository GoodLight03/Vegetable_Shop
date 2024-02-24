package com.shop.vegetable.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

import com.shop.vegetable.entity.Users;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ShoppingCartDto {
    private Long id;

    private Users users;

    private double totalPrice;

    private int totalItems;

    private Set<CartItemDto> cartItems;

}
