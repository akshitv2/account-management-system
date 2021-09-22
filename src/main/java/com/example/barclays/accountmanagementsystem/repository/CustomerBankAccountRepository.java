package com.example.barclays.accountmanagementsystem.repository;

import com.example.barclays.accountmanagementsystem.entity.Customer;
import com.example.barclays.accountmanagementsystem.entity.CustomerBankAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerBankAccountRepository extends JpaRepository<CustomerBankAccount,Integer> {
    CustomerBankAccount save(CustomerBankAccount customerBankAccount);
}
