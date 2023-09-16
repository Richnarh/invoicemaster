/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.khoders.invoicemaster.dto.accounts;

import com.khoders.invoicemaster.dto.UserProp;

/**
 *
 * @author Richard Narh
 */
public class DepositeItemDto extends UserProp{
    private String account;
    private String accountId;
    private String cashDeposite;
    private String cashDepositeId;
    private String description;
    private double amountDeposite;

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getCashDeposite() {
        return cashDeposite;
    }

    public void setCashDeposite(String cashDeposite) {
        this.cashDeposite = cashDeposite;
    }

    public String getCashDepositeId() {
        return cashDepositeId;
    }

    public void setCashDepositeId(String cashDepositeId) {
        this.cashDepositeId = cashDepositeId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getAmountDeposite() {
        return amountDeposite;
    }

    public void setAmountDeposite(double amountDeposite) {
        this.amountDeposite = amountDeposite;
    }
    
}
