package com.acme.test01.rajeswarigurusamy.model;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicLong;

public class Account {
    Long id;
    Long accountId;
    String customerNumber;
    AccountType accountType;
    int balance;
    int overdraft;

    public Account(Long id, Long accountId, String customerNumber, AccountType accountType, int balance,  int overdraft) {
        this.id = id;
        this.accountId = accountId;
        this.customerNumber = customerNumber;
        this.accountType = accountType;
        this.balance = balance;
        this.overdraft = overdraft;
    }

    public Long getAccountId() {
        return accountId;
    }

    public AccountType getAccountType() {
        return accountType;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public Long getId() {
        return id;
    }

    public String getCustomerNumber() {
        return customerNumber;
    }

    public int getOverdraft() {
        return overdraft;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return Objects.equals(id, account.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
