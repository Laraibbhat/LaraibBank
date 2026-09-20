package org.example.laraib.accounts.service;

import org.example.laraib.accounts.dto.CustomerDto;
import org.example.laraib.accounts.entity.Accounts;
import org.example.laraib.accounts.entity.Customer;

public interface IAccountsService {

    /**
     * Creates a new account for the given customer.
     *
     * @param customerDto the customer data transfer object containing customer details
     */
    void createAccount (CustomerDto customerDto);

    CustomerDto fetchAccountDetails(String mobileNumber);

    /**
     * Updates the account details for the given customer.
     *
     * @param customerDto the customer data transfer object containing updated customer details
     */
    boolean updateAccount(CustomerDto customerDto);

    boolean deleteAccount(String mobileNumber);
}
