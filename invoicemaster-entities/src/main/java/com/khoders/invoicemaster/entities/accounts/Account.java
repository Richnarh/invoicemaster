/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.khoders.invoicemaster.entities.accounts;

import com.khoders.invoicemaster.entities.UserAccountRecord;
import com.khoders.invoicemaster.enums.AccountType;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Lob;
import javax.persistence.Table;

/**
 *
 * @author Pascal
 */
@Entity
@Table(name = "accounts")
public class Account extends UserAccountRecord implements Serializable{
    @Column(name = "account_name")
    private String accountName;

    @Column(name = "parent_account")
    private String parentAccount;

    @Column(name = "account_code")
    private String accountCode;

    @Column(name = "account_type")
    @Enumerated(EnumType.STRING)
    private AccountType accountType;

    @Column(name = "sub_account")
    private boolean subAccount;

    @Column(name = "description")
    @Lob
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

    @Override
    public String toString() {
        return accountName;
    }
}
