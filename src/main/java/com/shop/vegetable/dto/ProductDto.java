package com.shop.vegetable.dto;

import java.io.Serializable;

import org.springframework.web.multipart.MultipartFile;

import com.shop.vegetable.entity.Type;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto implements Serializable{
    private Long id;
    @Size(min = 3, max = 10, message = "User name contains 3-10 characters")
    private String name;
    private String description;
    private float price;
    private String image;
    private Type type;

    private MultipartFile file;
}
