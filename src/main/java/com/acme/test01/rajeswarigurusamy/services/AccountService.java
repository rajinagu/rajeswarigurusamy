package com.acme.test01.rajeswarigurusamy.services;

import com.acme.test01.rajeswarigurusamy.exceptions.AccountNotFoundException;
import com.acme.test01.rajeswarigurusamy.exceptions.WithdrawalAmountTooLargeException;
import com.acme.test01.rajeswarigurusamy.model.Account;
import com.acme.test01.rajeswarigurusamy.repository.SystemDB;

import java.util.List;
import java.util.Optional;

public interface AccountService {
    //Note: Looks like there is some typo here, Account opening methods should take customerId as input (not accountId)
    //So making some adjustments accordingly
    //Moving the account opening methods to corresponding classes as it makes more sense to have it in Savings and Current account services
    /*void openSavingsAccount(String customerId, int amountToDeposit);
    void openCurrentAccount(String customerId); */

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

