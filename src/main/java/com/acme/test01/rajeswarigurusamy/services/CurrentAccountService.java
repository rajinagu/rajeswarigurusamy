package com.acme.test01.rajeswarigurusamy.services;

import com.acme.test01.rajeswarigurusamy.exceptions.AccountNotFoundException;
import com.acme.test01.rajeswarigurusamy.exceptions.WithdrawalAmountTooLargeException;
import com.acme.test01.rajeswarigurusamy.model.AccountType;
import com.acme.test01.rajeswarigurusamy.model.CurrentAccount;
import com.acme.test01.rajeswarigurusamy.model.SavingsAccount;

public class CurrentAccountService implements AccountService {

    public static final int OVERDRAFT = 100000;

    public void openCurrentAccount(String customerId){
        //Setting overdraft limit as Max Overdraft (provided by ACME bank) while opening accounts
        // This can be updated later to include overdraft limit as a param
        CurrentAccount currentAccount = new CurrentAccount(customerId,0, OVERDRAFT);
        System.out.println("New Current Account has been created for customer : " + customerId + " AccountId: " + currentAccount.getAccountId());
    }

    public void openSavingsAccount(String customerId, int amountToDeposit) {
        // Not implemeted here
    }
    //Adding synchronization for withdraw and deposit to work fine during multithreading
    public synchronized void withdraw(Long accountId, int withdrawAmount) throws
            AccountNotFoundException, WithdrawalAmountTooLargeException {
        CurrentAccount currentAccount = (CurrentAccount) AccountService.validateAccount(accountId);
        //Check the account type is Current
        if(!currentAccount.getAccountType().equals(AccountType.CURRENT)) {
            throw new AccountNotFoundException(String.format("Given Account Number: [%d] is not a Valid Current Account number", accountId));
        }
        if (((currentAccount.getBalance() + currentAccount.getOverdraft()) - withdrawAmount) < 0) {
            throw new WithdrawalAmountTooLargeException(
                    String.format("Your Account doesn't have sufficient balance to withdraw: [%d]. Current Balance: [%d], Overdraft allowed: [%d]"
                            , withdrawAmount, currentAccount.getBalance(), currentAccount.getOverdraft()));
        } else {
            //Update current balance
            currentAccount.setBalance(currentAccount.getBalance() - withdrawAmount);
            System.out.println("Current Account balance on AccountId: " + accountId + " after Withdraw: " + currentAccount.getBalance());
        }
    }

    public synchronized void deposit(Long accountId, int amountToDeposit)throws
            AccountNotFoundException {
        //Verify the accountId
        CurrentAccount currentAccount = (CurrentAccount) AccountService.validateAccount(accountId);
        //Check the account type is Current
        if(!currentAccount.getAccountType().equals(AccountType.CURRENT)) {
            throw new AccountNotFoundException(String.format("Given Account Number: [%d] is not a Valid Current Account number", accountId));
        }
        //Update current balance
        currentAccount.setBalance(currentAccount.getBalance() + amountToDeposit);
        System.out.println("Current Account balance on AccountId: " + accountId + " after Deposit: " + currentAccount.getBalance());
    }
}
