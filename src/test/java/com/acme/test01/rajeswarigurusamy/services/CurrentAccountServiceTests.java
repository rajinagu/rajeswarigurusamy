package com.acme.test01.rajeswarigurusamy.services;

import com.acme.test01.rajeswarigurusamy.exceptions.AccountNotFoundException;
import com.acme.test01.rajeswarigurusamy.exceptions.WithdrawalAmountTooLargeException;
import com.acme.test01.rajeswarigurusamy.repository.SystemDB;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class CurrentAccountServiceTests {
    CurrentAccountService currentAccountService;

    @BeforeEach
    public void setup() {
        currentAccountService = new CurrentAccountService();
        //Initialize the DB
        SystemDB.getInstance();
    }
    @Test
    public void testWithdrawOnInValidAccount() {
        assertThrows(AccountNotFoundException.class, () -> {
            currentAccountService.withdraw(10L, 1000);
        });
    }

    @Test
    public void testWithdrawOnValidAccountWithEnoughBalance() throws WithdrawalAmountTooLargeException {
        currentAccountService.withdraw(4L, 100);
    }

    @Test
    public void testWithdrawOnValidAccountWithNotEnoughBalance() {
        assertThrows(WithdrawalAmountTooLargeException.class, () -> {
            currentAccountService.withdraw(4L, 30000);
        });
    }

    @Test
    public void testOpenAccount() {
        currentAccountService.openCurrentAccount("5");
    }

    @Test
    public void testDepositOnInvalidAccount() {
        assertThrows(AccountNotFoundException.class, () -> {
            currentAccountService.deposit(30L, 1000);
        });
    }

    @Test
    public void testDepositOnValidAccount() {
        currentAccountService.deposit(3L, 3000);
    }
}
