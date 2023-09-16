/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.khoders.invoicemaster.entities.accounts;

import com.khoders.invoicemaster.entities.Client;
import com.khoders.invoicemaster.entities.UserAccountRecord;
import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author Pascal
 */
@Entity
@Table(name = "cash_deposite")
public class CashDeposite extends UserAccountRecord{
    public final String _depositeAccount = "depositeAccount";
    @JoinColumn(name = "deposite_account", referencedColumnName = "id")
    @ManyToOne
    private Account depositeAccount;
    
    public final String _depositeDate = "depositeDate";
    @Column(name = "deposite_date")
    private LocalDate depositeDate;
    
    public final String _receiptNo = "receiptNo";
    @Column(name = "receipt_no")
    private String receiptNo;
    
    public final String _client = "client";
    public final String _clientId = Client._id;
    @JoinColumn(name = "client", referencedColumnName = "id")
    @ManyToOne
    private Client client;
    
    @Column(name = "memo")
    @Lob
    private String memo;

    public Account getDepositeAccount() {
        return depositeAccount;
    }

    public void setDepositeAccount(Account depositeAccount) {
        this.depositeAccount = depositeAccount;
    }

    public LocalDate getDepositeDate() {
        return depositeDate;
    }

    public void setDepositeDate(LocalDate depositeDate) {
        this.depositeDate = depositeDate;
    }

    public String getReceiptNo() {
        return receiptNo;
    }

    public void setReceiptNo(String receiptNo) {
        this.receiptNo = receiptNo;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }
    
}
