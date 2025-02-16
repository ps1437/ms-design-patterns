package com.syscho.ms.customer.services;

import com.syscho.ms.commons.model.AccountCreateRequest;
import com.syscho.ms.commons.model.AccountResponse;
import com.syscho.ms.customer.domain.Customer;
import com.syscho.ms.customer.domain.CustomerRepository;
import com.syscho.ms.customer.web.AccountClient;
import com.syscho.ms.customer.web.CustomerDetailsResponse;
import com.syscho.ms.customer.web.NotificationClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private NotificationClient notificationClient;

    @Autowired
    private AccountClient accountClient;


    @Transactional
    public Customer registerCustomer(Customer customer) {
        // Step 1: Save the customer (without accountId)
        Customer savedCustomer = customerRepository.save(customer);

        // Step 2: Call Account Service to create an account
        AccountCreateRequest accountRequest = new AccountCreateRequest(savedCustomer.getName());
        AccountResponse accountResponse = accountClient.createAccount(accountRequest);

        // Step 3: Update customer with the generated accountId
        savedCustomer.setAccountId(accountResponse.accountNumber());
        return customerRepository.save(savedCustomer);
    }


    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    public CustomerDetailsResponse getCustomerWithAccount(Long customerId) {
        Optional<Customer> customerOpt = customerRepository.findById(customerId);

        if (customerOpt.isEmpty()) {
            throw new RuntimeException("Customer not found");
        }

        Customer customer = customerOpt.get();
        AccountResponse account = accountClient.getAccountById(customer.getAccountId());

        return new CustomerDetailsResponse(customer, account);
    }
}


