package com.acme.test01.rajeswarigurusamy.controller;

import com.acme.test01.rajeswarigurusamy.model.Account;
import com.acme.test01.rajeswarigurusamy.model.AccountRequest;
import com.acme.test01.rajeswarigurusamy.model.AccountType;
import com.acme.test01.rajeswarigurusamy.services.AccountService;
import com.acme.test01.rajeswarigurusamy.services.CurrentAccount;
import com.acme.test01.rajeswarigurusamy.services.SavingsAccount;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api")
public class AccountController {
    private final SavingsAccount savingsAccount;

    private final CurrentAccount currentAccount;

    public AccountController(SavingsAccount savingsAccount, CurrentAccount currentAccount) {
        this.savingsAccount = savingsAccount;
        this.currentAccount = currentAccount;
    }

    @RequestMapping(value = "/withdraw", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity withdraw(@RequestBody AccountRequest request) {
        int balance;
        try {
            Account account = AccountService.validateAccount(request.getAccountId());
            //Validate the amount is not in negative
            if(request.getAmount() <= 0) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("Message", "Please enter a valid amount"));
            }

            if(AccountType.SAVINGS.equals(account.getAccountType())) {
                savingsAccount.withdraw(request.getAccountId(), request.getAmount());
            } else if(AccountType.CURRENT.equals(account.getAccountType())) {
                currentAccount.withdraw(request.getAccountId(), request.getAmount());
            }
            balance = account.getBalance();
        } catch(Exception e) {
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(Map.of("Message", e.getMessage()));
        }

        return ResponseEntity.status(HttpStatus.OK)
                .body(Map.of("Current Balance", balance));
    }

    @RequestMapping(value = "/deposit", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity deposit(@RequestBody AccountRequest request) {
        int balance;
        try {
            Account account = AccountService.validateAccount(request.getAccountId());
            if(request.getAmount() <= 0) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("Message", "Please enter a valid amount"));
            }

            if(AccountType.SAVINGS.equals(account.getAccountType())) {
                savingsAccount.deposit(request.getAccountId(), request.getAmount());
            } else if(AccountType.CURRENT.equals(account.getAccountType())) {
                currentAccount.deposit(request.getAccountId(), request.getAmount());
            }
            balance = account.getBalance();
        } catch(Exception e) {
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(Map.of("Message", e.getMessage()));
        }

        return ResponseEntity.status(HttpStatus.OK)
                .body(Map.of("Current Balance", balance));
    }
}
