package com.acme.test01.rajeswarigurusamy.repository;

import com.acme.test01.rajeswarigurusamy.model.Account;
import com.acme.test01.rajeswarigurusamy.model.CurrentAccount;
import com.acme.test01.rajeswarigurusamy.model.SavingsAccount;

import java.util.List;

public class SystemDB {
    private static SystemDB dbObject;
    public List<Account> accountList;
    
    private SystemDB() {
        // Populate accounts
        accountList = List.of(new SavingsAccount("1",2000),
                new SavingsAccount("2", 5000),
                new CurrentAccount("3", 1000, 10000),
                new CurrentAccount("4", -5000, 20000));
    }

    public static SystemDB getInstance() {
        // create object if it's not already created
        if(dbObject == null) {
            dbObject = new SystemDB();
        }

        // returns the singleton object
        return dbObject;
    }
}
