package com.example.transferAPI.service;

import com.example.transferAPI.model.Account;
import com.example.transferAPI.model.Customer;
import com.example.transferAPI.repository.AccountRepository;
import com.example.transferAPI.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.logging.Logger;

    @Service
    public class AccountService {
        private static final Logger logger = Logger.getLogger(AccountService.class.getName());

        @Autowired
        private AccountRepository accountRepository;

        @Autowired
        private CustomerRepository customerRepository;

        public List<Account> getAllAccounts() {
            logger.info("Fetching all accounts");
            return accountRepository.findAll();
        }

        public Account getAccountById(Long id) {
            return accountRepository.findById(id).orElse(null);
        }

        public Account createAccount(Long customerId, Account account) {
            Customer customer = customerRepository.findById(customerId).orElse(null);
            if (customer != null) {
                account.setCustomer(customer);
                customer.getAccounts().add(account);
                return accountRepository.save(account);
            }
            return null;
        }

        @Transactional
        public boolean transferMoney(Long fromAccountId, Long toAccountId, Double amount) {
            Account fromAccount = accountRepository.findById(fromAccountId).orElse(null);
            Account toAccount = accountRepository.findById(toAccountId).orElse(null);

            if (fromAccount != null && toAccount != null && fromAccount.getBalance() >= amount) {
                fromAccount.setBalance(fromAccount.getBalance() - amount);
                toAccount.setBalance(toAccount.getBalance() + amount);
                accountRepository.save(fromAccount);
                accountRepository.save(toAccount);
                return true;
            }
            return false;
        }

        @Transactional
        public boolean deleteAccount(Long id) {
            Account account = accountRepository.findById(id).orElse(null);
            if (account != null) {
                accountRepository.delete(account);
                return true;
            }
            return false;
        }




}
