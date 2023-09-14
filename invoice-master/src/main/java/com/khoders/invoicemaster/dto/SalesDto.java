/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.khoders.invoicemaster.dto;

import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Pascal
 */
public class SalesDto extends UserProp{
    private String actionType;
    private String salesLeadId;
    private String invoiceId;
    double totalPayable;
    double installationFee;
    double totalAmount;
    double discountRate;
    double subTotal;
    private List<InvoiceItemDto> invoiceItemList = new LinkedList<>();
    private List<SalesTaxDto> salesTaxList = new LinkedList<>();

    public String getActionType() {
        return actionType;
    }

    public void setActionType(String actionType) {
        this.actionType = actionType;
    }

    public double getTotalPayable() {
        return totalPayable;
    }

    public String getSalesLeadId() {
        return salesLeadId;
    }

    public void setSalesLeadId(String salesLeadId) {
        this.salesLeadId = salesLeadId;
    }

    public void setTotalPayable(double totalPayable) {
        this.totalPayable = totalPayable;
    }
    
    public List<InvoiceItemDto> getInvoiceItemList() {
        return invoiceItemList;
    }

    public void setInvoiceItemList(List<InvoiceItemDto> invoiceItemList) {
        this.invoiceItemList = invoiceItemList;
    }

    public List<SalesTaxDto> getSalesTaxList() {
        return salesTaxList;
    }

    public void setSalesTaxList(List<SalesTaxDto> salesTaxList) {
        this.salesTaxList = salesTaxList;
    }

    public double getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(double subTotal) {
        this.subTotal = subTotal;
    }

    public String getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(String invoiceId) {
        this.invoiceId = invoiceId;
    }

    public double getInstallationFee() {
        return installationFee;
    }

    public void setInstallationFee(double installationFee) {
        this.installationFee = installationFee;
    }

    public double getDiscountRate() {
        return discountRate;
    }

    public void setDiscountRate(double discountRate) {
        this.discountRate = discountRate;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }
    
}
