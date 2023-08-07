/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.khoders.invoicemaster.dto;

/**
 *
 * @author Pascal
 */
public class SalesTaxDto extends UserProp{
    private String salesTaxId;
    private String taxName;
    private double taxRate;
    private String proformaInvoice;
    private String proformaInvoiceId;
    private String saleLead;
    private String saleLeadId;
    private double taxAmount;
    private int reOrder;

    public String getSalesTaxId() {
        return salesTaxId;
    }

    public void setSalesTaxId(String salesTaxId) {
        this.salesTaxId = salesTaxId;
    }

    public String getTaxName() {
        return taxName;
    }

    public void setTaxName(String taxName) {
        this.taxName = taxName;
    }

    public double getTaxRate() {
        return taxRate;
    }

    public void setTaxRate(double taxRate) {
        this.taxRate = taxRate;
    }

    public String getProformaInvoice() {
        return proformaInvoice;
    }

    public void setProformaInvoice(String proformaInvoice) {
        this.proformaInvoice = proformaInvoice;
    }

    public String getProformaInvoiceId() {
        return proformaInvoiceId;
    }

    public void setProformaInvoiceId(String proformaInvoiceId) {
        this.proformaInvoiceId = proformaInvoiceId;
    }

    public String getSaleLead() {
        return saleLead;
    }

    public void setSaleLead(String saleLead) {
        this.saleLead = saleLead;
    }

    public String getSaleLeadId() {
        return saleLeadId;
    }

    public void setSaleLeadId(String saleLeadId) {
        this.saleLeadId = saleLeadId;
    }

    public double getTaxAmount() {
        return taxAmount;
    }

    public void setTaxAmount(double taxAmount) {
        this.taxAmount = taxAmount;
    }

    public int getReOrder() {
        return reOrder;
    }

    public void setReOrder(int reOrder) {
        this.reOrder = reOrder;
    }
    
}
