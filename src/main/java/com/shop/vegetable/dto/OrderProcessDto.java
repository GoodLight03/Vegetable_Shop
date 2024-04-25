package com.shop.vegetable.dto;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.shop.vegetable.entity.Order;
import com.shop.vegetable.entity.Users;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonSerialize
public class OrderProcessDto{
    private Long idUs;
    private Long idOr;

    private Date processday;
    private String status;
    private String description;
    
    private Users users;
    
    private Order order;
}
