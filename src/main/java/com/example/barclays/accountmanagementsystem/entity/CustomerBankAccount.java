package com.example.barclays.accountmanagementsystem.entity;


import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Table
@NoArgsConstructor
@AllArgsConstructor
public class CustomerBankAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int accountNo;
    private double balanceAmount;

    @OneToMany
    @JoinColumn(name = "accountNo",referencedColumnName = "accountNo")
    private List<AccountTransactions> accountTransactions;

    public int getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(int accountNo) {
        this.accountNo = accountNo;
    }

    public double getBalanceAmount() {
        return balanceAmount;
    }

    public List<AccountTransactions> getAccountTransactions() {
        return accountTransactions;
    }

    public void setAccountTransactions(List<AccountTransactions> accountTransactions) {
        this.accountTransactions = accountTransactions;
    }

    public void setBalanceAmount(double balanceAmount) {
        this.balanceAmount = balanceAmount;
    }
}
