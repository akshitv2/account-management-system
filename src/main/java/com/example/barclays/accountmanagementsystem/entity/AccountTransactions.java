package com.example.barclays.accountmanagementsystem.entity;


import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table
@NoArgsConstructor
@AllArgsConstructor
public class AccountTransactions {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int transactionId;
    private int transactionReferenceNo;
    private Date date;
    private String type;
    private String subType;
    private int toAccount;
    private double amount;

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public int getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(int transactionId) {
        this.transactionId = transactionId;
    }

    public int getTransactionReferenceNo() {
        return transactionReferenceNo;
    }

    public void setTransactionReferenceNo(int transactionReferenceNo) {
        this.transactionReferenceNo = transactionReferenceNo;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSubType() {
        return subType;
    }

    public void setSubType(String subType) {
        this.subType = subType;
    }

    public int getToAccount() {
        return toAccount;
    }

    public void setToAccount(int toAccount) {
        this.toAccount = toAccount;
    }
}
