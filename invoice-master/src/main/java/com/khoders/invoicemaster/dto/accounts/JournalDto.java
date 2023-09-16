/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.khoders.invoicemaster.dto.accounts;

import com.khoders.invoicemaster.dto.UserProp;
import com.khoders.invoicemaster.enums.DebitCredit;
import com.khoders.invoicemaster.enums.EntrySource;

/**
 *
 * @author Richard Narh
 */
public class JournalDto extends UserProp{
    private double debit;
    private double credit;
    private double amount;
    private String description;
    private String account;
    private String accountId;
    private DebitCredit debitCredit;
    private EntrySource entrySource;

    public double getDebit() {
        return debit;
    }

    public void setDebit(double debit) {
        this.debit = debit;
    }

    public double getCredit() {
        return credit;
    }

    public void setCredit(double credit) {
        this.credit = credit;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

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

    public DebitCredit getDebitCredit() {
        return debitCredit;
    }

    public void setDebitCredit(DebitCredit debitCredit) {
        this.debitCredit = debitCredit;
    }

    public EntrySource getEntrySource() {
        return entrySource;
    }

    public void setEntrySource(EntrySource entrySource) {
        this.entrySource = entrySource;
    }
    
}
