package com.acme.test01.rajeswarigurusamy.services;

import com.acme.test01.rajeswarigurusamy.exceptions.AccountNotFoundException;
import com.acme.test01.rajeswarigurusamy.exceptions.WithdrawalAmountTooLargeException;
import com.acme.test01.rajeswarigurusamy.model.AccountType;
import com.acme.test01.rajeswarigurusamy.model.SavingsAccount;

public class SavingsAccountService implements AccountService{
    private static final int MIN_BALANCE = 2000;
    public void openSavingsAccount(String customerId, int amountToDeposit) {
        if(amountToDeposit >= 2000) {
           SavingsAccount savingsAccount =  new SavingsAccount(customerId,amountToDeposit);
            System.out.println("New Savings Account has been created for customer : " + customerId + " AccountId: " + savingsAccount.getAccountId());
        } else {
            System.out.println("New Savings Account can not be created as the given deposit amount: " + amountToDeposit + " not enough");
        }
    }

    //Adding synchronization for withdraw and deposit to work fine during multithreading
    public synchronized void withdraw(Long accountId, int withdrawAmount) throws
            WithdrawalAmountTooLargeException {
        //Verify the accountId
        SavingsAccount savingsAccount = (SavingsAccount) AccountService.validateAccount(accountId);
        //Check the account type is Savings
        if(!savingsAccount.getAccountType().equals(AccountType.SAVINGS)) {
            throw new AccountNotFoundException(String.format("Given Account Number: [%d] is not a Valid Savings Account number", accountId));
        }
        //Check the minimum balance of 2000 (currentBalance - amount should be greater than 2000)
        if ((savingsAccount.getBalance() - withdrawAmount) < MIN_BALANCE) {
            throw new WithdrawalAmountTooLargeException(
                    String.format("Your Account doesn't have sufficient balance to withdraw: [%d]. Current Balance: [%d]"
                            , withdrawAmount, savingsAccount.getBalance()));
        } else {
            //Update current balance
            savingsAccount.setBalance(savingsAccount.getBalance() - withdrawAmount);
            System.out.println("Savings Account balance on AccountId: " + accountId + " after Withdraw: " + savingsAccount.getBalance());
        }
    }

    public synchronized void deposit(Long accountId, int amountToDeposit){
        //Verify the accountId
        SavingsAccount savingsAccount = (SavingsAccount) AccountService.validateAccount(accountId);
        //Check the account type is Savings
        if(!savingsAccount.getAccountType().equals(AccountType.SAVINGS)) {
            throw new AccountNotFoundException(String.format("Given Account Number: [%d] is not a Valid Savings Account number", accountId));
        }
        //Update current balance
        savingsAccount.setBalance(savingsAccount.getBalance() + amountToDeposit);
        System.out.println("Savings Account balance on AccountId: " + accountId + " after Deposit: " + savingsAccount.getBalance());
    }
}
