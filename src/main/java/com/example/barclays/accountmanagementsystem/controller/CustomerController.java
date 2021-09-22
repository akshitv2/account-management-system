package com.example.barclays.accountmanagementsystem.controller;


import com.example.barclays.accountmanagementsystem.entity.AccountTransactions;
import com.example.barclays.accountmanagementsystem.entity.Customer;
import com.example.barclays.accountmanagementsystem.entity.CustomerBankAccount;
import com.example.barclays.accountmanagementsystem.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @GetMapping("/accounts/{customerId}")
    public Customer customerDetails(@PathVariable int customerId)
    {
        return customerService.customerDetails(customerId);
    }

    @GetMapping("/mini-statements/{accountNo}/{customerId}")
    public List<AccountTransactions> accountTransactions(@PathVariable int accountNo, @PathVariable int customerId)
    {
        return customerService.transactions(accountNo,customerId);
    }

    @PostMapping("/cash-withdrawl/{accountNo}")
    public boolean withdrawl(@RequestBody AccountTransactions accountTransactions, @PathVariable Integer accountNo)
    {
        customerService.cashWithDrawl(accountNo, accountTransactions);
        return true;
    }

    @PostMapping("/cash-deposit/{accountNo}")
    public boolean deposit(@RequestBody AccountTransactions accountTransactions,@PathVariable Integer accountNo)
    {
        customerService.cashDeposit(accountNo,accountTransactions);
        return true;
    }


    @PostMapping("/amount-transfer/{accountNo}")
    public boolean transferAmount(@RequestBody AccountTransactions accountTransactions,@PathVariable Integer accountNo)
    {
        customerService.transferAmount(accountNo,accountTransactions);
        return true;
    }
}
