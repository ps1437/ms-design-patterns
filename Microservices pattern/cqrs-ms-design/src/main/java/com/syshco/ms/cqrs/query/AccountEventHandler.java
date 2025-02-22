package com.syshco.ms.cqrs.query;

import com.syshco.ms.cqrs.event.AccountCreatedEvent;
import com.syshco.ms.cqrs.event.MoneyDepositedEvent;
import com.syshco.ms.cqrs.event.MoneyWithdrawnEvent;
import com.syshco.ms.cqrs.query.model.AccountView;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AccountEventHandler {
    private final AccountViewRepository repository;

    public AccountEventHandler(AccountViewRepository repository) {
        this.repository = repository;
    }

    @EventHandler
    public void on(AccountCreatedEvent event) {
        repository.save(new AccountView(event.accountId(), event.balance()));
    }

    @EventHandler
    public void on(MoneyDepositedEvent event) {
        AccountView account = repository.findById(event.accountId()).orElseThrow();
        account = new AccountView(account.getAccountId(), account.getBalance().add(event.amount()));
        repository.save(account);
    }

    @EventHandler
    public void on(MoneyWithdrawnEvent event) {
        AccountView account = repository.findById(event.accountId()).orElseThrow();
        account = new AccountView(account.getAccountId(), account.getBalance().subtract(event.amount()));
        repository.save(account);
    }
}
