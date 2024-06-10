package com.example.transferAPI.controller;

import com.example.transferAPI.model.Account;
import lombok.Data;

@Data
public class AccountRequest {
    private Long id;
    private Long customerId;
    private Account account;
}
