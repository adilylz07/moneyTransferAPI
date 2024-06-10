package com.example.transferAPI.repository;

import com.example.transferAPI.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {
}
