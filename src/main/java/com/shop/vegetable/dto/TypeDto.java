package com.shop.vegetable.dto;


import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TypeDto {
    private long id;
    @Size(min = 3, max = 10, message = "Type name contains 3-10 characters")
    private String name;

    private String image;
}
