package com.example.transferAPI.controller;

import com.example.transferAPI.model.Account;
import lombok.Data;

@Data
public class CustomerRequest {
    private Long id;
    private String name;
    private String email;
}
