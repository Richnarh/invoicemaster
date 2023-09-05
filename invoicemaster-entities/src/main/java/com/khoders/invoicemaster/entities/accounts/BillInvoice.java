package com.khoders.invoicemaster.entities.accounts;

import com.khoders.resource.jpa.BaseModel;

import javax.persistence.*;

@MappedSuperclass
public class BillInvoice extends BaseModel {
    @Column(name = "quantity")
    private double quantity;

    @Column(name = "unit_price")
    private double unitPrice;

    @Column(name = "total_amount")
    private double totalAmount;

    @Column(name = "product")
    @Lob
    private String product;

    @JoinColumn(name = "accounts", referencedColumnName = "id")
    @ManyToOne
    private Account account;

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }
}
