package com.khoders.invoicemaster.entities.accounts;

import com.khoders.invoicemaster.entities.UserAccountRecord;

import javax.persistence.*;

@Entity
@Table(name = "bank_transaction")
public class BankTransaction extends UserAccountRecord {
    
    public static final String _account = "account";
    @JoinColumn(name = "accounts", referencedColumnName = "id")
    @ManyToOne
    private Account account;

    @Column(name = "amount")
    private double amount;

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

}
