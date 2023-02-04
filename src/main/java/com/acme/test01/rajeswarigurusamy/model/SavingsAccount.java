package com.acme.test01.rajeswarigurusamy.model;

//Creating SavingsAccount as a separate class to accommodate additional fields in the future/live env requirements
// even though it doesnt have any additional properties now
public class SavingsAccount extends Account{
    public SavingsAccount(String customerNum, int balance) {
        super(customerNum,AccountType.SAVINGS,balance);
    }
}
