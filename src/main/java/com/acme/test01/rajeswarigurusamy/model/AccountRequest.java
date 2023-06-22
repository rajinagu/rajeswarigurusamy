package com.acme.test01.rajeswarigurusamy.model;

public class AccountRequest {
    private Long accountId;
    private Integer amount;

    public AccountRequest(Long accountId, Integer deposit) {
        this.accountId = accountId;
        this.amount = deposit;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }
}
