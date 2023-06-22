package com.acme.test01.rajeswarigurusamy.services;

import com.acme.test01.rajeswarigurusamy.exceptions.AccountNotFoundException;
import com.acme.test01.rajeswarigurusamy.exceptions.WithdrawalAmountTooLargeException;
import com.acme.test01.rajeswarigurusamy.model.Account;
import com.acme.test01.rajeswarigurusamy.repository.SystemDB;

import java.util.List;
import java.util.Optional;

public interface AccountService {
    void openSavingsAccount(Long accountId, int amountToDeposit);
    void openCurrentAccount(Long accountId);

    void withdraw(Long accountId, int amountToWithdraw)
            throws AccountNotFoundException, WithdrawalAmountTooLargeException;
    void deposit(Long accountId, int amountToDeposit)throws
            AccountNotFoundException;

    static  Account validateAccount(Long accountId) throws AccountNotFoundException {
        //Get the list of accounts
        SystemDB systemDB = SystemDB.getInstance();
        List<Account> accountList = systemDB.accountList;
        // Verify the account exists
        Optional<Account> accountOptional = accountList.stream().filter(account -> account.getAccountId().equals(accountId)).findFirst();
        if(accountOptional.isPresent()) {
            return accountOptional.get();
        } else {
            throw new AccountNotFoundException(String.format("Given Account Number: [%d] is not Valid", accountId));
        }
    }

}

