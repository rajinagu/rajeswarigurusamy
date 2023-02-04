package com.acme.test01.rajeswarigurusamy.model;

import java.util.concurrent.atomic.AtomicLong;

public class Account {
    Long accountId;
    String customerNumber;
    AccountType accountType;
    int balance;

    final static AtomicLong id = new AtomicLong(1);

    public Account(String customerNumber, AccountType accountType, int balance) {
        this.accountId = id.getAndIncrement();
        this.customerNumber = customerNumber;
        this.accountType = accountType;
        this.balance = balance;
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
}
