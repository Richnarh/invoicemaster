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
public class PettyCashDto extends UserProp{
    private String fundDate;
    private String fundName;
    private String pettyAccount;
    private String pettyAccountId;
    private String fundPurpose;
    private double totalAmount;
    private double fundBalance;
    private double moneyUsed;

    public String getFundDate() {
        return fundDate;
    }

    public void setFundDate(String fundDate) {
        this.fundDate = fundDate;
    }

    public String getFundName() {
        return fundName;
    }

    public void setFundName(String fundName) {
        this.fundName = fundName;
    }

    public String getPettyAccount() {
        return pettyAccount;
    }

    public void setPettyAccount(String pettyAccount) {
        this.pettyAccount = pettyAccount;
    }

    public String getPettyAccountId() {
        return pettyAccountId;
    }

    public void setPettyAccountId(String pettyAccountId) {
        this.pettyAccountId = pettyAccountId;
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
