package com.example.transferAPI.controller;

import com.example.transferAPI.model.Account;
import com.example.transferAPI.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/accounts")
public class AccountController {
    @Autowired
    private AccountService accountService;

    @GetMapping
    public List<Account> getAllAccounts() {
        return accountService.getAllAccounts();
    }

    @PostMapping("/getById")
    public ResponseEntity<Account> getAccountById(@RequestBody AccountRequest accountRequest) {
        Account account = accountService.getAccountById(accountRequest.getId());
        if (account != null) {
            return ResponseEntity.ok(account);
        }
        return ResponseEntity.notFound().build();
    }
    @PostMapping
    public ResponseEntity<Account> createAccount(@RequestBody AccountRequest accountRequest) {
        Account createdAccount = accountService.createAccount(accountRequest.getCustomerId(), accountRequest.getAccount());
        if (createdAccount != null) {
            return ResponseEntity.ok(createdAccount);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/transfer")
    public ResponseEntity<Void> transferMoney(@RequestBody TransferRequest transferRequest) {
        boolean success = accountService.transferMoney(transferRequest.getFromAccountId(), transferRequest.getToAccountId(), transferRequest.getAmount());
        if (success) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAccount(@PathVariable Long id) {
        boolean deleted = accountService.deleteAccount(id);
        if (deleted) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }


}

