package com.acme.test01.rajeswarigurusamy.services;

import com.acme.test01.rajeswarigurusamy.exceptions.AccountNotFoundException;
import com.acme.test01.rajeswarigurusamy.exceptions.WithdrawalAmountTooLargeException;
import com.acme.test01.rajeswarigurusamy.model.Account;
import com.acme.test01.rajeswarigurusamy.model.AccountType;
import org.springframework.stereotype.Service;

@Service
public class SavingsAccount implements AccountService {
    private static final int MIN_BALANCE = 2000;

    public void openSavingsAccount(Long accountId, int amountToDeposit) {
        if(amountToDeposit >= 2000) {
            //Creating saving account with all required fields
            System.out.println("New Savings Account has been created for AccountId : " + accountId);
        } else {
            System.out.println("New Savings Account can not be created as the given deposit amount: " + amountToDeposit + " not enough");
        }
    }

    public void openCurrentAccount(Long accountId){
        //Not implemented here
    }

    //Adding synchronization for withdraw and deposit to work fine during multithreading
    public synchronized void withdraw(Long accountId, int withdrawAmount) throws
            WithdrawalAmountTooLargeException {
        //Adding the following block of code incase some other API endpoint or service calls this method without validating account
        //Verify the accountId
        Account account = AccountService.validateAccount(accountId);
        //Check the account type is Savings
        if(!account.getAccountType().equals(AccountType.SAVINGS)) {
            throw new AccountNotFoundException(String.format("Given Account Number: [%d] is not a Valid Savings Account number", accountId));
        }

        //Check the minimum balance of 2000 (currentBalance - amount should be greater than 2000)
        if ((account.getBalance() - withdrawAmount) < MIN_BALANCE) {
            throw new WithdrawalAmountTooLargeException(
                    String.format("Your Account doesn't have sufficient balance to withdraw: [%d]. Current Balance: [%d]"
                            , withdrawAmount, account.getBalance()));
        } else {
            //Update current balance
            account.setBalance(account.getBalance() - withdrawAmount);
            System.out.println("Savings Account balance on AccountId: " + accountId + " after Withdraw: " + account.getBalance());
        }
    }

    public synchronized void deposit(Long accountId, int amountToDeposit){
        //Adding the following block of code incase some other API endpoint or service calls this method without validating account
        //Verify the accountId
        Account account = AccountService.validateAccount(accountId);
        //Check the account type is Savings
        if(!account.getAccountType().equals(AccountType.SAVINGS)) {
            throw new AccountNotFoundException(String.format("Given Account Number: [%d] is not a Valid Savings Account number", accountId));
        }

        //Update current balance
        account.setBalance(account.getBalance() + amountToDeposit);
        System.out.println("Savings Account balance on AccountId: " + accountId + " after Deposit: " + account.getBalance());
    }
}
