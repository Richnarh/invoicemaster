/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.khoders.invoicemaster.entities.accounts;

import com.khoders.invoicemaster.entities.UserAccountRecord;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author Pascal
 */
@Entity
@Table(name = "deposite_item")
public class DepositeItem extends UserAccountRecord{
    public static final String _account = "account";
    @JoinColumn(name = "accounts", referencedColumnName = "id")
    @ManyToOne
    private Account account;
    
    public static final String _cashDeposite = "cashDeposite";
    public static final String _cashDepositeId = CashDeposite._id;
    @JoinColumn(name = "cash_deposite", referencedColumnName = "id")
    @ManyToOne
    private CashDeposite cashDeposite;
    
    @Column(name = "description")
    private String description;
    
    @Column(name = "amount_deposite")
    private double amountDeposite;

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
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

    public CashDeposite getCashDeposite() {
        return cashDeposite;
    }

    public void setCashDeposite(CashDeposite cashDeposite) {
        this.cashDeposite = cashDeposite;
    }
    
    
}
