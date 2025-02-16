package com.syscho.ms.customer.web;

import com.syscho.ms.commons.model.AccountCreateRequest;
import com.syscho.ms.commons.model.AccountResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "account-service", url = "http://localhost:8080") // Account Service URL
public interface AccountClient {
    
    @GetMapping("/accounts/{id}")
    AccountResponse getAccountById(@PathVariable("id") String id);

    @PostMapping("/accounts/create")
    AccountResponse createAccount(@RequestBody AccountCreateRequest request);
}
