package com.khoders.invoicemaster.entities.accounts;

import com.khoders.invoicemaster.entities.Client;
import com.khoders.resource.jpa.BaseModel;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

@Entity
@Table(name = "invoices")
public class Invoice extends BaseModel {
    @JoinColumn(name = "client", referencedColumnName = "id")
    @ManyToOne
    private Client client;

    @Column(name = "invoice_no")
    private String invoiceNo;

    @Column(name = "due_date")
    private LocalDate dueDate;

    @Column(name = "memo")
    @Lob
    private String memo;

    @Column(name = "balance_overdue")
    private double balanceOverDue;

    @Column(name = "total_amount")
    private double totalAmount;

    @Column(name = "customer_notes")
    @Lob
    private String customerNotes;

    @Column(name = "terms_condition")
    @Lob
    private String termsCondition;

    @Transient
    private List<InvoiceItem> invoiceItemList = new LinkedList<>();

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }
    
    public String getInvoiceNo() {
        return invoiceNo;
    }

    public void setInvoiceNo(String invoiceNo) {
        this.invoiceNo = invoiceNo;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
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

    public List<InvoiceItem> getInvoiceItemList() {
        return invoiceItemList;
    }

    public void setInvoiceItemList(List<InvoiceItem> invoiceItemList) {
        this.invoiceItemList = invoiceItemList;
    }
}
