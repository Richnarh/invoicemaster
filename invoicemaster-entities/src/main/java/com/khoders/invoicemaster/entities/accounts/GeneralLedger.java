package com.khoders.invoicemaster.entities.accounts;

import com.khoders.invoicemaster.entities.UserAccountRecord;
import com.khoders.invoicemaster.enums.EntrySource;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "general_ledger")
public class GeneralLedger extends UserAccountRecord {
    public static final String _account = "account";
    @JoinColumn(name = "accounts", referencedColumnName = "id")
    @ManyToOne
    private Account account;

    @Column(name = "debit")
    private double debit;

    @Column(name = "entry_date")
    private LocalDate entryDate;

    @Column(name = "credit")
    private double credit;

    @Column(name = "entry_source")
    @Enumerated(EnumType.STRING)
    private EntrySource entrySource;

    @Column(name = "description")
    @Lob
    private String description;

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

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

    public EntrySource getEntrySource() {
        return entrySource;
    }

    public void setEntrySource(EntrySource entrySource) {
        this.entrySource = entrySource;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(LocalDate entryDate) {
        this.entryDate = entryDate;
    }
}
