package com.acme.test01.rajeswarigurusamy.services;

import com.acme.test01.rajeswarigurusamy.exceptions.AccountNotFoundException;
import com.acme.test01.rajeswarigurusamy.exceptions.WithdrawalAmountTooLargeException;
import com.acme.test01.rajeswarigurusamy.repository.SystemDB;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class SavingAccountServicesTest {


    SavingsAccountService savingsAccountService;

    @BeforeEach
    public void setup() {
        savingsAccountService = new SavingsAccountService();
        //Initialize the DB
        SystemDB.getInstance();
    }
    @Test
    public void testWithdrawOnInValidAccount() {
        assertThrows(AccountNotFoundException.class, () -> {
            savingsAccountService.withdraw(10L, 1000);
        });
    }

    @Test
    public void testWithdrawOnValidAccountWithEnoughBalance() throws WithdrawalAmountTooLargeException{
        savingsAccountService.withdraw(2L, 100);
    }

    @Test
    public void testWithdrawOnValidAccountWithNotEnoughBalance() {
        assertThrows(WithdrawalAmountTooLargeException.class, () -> {
            savingsAccountService.withdraw(2L, 7000);
        });
    }

    @Test
    public void testOpenAccountWithEnoughDeposit() {
        savingsAccountService.openSavingsAccount("5",3000);
    }

    @Test
    public void testOpenAccountWithNotEnoughDeposit() {
        savingsAccountService.openSavingsAccount("6",300);
    }

    @Test
    public void testDepositOnInvalidAccount() {
        assertThrows(AccountNotFoundException.class, () -> {
            savingsAccountService.deposit(200L,3000);
        });
    }

    @Test
    public void testDepositOnValidAccount() {
        savingsAccountService.deposit(2L,3000);
    }

    @Test
    public void testMultipleOperations() throws WithdrawalAmountTooLargeException {
        //Deposit 3000
        savingsAccountService.deposit(1L,3000);
        //Withdraw 5000
        savingsAccountService.withdraw(1L, 2000);
        //Deposit 300
        savingsAccountService.deposit(1L,300);
        //Withdraw 500
        assertThrows(WithdrawalAmountTooLargeException.class, () -> {
            savingsAccountService.withdraw(1L, 3500);
        });
    }

}
