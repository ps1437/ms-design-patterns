package com.syscho.ms.customer.web;

import com.syscho.ms.commons.model.AccountResponse;
import com.syscho.ms.customer.domain.Customer;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomerDetailsResponse {
    private Long customerId;
    private String name;
    private String email;
    private String phone;
    private AccountResponse account;

    public CustomerDetailsResponse(Customer customer, AccountResponse account) {
        this.customerId = customer.getId();
        this.name = customer.getName();
        this.email = customer.getEmail();
        this.phone = customer.getPhone();
        this.account = account;
    }
}
