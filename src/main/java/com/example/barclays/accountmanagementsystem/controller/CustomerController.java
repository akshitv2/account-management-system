package com.example.barclays.accountmanagementsystem.controller;


import com.example.barclays.accountmanagementsystem.dto.ApiResponse;
import com.example.barclays.accountmanagementsystem.dto.DateRangeDto;
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

    @PostMapping("/mini-statements/{accountNo}/{customerId}")
    public List<AccountTransactions> accountTransactionsInRange(@PathVariable int accountNo, @PathVariable int customerId, @RequestBody DateRangeDto dateRangeDto)
    {
        return customerService.transactionsInRange(accountNo,customerId,dateRangeDto.getLl(),dateRangeDto.getUl());
    }

    @PostMapping("/cash-withdrawl/{accountNo}")
    public ApiResponse withdrawl(@RequestBody AccountTransactions accountTransactions, @PathVariable Integer accountNo)
    {
        return customerService.cashWithDrawl(accountNo, accountTransactions);
    }

    @PostMapping("/cash-deposit/{accountNo}")
    public ApiResponse deposit(@RequestBody AccountTransactions accountTransactions,@PathVariable Integer accountNo)
    {
        return customerService.cashDeposit(accountNo,accountTransactions);
    }


    @PostMapping("/amount-transfer/{accountNo}")
    public boolean transferAmount(@RequestBody AccountTransactions accountTransactions,@PathVariable Integer accountNo)
    {
        customerService.transferAmount(accountNo,accountTransactions);
        return true;
    }
}
