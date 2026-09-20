package org.example.laraib.accounts.service.impl;

import lombok.AllArgsConstructor;
import org.example.laraib.accounts.constants.AccountsConstants;
import org.example.laraib.accounts.dto.AccountsDto;
import org.example.laraib.accounts.dto.CustomerDto;
import org.example.laraib.accounts.entity.Accounts;
import org.example.laraib.accounts.entity.Customer;
import org.example.laraib.accounts.exception.CustomerAlreadyExistsException;
import org.example.laraib.accounts.exception.EntityNotFoundException;
import org.example.laraib.accounts.mapper.AccountsMapper;
import org.example.laraib.accounts.mapper.CustomerMapper;
import org.example.laraib.accounts.repository.AccountsRepository;
import org.example.laraib.accounts.repository.CustomerRepository;
import org.example.laraib.accounts.service.IAccountsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Optional;
import java.util.Random;

import static org.example.laraib.accounts.mapper.AccountsMapper.mapToAccountsDto;
import static org.example.laraib.accounts.mapper.CustomerMapper.mapToCustomer;
import static org.example.laraib.accounts.mapper.CustomerMapper.mapToCustomerDto;

@Service
@AllArgsConstructor
public class AccountsServiceImpl implements IAccountsService {

    private AccountsRepository accountsRepository;
    private CustomerRepository customerRepository;


    @Override
    @Transactional
    public void createAccount(CustomerDto customerDto) {

        Customer customer = new Customer();

        mapToCustomer(customerDto, customer);
//        customer.setCreatedAt(LocalDateTime.now());
//        customer.setCreatedBy("Laraib");

        Optional<Customer> optionalCustomer = customerRepository.findByMobileNumber(customer.getMobileNumber());
        if (optionalCustomer.isPresent()) {
            throw new CustomerAlreadyExistsException("Customer with mobile number " + customer.getMobileNumber() + " already exists.");
        }
        ;
        Customer savedCustomer = customerRepository.save(customer);
        Accounts newAccount = createNewAccount(savedCustomer);
        accountsRepository.save(newAccount);
    }

    private Accounts createNewAccount(Customer customer) {
        Accounts newAccount = new Accounts();
        newAccount.setCustomerId(customer);
        long randomAccNumber = 10000000L + new Random().nextInt(999999999);
        newAccount.setAccountNumber(randomAccNumber);
        newAccount.setAccountType(AccountsConstants.SAVINGS);
        newAccount.setBranchAddress(AccountsConstants.ADDRESS);
//        newAccount.setCreatedAt(LocalDateTime.now());
//        newAccount.setCreatedBy("Laraib");
        return newAccount;
    }

    @Override
    public CustomerDto fetchAccountDetails(String mobileNumber) {
//        Optional <Customer> optionalCustomer = customerRepository.findByMobileNumber(mobileNumber);
//        if (optionalCustomer.isEmpty()) {
//            throw new EntityNotFoundException("Customer", "mobileNumber", mobileNumber);
//        } else {
//            CustomerDto customerDto = new CustomerDto();
//            mapToCustomerDto(optionalCustomer.get(), customerDto);
//            return customerDto;
//        }

        Customer customer = customerRepository.findByMobileNumber(mobileNumber)
                .orElseThrow(() -> new EntityNotFoundException("Customer", "mobileNumber", mobileNumber));
        System.out.println("The Customer id is: " + customer.getCustomerId());
        Accounts accounts = accountsRepository.findByCustomerId(customer)
                .orElseThrow(() -> new EntityNotFoundException("Accounts", "customerId", String.valueOf(customer.getCustomerId())));
        CustomerDto customerDto = mapToCustomerDto(customer, new CustomerDto());

        customerDto.setAccountsDto(mapToAccountsDto(accounts, new AccountsDto()));

        return customerDto;
    }

    @Override
    public boolean updateAccount(CustomerDto customerDto) {
        boolean isUpdated = false;

        AccountsDto accountsDto = customerDto.getAccountsDto();

        if (accountsDto != null) {
            Accounts accounts = accountsRepository.findById(accountsDto.getAccountNumber())
                    .orElseThrow(() -> new EntityNotFoundException("Accounts", "accountNumber", String.valueOf(accountsDto.getAccountNumber())));

            AccountsMapper.mapToAccounts(accountsDto, accounts);
//            accounts.setUpdatedAt(LocalDateTime.now());
//            accounts.setUpdatedBy("Laraib");
            accounts = accountsRepository.save(accounts);

            Long customerId = accounts.getCustomerId().getCustomerId();

            Customer customer = customerRepository.findById(customerId)
                    .orElseThrow(() -> new EntityNotFoundException("Customer", "mobileNumber", customerDto.getMobileNumber()));

            mapToCustomer(customerDto, customer);
//            customer.setUpdatedAt(LocalDateTime.now());
//            customer.setUpdatedBy("Laraib");
            customerRepository.save(customer);
            isUpdated = true;
        }
        return isUpdated;
    }


    @Override
    public boolean deleteAccount(String mobileNumber) {
        boolean isDeleted = false;

        Customer customer = customerRepository.findByMobileNumber(mobileNumber)
                .orElseThrow(() -> new EntityNotFoundException("Customer", "mobileNumber", mobileNumber));

        accountsRepository.deleteByCustomerId(customer);
        customerRepository.deleteById(customer.getCustomerId());
        isDeleted = true;

        return isDeleted;
    }

}
