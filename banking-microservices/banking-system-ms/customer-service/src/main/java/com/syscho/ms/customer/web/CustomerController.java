package com.syscho.ms.customer.web;

import com.syscho.ms.customer.services.CustomerService;
import com.syscho.ms.customer.domain.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @PostMapping("/register")
    public Customer registerCustomer(@RequestBody Customer customer) {
        return customerService.registerCustomer(customer);
    }

    @GetMapping("/{id}")
    public CustomerDetailsResponse getCustomerWithAccount(@PathVariable Long id) {
        return customerService.getCustomerWithAccount(id);
    }

    @GetMapping
    public List<Customer> getAllCustomers() {
        return customerService.getAllCustomers();
    }
}
