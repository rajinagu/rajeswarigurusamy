package com.acme.test01.rajeswarigurusamy.repository;

import com.acme.test01.rajeswarigurusamy.model.Account;
import com.acme.test01.rajeswarigurusamy.model.AccountType;

import java.util.List;

public class SystemDB {
    private static SystemDB dbObject;
    public List<Account> accountList;
    
    private SystemDB() {
        // Populate accounts
        accountList = List.of(new Account(1L, 1L, "1", AccountType.SAVINGS, 2000, 0),
                new Account(2L, 2L, "2", AccountType.SAVINGS, 5000, 0),
                new Account(3L, 3L, "3", AccountType.CURRENT,1000, 10000),
                new Account(4L, 4L, "4", AccountType.CURRENT,-5000, 20000));
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
