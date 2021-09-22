package com.example.barclays.accountmanagementsystem.controller;
import com.example.barclays.accountmanagementsystem.dto.ApiResponse;
import com.example.barclays.accountmanagementsystem.entity.*;
import com.example.barclays.accountmanagementsystem.service.BankManagerService;
import com.example.barclays.accountmanagementsystem.service.CustomerService;
import com.example.barclays.accountmanagementsystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class BankManagerController {

    @Autowired
    CustomerService customerService;

    @Autowired
    BankManagerService bankManagerService;

    @Autowired
    UserService userService;

    @GetMapping("/find-account/{pancardid}")
    public ApiResponse customerDetails(@PathVariable String pancardid)
    {
        return bankManagerService.findByPanCardNo(pancardid);
    }

    @PostMapping("/create-new-account")
    public ApiResponse saveCustomer(@RequestBody Customer customer)
    {
        ApiResponse resp = bankManagerService.save(customer);
        return resp;
    }

    @PostMapping("/create-new-bank-account")
    public ApiResponse saveAccount(@RequestBody Customer customer)
    {
        return bankManagerService.saveAccount(customer);
    }


}
