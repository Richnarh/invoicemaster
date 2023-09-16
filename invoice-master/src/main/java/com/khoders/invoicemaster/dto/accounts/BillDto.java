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
public class BillDto extends UserProp{
    private String client;
    private String clientId;
    private String receiptNo;
    private String billDate;
    private String memo;
    private double totalAmount;
    private double balanceOverDue;
    private List<BillItemDto> billItemList = new LinkedList<>();

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

    public String getReceiptNo() {
        return receiptNo;
    }

    public void setReceiptNo(String receiptNo) {
        this.receiptNo = receiptNo;
    }

    public String getBillDate() {
        return billDate;
    }

    public void setBillDate(String billDate) {
        this.billDate = billDate;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public double getBalanceOverDue() {
        return balanceOverDue;
    }

    public void setBalanceOverDue(double balanceOverDue) {
        this.balanceOverDue = balanceOverDue;
    }

    public List<BillItemDto> getBillItemList() {
        return billItemList;
    }

    public void setBillItemList(List<BillItemDto> billItemList) {
        this.billItemList = billItemList;
    }
    
}
