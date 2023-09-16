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
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author Pascal
 */
@Entity
@Table(name = "cash_transfer")
public class CashTransfer extends UserAccountRecord{
    @Column(name = "transfer_date")
    private LocalDate transferDate;
    
    @Column(name = "total_amount")
    private double totalAmount;
    
    public final String _fromAccount = "fromAccount";
    @JoinColumn(name = "from_account", referencedColumnName = "id")
    @ManyToOne
    private Account fromAccount;
    
    public final String _toAccount = "toAccount";
    @JoinColumn(name = "to_account", referencedColumnName = "id")
    @ManyToOne
    private Account toAccount;
    
    @Column(name = "memo")
    @Lob
    private String memo;

    public LocalDate getTransferDate() {
        return transferDate;
    }

    public void setTransferDate(LocalDate transferDate) {
        this.transferDate = transferDate;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Account getFromAccount() {
        return fromAccount;
    }

    public void setFromAccount(Account fromAccount) {
        this.fromAccount = fromAccount;
    }

    public Account getToAccount() {
        return toAccount;
    }

    public void setToAccount(Account toAccount) {
        this.toAccount = toAccount;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }
    
}
