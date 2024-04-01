package com.shop.vegetable.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class TransactionStatusDto implements Serializable{
    private String status;
    private String message;
    private String data;
}
