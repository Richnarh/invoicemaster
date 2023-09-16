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
@Table(name = "bank_statement")
public class BankStatement extends UserAccountRecord{
    public final String _bankAccount = "bankAccount";
    @JoinColumn(name = "accounts", referencedColumnName = "id")
    @ManyToOne
    private Account bankAccount;
    
    public final String _statementDate = "statementDate";
    @Column(name = "statement_date")
    private LocalDate statementDate;
    
    public final String _description = "description";
    @Column(name = "description")
    @Lob
    private String description;
    
    public final String _credit = "credit";
    @Column(name = "credit")
    private double credit;
    
    public final String _debit = "debit";
    @Column(name = "debit")
    private double debit;
    
    @Column(name = "balance")
    private double balance;
    
    @Column(name = "reference_account")
    private String referenceAccount;
    
    @Column(name = "reference_party")
    private String referenceParty;
    
    @Column(name = "product")
    private String product;

    public Account getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(Account bankAccount) {
        this.bankAccount = bankAccount;
    }

    public LocalDate getStatementDate() {
        return statementDate;
    }

    public void setStatementDate(LocalDate statementDate) {
        this.statementDate = statementDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getCredit() {
        return credit;
    }

    public void setCredit(double credit) {
        this.credit = credit;
    }

    public double getDebit() {
        return debit;
    }

    public void setDebit(double debit) {
        this.debit = debit;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public String getReferenceAccount() {
        return referenceAccount;
    }

    public void setReferenceAccount(String referenceAccount) {
        this.referenceAccount = referenceAccount;
    }

    public String getReferenceParty() {
        return referenceParty;
    }

    public void setReferenceParty(String referenceParty) {
        this.referenceParty = referenceParty;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    @Override
    public String toString() {
        return bankAccount+"";
    }
    
    
}
