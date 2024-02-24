package com.shop.vegetable.dto;

import java.util.Date;

import com.shop.vegetable.entity.Product;
import com.shop.vegetable.entity.Users;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CommentDto {
    private Long id;
    private String content;
    private Date commentDate;
    private int start;

    private Users users;

    private Product product;
}
