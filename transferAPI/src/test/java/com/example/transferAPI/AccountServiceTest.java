package com.example.transferAPI;

import com.example.transferAPI.model.Account;
import com.example.transferAPI.model.Customer;
import com.example.transferAPI.repository.AccountRepository;
import com.example.transferAPI.repository.CustomerRepository;
import com.example.transferAPI.service.AccountService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class AccountServiceTest {

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private AccountService accountService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllAccounts() {
        List<Account> accounts = new ArrayList<>();
        accounts.add(new Account(1L, 1000.0, new Customer()));

        when(accountRepository.findAll()).thenReturn(accounts);

        List<Account> result = accountService.getAllAccounts();
        assertEquals(1, result.size());
        assertEquals(1000.0, result.get(0).getBalance());
    }

    @Test
    void getAccountById() {
        Account account = new Account(1L, 1000.0, new Customer());

        when(accountRepository.findById(anyLong())).thenReturn(Optional.of(account));

        Account result = accountService.getAccountById(1L);
        assertNotNull(result);
        assertEquals(1000.0, result.getBalance());
    }

    @Test
    void createAccount() {
        Customer customer = new Customer(1L, "John Doe", "john.doe@example.com", new ArrayList<>());
        Account account = new Account(null, 1000.0, customer);
        Account createdAccount = new Account(1L, 1000.0, customer);

        when(customerRepository.findById(anyLong())).thenReturn(Optional.of(customer));
        when(accountRepository.save(any(Account.class))).thenReturn(createdAccount);

        Account result = accountService.createAccount(1L, account);
        assertNotNull(result);
        assertEquals(1000.0, result.getBalance());
        assertEquals(customer, result.getCustomer());
    }

    @Test
    void transferMoney() {
        Account fromAccount = new Account(1L, 1000.0, new Customer());
        Account toAccount = new Account(2L, 500.0, new Customer());

        when(accountRepository.findById(1L)).thenReturn(Optional.of(fromAccount));
        when(accountRepository.findById(2L)).thenReturn(Optional.of(toAccount));

        boolean success = accountService.transferMoney(1L, 2L, 500.0);
        assertTrue(success);
        assertEquals(500.0, fromAccount.getBalance());
        assertEquals(1000.0, toAccount.getBalance());

        verify(accountRepository, times(1)).save(fromAccount);
        verify(accountRepository, times(1)).save(toAccount);
    }

    @Test
    void deleteAccount() {
        Account account = new Account(1L, 1000.0, new Customer());

        when(accountRepository.findById(1L)).thenReturn(Optional.of(account));

        boolean success = accountService.deleteAccount(1L);
        assertTrue(success);

        verify(accountRepository, times(1)).delete(account);
    }

    @Test
    void deleteAccountNotFound() {
        when(accountRepository.findById(1L)).thenReturn(Optional.empty());

        boolean success = accountService.deleteAccount(1L);
        assertFalse(success);

        verify(accountRepository, never()).delete(any(Account.class));
    }
}
