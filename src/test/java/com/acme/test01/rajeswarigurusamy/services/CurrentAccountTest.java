package com.acme.test01.rajeswarigurusamy.services;

import com.acme.test01.rajeswarigurusamy.exceptions.AccountNotFoundException;
import com.acme.test01.rajeswarigurusamy.exceptions.WithdrawalAmountTooLargeException;
import com.acme.test01.rajeswarigurusamy.repository.SystemDB;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class CurrentAccountTest {
    CurrentAccount currentAccount;

    @BeforeEach
    public void setup() {
        //Initialize the DB
        SystemDB.getInstance();
        currentAccount = new CurrentAccount();
    }
    @Test
    public void testWithdrawOnInValidAccount() {
        assertThrows(AccountNotFoundException.class, () -> {
            currentAccount.withdraw(10L, 1000);
        });
    }

    @Test
    public void testWithdrawOnValidAccountWithEnoughBalance() throws WithdrawalAmountTooLargeException {
        currentAccount.withdraw(4L, 100);
    }

    @Test
    public void testWithdrawOnValidAccountWithNotEnoughBalance() {
        assertThrows(WithdrawalAmountTooLargeException.class, () -> {
            currentAccount.withdraw(4L, 30000);
        });
    }

    @Test
    public void testOpenAccount() {
        currentAccount.openCurrentAccount(5L);
    }

    @Test
    public void testDepositOnInvalidAccount() {
        assertThrows(AccountNotFoundException.class, () -> {
            currentAccount.deposit(30L, 1000);
        });
    }

    @Test
    public void testDepositOnValidAccount() {
        currentAccount.deposit(3L, 3000);
    }
}
