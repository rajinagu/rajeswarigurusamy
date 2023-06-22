package com.acme.test01.rajeswarigurusamy.services;

import com.acme.test01.rajeswarigurusamy.exceptions.AccountNotFoundException;
import com.acme.test01.rajeswarigurusamy.exceptions.WithdrawalAmountTooLargeException;
import com.acme.test01.rajeswarigurusamy.model.Account;
import com.acme.test01.rajeswarigurusamy.model.AccountType;
import org.springframework.stereotype.Service;

@Service
public class CurrentAccount implements AccountService {
    public static final int OVERDRAFT = 100000;

    public void openCurrentAccount(Long accountId){
        //Setting overdraft limit as Max Overdraft (provided by ACME bank) while opening accounts
        //Creating current account
        System.out.println("New Current Account has been created for AccountId : " + accountId);
    }

    public void openSavingsAccount(Long accountId, int amountToDeposit) {
        // Not implemeted here
    }
    //Adding synchronization for withdraw and deposit to work fine during multithreading
    public synchronized void withdraw(Long accountId, int withdrawAmount) throws
            AccountNotFoundException, WithdrawalAmountTooLargeException {
        Account account = AccountService.validateAccount(accountId);
        //Check the account type is Current
        if(!account.getAccountType().equals(AccountType.CURRENT)) {
            throw new AccountNotFoundException(String.format("Given Account Number: [%d] is not a Valid Current Account number", accountId));
        }
        if (((account.getBalance() + account.getOverdraft()) - withdrawAmount) < 0) {
            throw new WithdrawalAmountTooLargeException(
                    String.format("Your Account doesn't have sufficient balance to withdraw: [%d]. Current Balance: [%d], Overdraft allowed: [%d]"
                            , withdrawAmount, account.getBalance(), account.getOverdraft()));
        } else {
            //Update current balance
            account.setBalance(account.getBalance() - withdrawAmount);
            System.out.println("Current Account balance on AccountId: " + accountId + " after Withdraw: " + account.getBalance());
        }
    }

    public synchronized void deposit(Long accountId, int amountToDeposit)throws
            AccountNotFoundException {
        //Verify the accountId
        Account account = AccountService.validateAccount(accountId);
        //Check the account type is Current
        if(!account.getAccountType().equals(AccountType.CURRENT)) {
            throw new AccountNotFoundException(String.format("Given Account Number: [%d] is not a Valid Current Account number", accountId));
        }
        //Update current balance
        account.setBalance(account.getBalance() + amountToDeposit);
        System.out.println("Current Account balance on AccountId: " + accountId + " after Deposit: " + account.getBalance());
    }
}
