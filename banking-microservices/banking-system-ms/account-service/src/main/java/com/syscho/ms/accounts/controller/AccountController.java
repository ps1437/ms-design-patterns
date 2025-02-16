package com.syscho.ms.accounts.controller;

import com.syscho.ms.commons.model.AccountCreateRequest;
import com.syscho.ms.commons.model.AccountResponse;
import com.syscho.ms.accounts.domain.Account;
import com.syscho.ms.accounts.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/accounts")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @GetMapping
    public List<Account> getAllAccounts() {
        return accountService.getAllAccounts();
    }

    @GetMapping("/{accountNumber}")
    public Optional<Account> getAccountByNumber(@PathVariable String accountNumber) {
        return accountService.getAccountByNumber(accountNumber);
    }


    @PostMapping("/create")
    public AccountResponse createAccount(@RequestBody AccountCreateRequest request) {
        String accountNumber = generateAccountNumber();

        Account account = mapToAccount(request, accountNumber);

        Account savedAccount = accountService.createAccount(account);

        return new AccountResponse(savedAccount.getId(), savedAccount.getAccountNumber(),
                savedAccount.getAccountHolderName(), savedAccount.getBalance());
    }

    private static Account mapToAccount(AccountCreateRequest request, String accountNumber) {
        Account account = new Account();
        account.setAccountNumber(accountNumber);
        account.setAccountHolderName(request.accountHolderName());
        account.setBalance(0.0);
        return account;
    }

    private static String generateAccountNumber() {
        return "ACC" + UUID.randomUUID().toString().substring(0, 8).toUpperCase(Locale.ROOT);
    }

}
