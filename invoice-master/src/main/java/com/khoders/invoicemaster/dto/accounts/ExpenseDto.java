/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.khoders.invoicemaster.dto.accounts;

import com.khoders.invoicemaster.dto.UserProp;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Richard Narh
 */
public class ExpenseDto extends UserProp{
    private String expenseDate;
    private String receiptNo;
    private String account;
    private String accountId;
    private String client;
    private String clientId;
    private List<ExpenseItemDto> expenseItemList = new LinkedList<>();

    public String getExpenseDate() {
        return expenseDate;
    }

    public void setExpenseDate(String expenseDate) {
        this.expenseDate = expenseDate;
    }

    public String getReceiptNo() {
        return receiptNo;
    }

    public void setReceiptNo(String receiptNo) {
        this.receiptNo = receiptNo;
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

    public List<ExpenseItemDto> getExpenseItemList() {
        return expenseItemList;
    }

    public void setExpenseItemList(List<ExpenseItemDto> expenseItemList) {
        this.expenseItemList = expenseItemList;
    }
    
}
