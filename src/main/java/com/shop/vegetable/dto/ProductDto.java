package com.shop.vegetable.dto;

import com.shop.vegetable.entity.Type;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto {
    private Long id;
    // @Size(min = 3, max = 10, message = "User name contains 3-10 characters")
    private String name;
    private String description;
    private float price;
    private String image;
    private Type type;
}
