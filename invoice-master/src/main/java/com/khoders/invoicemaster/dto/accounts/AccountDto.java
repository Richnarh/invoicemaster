package com.khoders.invoicemaster.dto.accounts;


import com.khoders.invoicemaster.dto.UserProp;
import com.khoders.invoicemaster.enums.AccountType;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Richard Narh
 */
public class AccountDto extends UserProp{
    private String accountName;
    private String parentAccount;
    private String accountCode;
    private AccountType accountType;
    private boolean subAccount;
    private String description;

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getParentAccount() {
        return parentAccount;
    }

    public void setParentAccount(String parentAccount) {
        this.parentAccount = parentAccount;
    }

    public String getAccountCode() {
        return accountCode;
    }

    public void setAccountCode(String accountCode) {
        this.accountCode = accountCode;
    }

    public AccountType getAccountType() {
        return accountType;
    }

    public void setAccountType(AccountType accountType) {
        this.accountType = accountType;
    }

    public boolean isSubAccount() {
        return subAccount;
    }

    public void setSubAccount(boolean subAccount) {
        this.subAccount = subAccount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
}
