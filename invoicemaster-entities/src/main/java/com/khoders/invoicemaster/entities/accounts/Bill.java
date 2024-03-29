package com.khoders.invoicemaster.entities.accounts;

import com.khoders.invoicemaster.entities.Client;
import com.khoders.invoicemaster.entities.UserAccountRecord;
import javax.persistence.*;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

@Entity
@Table(name = "bills")
public class Bill extends UserAccountRecord {
    public static final String _client = "client";
    @JoinColumn(name = "client", referencedColumnName = "id")
    @ManyToOne
    private Client client;

    public static final String _receiptNo = "receiptNo";
    @Column(name = "receipt_no")
    private String receiptNo;

    public static final String _billDate = "billDate";
    @Column(name = "bill_date")
    private LocalDate billDate;

    @Column(name = "memo")
    @Lob
    private String memo;

    @Column(name = "total_amount")
    private double totalAmount;

    public static final String _balanceOverDue = "balanceOverDue";
    @Column(name = "balance_overdue")
    private double balanceOverDue;

    @Transient
    private List<BillItem> billItemList = new LinkedList<>();

    public String getReceiptNo() {
        return receiptNo;
    }

    public void setReceiptNo(String receiptNo) {
        this.receiptNo = receiptNo;
    }

    public LocalDate getBillDate() {
        return billDate;
    }

    public void setBillDate(LocalDate billDate) {
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

    public List<BillItem> getBillItemList() {
        return billItemList;
    }

    public void setBillItemList(List<BillItem> billItemList) {
        this.billItemList = billItemList;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }
    
}
