package com.example.barclays.accountmanagementsystem.dto;

import java.util.Date;

public class TransactionDetails {

    public int transactionId;
    public String type;
    public String subType;
    public Date date;
    public double balanceAmount;

    public TransactionDetails()
    {

    }


    public TransactionDetails(int transactionId, String type, String subType, Date date, double balanceAmount) {
        this.transactionId = transactionId;
        this.type = type;
        this.subType = subType;
        this.date = date;
        this.balanceAmount = balanceAmount;
    }
}
