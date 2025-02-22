package com.syshco.ms.cqrs.query;

import com.syshco.ms.cqrs.query.model.AccountView;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/accounts")
@Tag(name = "Query API", description = "Handles query operations")
public class AccountQueryController {

    private final AccountViewRepository repository;

    public AccountQueryController(AccountViewRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<AccountView> getAllAccounts() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public AccountView getAccount(@PathVariable String id) {
        return repository.findById(id).orElseThrow();
    }
}
