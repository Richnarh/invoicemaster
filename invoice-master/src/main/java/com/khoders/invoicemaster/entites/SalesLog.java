/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.khoders.invoicemaster.entites;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author richa
 */
@Entity
@Table(name = "sales_log")
public class SalesLog extends UserAccountRecord implements Serializable
{
    @JoinColumn(name = "proforma_invoce_item")
    @ManyToOne
    private ProformaInvoiceItem proformaInvoiceItem;
    
    @Column(name = "tax_amount")
    private double totalTaxAmount;
        
    @Column(name = "total_discount")
    private double totalDiscount;
    
    @Column(name = "installation_fee")
    private double installationFee;
    
    @Column(name = "totalAmount")
    private double totalAmount;

    public ProformaInvoiceItem getProformaInvoiceItem()
    {
        return proformaInvoiceItem;
    }

    public void setProformaInvoiceItem(ProformaInvoiceItem proformaInvoiceItem)
    {
        this.proformaInvoiceItem = proformaInvoiceItem;
    }

    public double getTotalTaxAmount()
    {
        return totalTaxAmount;
    }

    public void setTotalTaxAmount(double totalTaxAmount)
    {
        this.totalTaxAmount = totalTaxAmount;
    }

    public double getTotalDiscount()
    {
        return totalDiscount;
    }

    public void setTotalDiscount(double totalDiscount)
    {
        this.totalDiscount = totalDiscount;
    }

    public double getInstallationFee()
    {
        return installationFee;
    }

    public void setInstallationFee(double installationFee)
    {
        this.installationFee = installationFee;
    }

    public double getTotalAmount()
    {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount)
    {
        this.totalAmount = totalAmount;
    }
    
    
}
