package com.khoders.invoicemaster.entities.accounts;

import com.khoders.invoicemaster.entities.Client;
import com.khoders.invoicemaster.entities.UserAccountRecord;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

@Entity
@Table(name = "expense")
public class Expense extends UserAccountRecord {
    @Column(name = "expense_date")
    private LocalDate expenseDate;

    @Column(name = "receipt_no")
    private String receiptNo;

    @JoinColumn(name = "account")
    @ManyToOne
    private Account account;

    @JoinColumn(name = "client")
    @ManyToOne
    private Client client;

    @Transient
    private List<ExpenseItem> expenseItemList = new LinkedList<>();

    public LocalDate getExpenseDate() {
        return expenseDate;
    }

    public void setExpenseDate(LocalDate expenseDate) {
        this.expenseDate = expenseDate;
    }

    public String getReceiptNo() {
        return receiptNo;
    }

    public void setReceiptNo(String receiptNo) {
        this.receiptNo = receiptNo;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }
    
    public List<ExpenseItem> getExpenseItemList() {
        return expenseItemList;
    }

    public void setExpenseItemList(List<ExpenseItem> expenseItemList) {
        this.expenseItemList = expenseItemList;
    }
}
