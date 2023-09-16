package com.khoders.invoicemaster.entities.accounts;

import com.khoders.invoicemaster.entities.UserAccountRecord;
import com.khoders.invoicemaster.enums.DebitCredit;
import com.khoders.invoicemaster.enums.EntrySource;
import com.khoders.resource.jpa.BaseModel;

import javax.persistence.*;

@Entity
@Table(name = "journal")
public class Journal extends UserAccountRecord {
    @Column(name = "debit")
    private double debit;

    @Column(name = "credit")
    private double credit;

    @Column(name = "amount")
    private double amount;

    @Column(name = "description")
    private String description;

    @JoinColumn(name = "accounts", referencedColumnName = "id")
    @ManyToOne
    private Account account;

    @Column(name = "debit_credit")
    @Enumerated(EnumType.STRING)
    private DebitCredit debitCredit;
    
    @Column(name = "entry_source")
    @Enumerated(EnumType.STRING)
    private EntrySource entrySource;

    public double getDebit() {
        return debit;
    }

    public void setDebit(double debit) {
        this.debit = debit;
    }

    public double getCredit() {
        return credit;
    }

    public void setCredit(double credit) {
        this.credit = credit;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public DebitCredit getDebitCredit() {
        return debitCredit;
    }

    public void setDebitCredit(DebitCredit debitCredit) {
        this.debitCredit = debitCredit;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public EntrySource getEntrySource() {
        return entrySource;
    }

    public void setEntrySource(EntrySource entrySource) {
        this.entrySource = entrySource;
    }
    
}
