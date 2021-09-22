package com.example.barclays.accountmanagementsystem.service;

import com.example.barclays.accountmanagementsystem.dto.ApiResponse;
import com.example.barclays.accountmanagementsystem.entity.Customer;
import com.example.barclays.accountmanagementsystem.entity.CustomerBankAccount;
import com.example.barclays.accountmanagementsystem.entity.Role;
import com.example.barclays.accountmanagementsystem.entity.User;
import com.example.barclays.accountmanagementsystem.repository.CustomerBankAccountRepository;
import com.example.barclays.accountmanagementsystem.repository.CustomerRepository;
import com.example.barclays.accountmanagementsystem.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BankManagerService {

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    private CustomerBankAccountRepository customerBankAccountRepository;

    @Autowired
    private UserRepository userRepository;

    public ApiResponse findByPanCardNo(String pancardno) {
        Optional<Customer> tempCust = customerRepository.findByPanCardNo(pancardno);
        if (tempCust.isEmpty())
            return new ApiResponse(false, "Account not found");
        return new ApiResponse(true, "Account Found");
    }

    public ApiResponse save(Customer customer) {
        if (customer.getAadharNo() == 0 || customer.getPanCardNo().length() == 0 || customer.getName().length() == 0 ||
                customer.getEmail().length() == 0 || customer.getAddress().length() == 0)
            return new ApiResponse(false, "All fields were not filled");
        Optional<Customer> optTempCust = customerRepository.findByPanCardNo(customer.getPanCardNo());
        if (optTempCust.isEmpty()) {
            customer = customerRepository.save(customer);
            CustomerBankAccount customerBankAccount = new CustomerBankAccount();
            customerBankAccount.setBalanceAmount(0);

            List<CustomerBankAccount> accounts = new ArrayList<CustomerBankAccount>();
            accounts.add(customerBankAccount);
            customer.setAccounts(accounts);
            customerBankAccountRepository.save(customerBankAccount);

            User user = new User();
            user.setUserId(customerRepository.findByPanCardNo(customer.getPanCardNo()).get().getCustomerId());
            System.out.println(user.getUserId());
            user.setPassword("tempPassword");
            Role role = new Role();
            role.setRoleId(2);
            user.setRole(role);
            userRepository.save(user);

            return new ApiResponse(true, Integer.toString(customer.getCustomerId()));
        } else {
            saveAccount(optTempCust.get());
            return new ApiResponse(true, Integer.toString(optTempCust.get().getCustomerId()));
        }
    }

    public ApiResponse saveAccount(Customer customer) {
        try {
            Customer curCustomer = customerRepository.findById(customer.getCustomerId()).get();
            CustomerBankAccount customerBankAccount = customerBankAccountRepository.save(new CustomerBankAccount());
            curCustomer.addAccount(customerBankAccount);
            customerRepository.save(curCustomer);

        } catch (Exception e) {
            return new ApiResponse(false, "Customer Id does not exist in db");
        }
        return new ApiResponse(true, "Account Created");
    }
}
