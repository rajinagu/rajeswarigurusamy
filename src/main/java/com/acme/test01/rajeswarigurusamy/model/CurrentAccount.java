package com.acme.test01.rajeswarigurusamy.model;

public class CurrentAccount extends Account{
    int overdraft;

    public CurrentAccount(String customerNumber, int balance, int overdraft) {
        super(customerNumber, AccountType.CURRENT, balance);
        this.overdraft = overdraft;
    }

    public int getOverdraft() {
        return overdraft;
    }
}
