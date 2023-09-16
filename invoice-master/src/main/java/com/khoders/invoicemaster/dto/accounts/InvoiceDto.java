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
public class InvoiceDto extends UserProp{
    private String client;
    private String clientId;
    private String invoiceNo;
    private String dueDate;
    private String memo;
    private double balanceOverDue;
    private double totalAmount;
    private String customerNotes;
    private String termsCondition;
    private List<InvoiceItemDto> invoiceItemList = new LinkedList<>();

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

    public String getInvoiceNo() {
        return invoiceNo;
    }

    public void setInvoiceNo(String invoiceNo) {
        this.invoiceNo = invoiceNo;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public double getBalanceOverDue() {
        return balanceOverDue;
    }

    public void setBalanceOverDue(double balanceOverDue) {
        this.balanceOverDue = balanceOverDue;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getCustomerNotes() {
        return customerNotes;
    }

    public void setCustomerNotes(String customerNotes) {
        this.customerNotes = customerNotes;
    }

    public String getTermsCondition() {
        return termsCondition;
    }

    public void setTermsCondition(String termsCondition) {
        this.termsCondition = termsCondition;
    }

    public List<InvoiceItemDto> getInvoiceItemList() {
        return invoiceItemList;
    }

    public void setInvoiceItemList(List<InvoiceItemDto> invoiceItemList) {
        this.invoiceItemList = invoiceItemList;
    }
    
}
