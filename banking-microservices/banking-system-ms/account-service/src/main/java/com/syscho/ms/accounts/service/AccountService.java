package com.syscho.ms.accounts.service;

import com.syscho.ms.accounts.domain.Account;
import com.syscho.ms.accounts.domain.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }

    public Optional<Account> getAccountByNumber(String accountNumber) {
        return accountRepository.findByAccountNumber(accountNumber);
    }

    public Account createAccount(Account account) {
        return accountRepository.save(account);
    }

}