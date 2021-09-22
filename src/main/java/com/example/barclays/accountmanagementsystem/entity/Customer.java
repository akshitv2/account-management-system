package com.example.barclays.accountmanagementsystem.entity;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "customer")
@NoArgsConstructor
@AllArgsConstructor
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int customerId;
    private String name;
    private Date dob;
    private String email;
    private String address;
    @Column(name = "pan_card_no")
    private String panCardNo;
    @Column(name = "aadhar_no")
    private int aadharNo;


    @OneToMany
    @JoinColumn(name="customerId",referencedColumnName = "customerId")
    private List<CustomerBankAccount> accounts;

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPanCardNo() {
        return panCardNo;
    }

    public void setPanCardNo(String panCardNo) {
        this.panCardNo = panCardNo;
    }

    public int getAadharNo() {
        return aadharNo;
    }

    public void setAadharNo(int aadharNo) {
        this.aadharNo = aadharNo;
    }

    public List<CustomerBankAccount> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<CustomerBankAccount> accounts) {
        this.accounts = accounts;
    }

    public void addAccount(CustomerBankAccount customerBankAccount){
        accounts.add(customerBankAccount);
    }
}
