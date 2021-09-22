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
import java.util.*;

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
        Boolean found = false;
        for(CustomerBankAccount account:customer.getAccounts()){
            found = (account.getAccountNo()==accountNo)||found;
        }
        if(found)
            return accountTransactionsRepository.fetchMiniTransactions(accountNo);
        return new ArrayList<AccountTransactions>();
    }

    public List<AccountTransactions> transactionsInRange(int accountNo, Integer customerId,Date ll,Date ul) {
        Customer customer = customerDetails(customerId);
        Boolean found = false;
        for(CustomerBankAccount account:customer.getAccounts()){
            found = (account.getAccountNo()==accountNo)||found;
        }
        if(found)
            return accountTransactionsRepository.fetchMiniTransactionsBetweenDates(accountNo,ll,ul);
        return new ArrayList<>();
    }

    public ApiResponse cashWithDrawl(Integer accountNo, AccountTransactions accountTransactions)
    {
        double tId=Math.random();
        tId=Math.floor(tId*1000);
        int transactionReferenceNo=(int)tId*100;

        accountTransactions.setTransactionReferenceNo(transactionReferenceNo);
        accountTransactions.setDate(new Date());

        if(!customerBankAccountRepository.existsById(accountNo))
            return new ApiResponse(false,"Account not found");
        CustomerBankAccount account = customerBankAccountRepository.getById(accountNo);
        if(account.getBalanceAmount()<accountTransactions.getAmount())
            return new ApiResponse(false,"Balance too low");
        if(accountTransactions.getAmount()<1)
            return new ApiResponse(false,"Withdrawl amount must be greater than 0");
        if(accountTransactionsRepository.fetchDailyDebitTransactionsSum(accountNo)+accountTransactions.getAmount()>10000)
            return new ApiResponse(false,"Daily Withdrawl Limit Reached");

        double balanceAmount=account.getBalanceAmount()-accountTransactions.getAmount();
        account.setBalanceAmount(balanceAmount);
        accountTransactions.setType("Debit");
        List<AccountTransactions> transactions = account.getAccountTransactions();
        transactions.add(accountTransactionsRepository.save(accountTransactions));
        account.setAccountTransactions(transactions);
        customerBankAccountRepository.save(account);
        return new ApiResponse(true,"Withdrawl Succeeded");
    }

    public ApiResponse cashDeposit(Integer accountNo,AccountTransactions accountTransactions)
    {
        double tId=Math.random();
        tId=Math.floor(tId*1000);
        int transactionReferenceNo=(int)tId*100;
        accountTransactions.setTransactionReferenceNo(transactionReferenceNo);

        accountTransactions.setDate(new Date());

        if(!customerBankAccountRepository.existsById(accountNo))
            return new ApiResponse(false,"Account not found");
        if(accountTransactions.getAmount()<1)
            return new ApiResponse(false,"Deposit amount must be greater than 0");

        CustomerBankAccount account = customerBankAccountRepository.getById(accountNo);
        double balanceAmount=account.getBalanceAmount()+accountTransactions.getAmount();
        account.setBalanceAmount(balanceAmount);

        accountTransactions.setType("Credit");
        List<AccountTransactions> transactions = account.getAccountTransactions();
        transactions.add(accountTransactionsRepository.save(accountTransactions));
        account.setAccountTransactions(transactions);
        customerBankAccountRepository.save(account);
        return new ApiResponse(true,"Amount Deposited");
    }

    public ApiResponse transferAmount(Integer accountNo,AccountTransactions accountTransactions){
        double tId1=Math.random();
        tId1=Math.floor(tId1*1000);
        int transactionReferenceNo=(int)tId1*100;
        accountTransactions.setTransactionReferenceNo(transactionReferenceNo);

        if(accountTransactions.getAmount()<1)
            return new ApiResponse(false,"Transfer amount must be greater than 0");
        if(!customerBankAccountRepository.existsById(accountNo))
            return new ApiResponse(false,"Sender Account not found");
        if(!customerBankAccountRepository.existsById(accountTransactions.getToAccount()))
            return new ApiResponse(false,"Benificiary Account not found");
        if(accountTransactionsRepository.fetchDailyDebitTransactionsSum(accountNo)+accountTransactions.getAmount()>10000)
            return new ApiResponse(false,"Daily Withdrawl Limit Reached");

        AccountTransactions accountTransactions1 = new AccountTransactions();
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

        if(Objects.equals(type, "Credit"))
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
        return new ApiResponse(true,"Amount Transfer Success");
    }


}
