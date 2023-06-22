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
    public ResponseEntity getChatToPayConfig(@RequestBody AccountRequest request) {
        try {
            Account account = AccountService.validateAccount(request.getAccountId());
            if(AccountType.SAVINGS.equals(account.getAccountType())) {
                savingsAccount.withdraw(request.getAccountId(), request.getAmount());
            } else if(AccountType.CURRENT.equals(account.getAccountType())) {
                currentAccount.withdraw(request.getAccountId(), request.getAmount());
            }
        } catch(Exception e) {
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(Map.of("Message", e.getMessage()));
        }

        return ResponseEntity.ok().build();
    }
}
