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
public class CashDepositeDto extends UserProp{
    private String depositeAccount;
    private String depositeAccountId;
    private String depositeDate;
    private String receiptNo;
    private String client;
    private String clientId;
    private String memo;

    public String getDepositeAccount() {
        return depositeAccount;
    }

    public void setDepositeAccount(String depositeAccount) {
        this.depositeAccount = depositeAccount;
    }

    public String getDepositeAccountId() {
        return depositeAccountId;
    }

    public void setDepositeAccountId(String depositeAccountId) {
        this.depositeAccountId = depositeAccountId;
    }

    public String getDepositeDate() {
        return depositeDate;
    }

    public void setDepositeDate(String depositeDate) {
        this.depositeDate = depositeDate;
    }

    public String getReceiptNo() {
        return receiptNo;
    }

    public void setReceiptNo(String receiptNo) {
        this.receiptNo = receiptNo;
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }
    
}
