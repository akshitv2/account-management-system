package com.example.barclays.accountmanagementsystem.service;

import com.example.barclays.accountmanagementsystem.dto.ApiResponse;
import com.example.barclays.accountmanagementsystem.entity.*;
import com.example.barclays.accountmanagementsystem.repository.AccountTransactionsRepository;
import com.example.barclays.accountmanagementsystem.repository.CustomerBankAccountRepository;
import com.example.barclays.accountmanagementsystem.repository.CustomerRepository;
import com.example.barclays.accountmanagementsystem.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Transient;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.transaction.Transaction;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private CustomerBankAccountRepository customerBankAccountRepository;

    @Autowired
    private AccountTransactionsRepository accountTransactionsRepository;

    public Customer customerDetails(int customerId) {
        return customerRepository.findById(customerId).orElse(null);
    }

    //    public List<AccountTransactions> transactions(int accountNo){Optional<CustomerBankAccount> tempAccount = customerBankAccountRepository.findById(accountNo);if(tempAccount.isEmpty())return Collections.emptyList();return tempAccount.get().getAccountTransactions();}

    public List<AccountTransactions> transactions(int accountNo, Integer customerId) {
        Customer customer = customerDetails(customerId);
        List<CustomerBankAccount> cref = customer.getAccounts();
//          for(CustomerBankAccount element:cref){System.out.println("Details= "+element.getBalanceAmount());}
        for (int i = 0; i < cref.size(); i++) {
            if (cref.get(i).getAccountNo() == accountNo) {
                System.out.println("Account Found");
                List<AccountTransactions> transactionDetails = cref.get(i).getAccountTransactions();
                for (AccountTransactions element : transactionDetails) {
                    System.out.println("Type= " + element.getType());
                }
                return transactionDetails;
            }
        }
        return null;
    }

    public boolean cashWithDrawl(Integer accountNo, AccountTransactions accountTransactions)
    {
        double tId=Math.random();
        tId=Math.floor(tId*1000);
        int transactionReferenceNo=(int)tId*100;
        accountTransactions.setTransactionReferenceNo(transactionReferenceNo);
        CustomerBankAccount account = customerBankAccountRepository.getById(accountNo);
        double balanceAmount=account.getBalanceAmount()-accountTransactions.getAmount();
        account.setBalanceAmount(balanceAmount);
        List<AccountTransactions> transactions = account.getAccountTransactions();
        transactions.add(accountTransactionsRepository.save(accountTransactions));
        account.setAccountTransactions(transactions);
        customerBankAccountRepository.save(account);
        return true;
    }

    public boolean cashDeposit(Integer accountNo,AccountTransactions accountTransactions)
    {
        double tId=Math.random();
        tId=Math.floor(tId*1000);
        int transactionReferenceNo=(int)tId*100;
        accountTransactions.setTransactionReferenceNo(transactionReferenceNo);
        CustomerBankAccount account = customerBankAccountRepository.getById(accountNo);
        double balanceAmount=account.getBalanceAmount()+accountTransactions.getAmount();
        account.setBalanceAmount(balanceAmount);
        List<AccountTransactions> transactions = account.getAccountTransactions();
        transactions.add(accountTransactionsRepository.save(accountTransactions));
        account.setAccountTransactions(transactions);
        customerBankAccountRepository.save(account);
        return true;
    }

    public boolean transferAmount(Integer accountNo,AccountTransactions accountTransactions){
        double tId1=Math.random();
        tId1=Math.floor(tId1*1000);
        int transactionReferenceNo=(int)tId1*100;
        accountTransactions.setTransactionReferenceNo(transactionReferenceNo);

        AccountTransactions accountTransactions1=new AccountTransactions();
        double tId2=Math.random();
        tId2=Math.floor(tId2*1000);
        int toAccountNumber=accountTransactions.getToAccount();
        int transactionReferenceNo2=(int)tId2*100;
        accountTransactions1.setTransactionReferenceNo(transactionReferenceNo2);
        accountTransactions1.setSubType(accountTransactions.getType());
        accountTransactions1.setDate(accountTransactions.getDate());
        accountTransactions1.setToAccount(accountNo);
        accountTransactions1.setAmount(accountTransactions.getAmount());
        String type=accountTransactions.getType();
        if(type=="Credit")
        {
            accountTransactions.setType("Debit");
            accountTransactions1.setType("Credit");
        }
        else {
            accountTransactions.setType("Credit");
            accountTransactions1.setType("Debit");
        }

        CustomerBankAccount fromAccount=customerBankAccountRepository.getById(accountNo);
        List<AccountTransactions> transactionsRef=fromAccount.getAccountTransactions();
        transactionsRef.add(accountTransactionsRepository.save(accountTransactions));
        fromAccount.setAccountTransactions(transactionsRef);
        fromAccount.setBalanceAmount(fromAccount.getBalanceAmount()-accountTransactions.getAmount());
        customerBankAccountRepository.save(fromAccount);

        CustomerBankAccount toAccount=customerBankAccountRepository.getById(toAccountNumber);
        List<AccountTransactions> transactionsRef1=toAccount.getAccountTransactions();
        transactionsRef1.add(accountTransactionsRepository.save(accountTransactions1));
        toAccount.setAccountTransactions(transactionsRef1);
        toAccount.setBalanceAmount(toAccount.getBalanceAmount()+accountTransactions.getAmount());
        customerBankAccountRepository.save(toAccount);
        return true;
    }


}
