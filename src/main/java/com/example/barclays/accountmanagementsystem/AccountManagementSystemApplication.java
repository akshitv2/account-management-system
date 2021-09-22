package com.example.barclays.accountmanagementsystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
public class AccountManagementSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(AccountManagementSystemApplication.class, args);
    }

}
