package com.example.transferAPI.controller;

import lombok.Data;

@Data
public class TransferRequest {
    private Long fromAccountId;
    private Long toAccountId;
    private Double amount;
}
