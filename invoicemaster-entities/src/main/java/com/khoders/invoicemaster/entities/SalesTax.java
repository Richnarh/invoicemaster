/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.khoders.invoicemaster.entities;

import com.khoders.resource.utilities.SystemUtils;
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
@Table(name = "sales_tax")
public class SalesTax extends UserAccountRecord implements Serializable
{
    @Column(name = "sales_tax_id")
    private String salesTaxId;

    @Column(name = "tax_name")
    private String taxName;

    @Column(name = "tax_rate")
    private double taxRate;
  
    public static final String _proformaInvoice = "proformaInvoice";
    @JoinColumn(name = "proforma_invoice", referencedColumnName = "id")
    @ManyToOne
    private ProformaInvoice proformaInvoice;
    
    public static final String _taxGroup = "taxGroup";
    @JoinColumn(name = "tax_group", referencedColumnName = "id")
    @ManyToOne
    private TaxGroup taxGroup;
    
    @JoinColumn(name = "sale_lead", referencedColumnName = "id")
    @ManyToOne
    private SaleLead saleLead;
    
    @Column(name = "tax_amount")
    private double taxAmount;
    
    @Column(name = "reorder")
    private int reOrder;
    

    public String getSalesTaxId()
    {
        return salesTaxId;
    }

    public void setSalesTaxId(String salesTaxId)
    {
        this.salesTaxId = salesTaxId;
    }

    public String getTaxName()
    {
        return taxName;
    }

    public void setTaxName(String taxName)
    {
        this.taxName = taxName;
    }

    public double getTaxRate()
    {
        return taxRate;
    }

    public void setTaxRate(double taxRate)
    {
        this.taxRate = taxRate;
    }
    
    public double getTaxAmount()
    {
        return taxAmount;
    }

    public void setTaxAmount(double taxAmount)
    {
        this.taxAmount = taxAmount;
    }

    public ProformaInvoice getProformaInvoice()
    {
        return proformaInvoice;
    }

    public void setProformaInvoice(ProformaInvoice proformaInvoice)
    {
        this.proformaInvoice = proformaInvoice;
    }
    
    public int getReOrder()
    {
        return reOrder;
    }

    public void setReOrder(int reOrder)
    {
        this.reOrder = reOrder;
    }

    public SaleLead getSaleLead() {
        return saleLead;
    }

    public void setSaleLead(SaleLead saleLead) {
        this.saleLead = saleLead;
    }

    public TaxGroup getTaxGroup() {
        return taxGroup;
    }

    public void setTaxGroup(TaxGroup taxGroup) {
        this.taxGroup = taxGroup;
    }
    
    public void genCode()
    {
        if (getSalesTaxId() != null)
        {
            setSalesTaxId(getSalesTaxId());
        } else
        {
            setSalesTaxId(SystemUtils.generateCode());
        }
    }
 }
