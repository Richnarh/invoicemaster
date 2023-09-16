/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.khoders.invoicemaster.entities.accounts;

import com.khoders.invoicemaster.entities.UserAccountRecord;
import java.time.LocalDate;
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
@Table(name = "petty_cash")
public class PettyCash extends UserAccountRecord{
    private LocalDate date;
    private String fundName;
    
    public final String _pettyAccount = "pettyAccount";
    @JoinColumn(name = "accounts")
    @ManyToOne
    private Account pettyAccount;
    
    @Column(name = "fund_purpose")
    private String fundPurpose;
    
    @Column(name = "total_amount")
    private double totalAmount;
    
    @Column(name = "fund_balance")
    private double fundBalance;
    
    @Column(name = "money_used")
    private double moneyUsed;

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getFundName() {
        return fundName;
    }

    public void setFundName(String fundName) {
        this.fundName = fundName;
    }

    public Account getPettyAccount() {
        return pettyAccount;
    }

    public void setPettyAccount(Account pettyAccount) {
        this.pettyAccount = pettyAccount;
    }

    public String getFundPurpose() {
        return fundPurpose;
    }

    public void setFundPurpose(String fundPurpose) {
        this.fundPurpose = fundPurpose;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public double getFundBalance() {
        return fundBalance;
    }

    public void setFundBalance(double fundBalance) {
        this.fundBalance = fundBalance;
    }

    public double getMoneyUsed() {
        return moneyUsed;
    }

    public void setMoneyUsed(double moneyUsed) {
        this.moneyUsed = moneyUsed;
    }
    
}
