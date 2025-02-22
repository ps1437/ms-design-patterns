package com.syshco.ms.cqrs.command.controller;

import com.syshco.ms.cqrs.command.model.CreateAccountCommand;
import com.syshco.ms.cqrs.command.model.DepositMoneyCommand;
import com.syshco.ms.cqrs.command.model.WithdrawMoneyCommand;
import com.syshco.ms.cqrs.event.MoneyWithdrawnEvent;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/accounts")
@Tag(name = "Commands API", description = "Handles command operations")
public class AccountCommandController {

    private final CommandGateway commandGateway;

    public AccountCommandController(CommandGateway commandGateway) {
        this.commandGateway = commandGateway;
    }

    @PostMapping("/create")
    public CompletableFuture<String> createAccount(@RequestParam BigDecimal initialBalance) {
        String accountId = UUID.randomUUID().toString();
        return commandGateway.send(new CreateAccountCommand(accountId, initialBalance));
    }

    @PostMapping("/deposit")
    public CompletableFuture<String> deposit(@RequestParam String accountId, @RequestParam BigDecimal amount) {
        return commandGateway.send(new DepositMoneyCommand(accountId, amount));
    }

    @PostMapping("/withdrawn")
    public CompletableFuture<String> withdrawn(@RequestParam String accountId, @RequestParam BigDecimal amount) {
        return commandGateway.send(new WithdrawMoneyCommand(accountId, amount));
    }
}