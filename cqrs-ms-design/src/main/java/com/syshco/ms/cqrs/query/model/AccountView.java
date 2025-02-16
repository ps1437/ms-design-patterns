package com.syshco.ms.cqrs.query.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import java.math.BigDecimal;

@Entity
public class AccountView {
    @Id
    private String accountId;
    private BigDecimal balance;

    public AccountView() {}

    public AccountView(String accountId, BigDecimal balance) {
        this.accountId = accountId;
        this.balance = balance;
    }

    public String getAccountId() { return accountId; }
    public BigDecimal getBalance() { return balance; }
}