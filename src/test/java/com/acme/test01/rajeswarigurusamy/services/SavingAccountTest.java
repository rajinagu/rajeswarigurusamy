package com.acme.test01.rajeswarigurusamy.services;

import com.acme.test01.rajeswarigurusamy.exceptions.AccountNotFoundException;
import com.acme.test01.rajeswarigurusamy.exceptions.WithdrawalAmountTooLargeException;
import com.acme.test01.rajeswarigurusamy.repository.SystemDB;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class SavingAccountTest {
    SavingsAccount savingsAccount;

    @BeforeEach
    public void setup() {
        //Initialize the DB
        SystemDB.getInstance();
        savingsAccount = new SavingsAccount();
    }
    @Test
    public void testWithdrawOnInValidAccount() {
        assertThrows(AccountNotFoundException.class, () -> {
            savingsAccount.withdraw(10L, 1000);
        });
    }

    @Test
    public void testWithdrawOnValidAccountWithEnoughBalance() throws WithdrawalAmountTooLargeException{
        savingsAccount.withdraw(2L, 100);
    }

    @Test
    public void testWithdrawOnValidAccountWithNotEnoughBalance() {
        assertThrows(WithdrawalAmountTooLargeException.class, () -> {
            savingsAccount.withdraw(2L, 7000);
        });
    }

    @Test
    public void testOpenAccountWithEnoughDeposit() {
        savingsAccount.openSavingsAccount(5L,3000);
    }

    @Test
    public void testOpenAccountWithNotEnoughDeposit() {
        savingsAccount.openSavingsAccount(6L,300);
    }

    @Test
    public void testDepositOnInvalidAccount() {
        assertThrows(AccountNotFoundException.class, () -> {
            savingsAccount.deposit(200L,3000);
        });
    }

    @Test
    public void testDepositOnValidAccount() {
        savingsAccount.deposit(2L,3000);
    }

    @Test
    public void testMultipleOperations() throws WithdrawalAmountTooLargeException {
        //Deposit 3000
        savingsAccount.deposit(1L,3000);
        //Withdraw 5000
        savingsAccount.withdraw(1L, 2000);
        //Deposit 300
        savingsAccount.deposit(1L,300);
        //Withdraw 500
        assertThrows(WithdrawalAmountTooLargeException.class, () -> {
            savingsAccount.withdraw(1L, 3500);
        });
    }

}
