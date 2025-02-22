package com.syshco.ms.cqrs.command.aggregate;

import com.syshco.ms.cqrs.command.model.WithdrawMoneyCommand;
import com.syshco.ms.cqrs.event.AccountCreatedEvent;
import com.syshco.ms.cqrs.event.MoneyDepositedEvent;
import com.syshco.ms.cqrs.command.model.CreateAccountCommand;
import com.syshco.ms.cqrs.command.model.DepositMoneyCommand;
import com.syshco.ms.cqrs.event.MoneyWithdrawnEvent;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.spring.stereotype.Aggregate;

import java.math.BigDecimal;

import static org.axonframework.modelling.command.AggregateLifecycle.apply;

@Aggregate
public class AccountAggregate {

    @AggregateIdentifier
    private String accountId;
    private BigDecimal balance;

    public AccountAggregate() {}

    @CommandHandler
    public AccountAggregate(CreateAccountCommand command) {
        apply(new AccountCreatedEvent(command.accountId(), command.initialBalance()));
    }

    @EventSourcingHandler
    public void on(AccountCreatedEvent event) {
        this.accountId = event.accountId();
        this.balance = event.balance();
    }

    @CommandHandler
    public void handle(DepositMoneyCommand command) {
        apply(new MoneyDepositedEvent(command.accountId(), command.amount()));
    }

    @EventSourcingHandler
    public void on(MoneyDepositedEvent event) {
        this.balance = this.balance.add(event.amount());
    }


    @CommandHandler
    public void handle(WithdrawMoneyCommand command) {
        if (balance.compareTo(command.amount()) < 0) {
            throw new IllegalArgumentException("Insufficient funds");
        }
        apply(new MoneyWithdrawnEvent(command.accountId(), command.amount()));
    }

    @EventSourcingHandler
    public void on(MoneyWithdrawnEvent event) {
        this.balance = this.balance.subtract(event.amount());
    }

}