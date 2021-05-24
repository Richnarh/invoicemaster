/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.khoders.invoicemaster.entites;

import com.khoders.resource.utilities.SystemUtils;
import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author pascal
 */
@Entity
@Table(name = "proforma_invoice")
public class ProformaInvoice extends UserAccountRecord implements Serializable
{
    @Column(name = "issued_date")
    private LocalDate issuedDate = LocalDate.now();
   
    @Column(name = "expiry_date")
    private LocalDate expiryDate;
   
    @JoinColumn(name = "client", referencedColumnName = "id")
    @ManyToOne
    private Client client;

    @Column(name = "quotation_number")
    private String quotationNumber = SystemUtils.generateRefNo();

    @Column(name = "total_amount")
    private double totalAmount;

    @Column(name = "converted")
    private boolean converted;

    @Column(name = "description")
    @Lob
    private String description;

    public boolean isConverted()
    {
        return converted;
    }

    public void setConverted(boolean converted)
    {
        this.converted = converted;
    }

    public LocalDate getIssuedDate()
    {
        return issuedDate;
    }

    public void setIssuedDate(LocalDate issuedDate)
    {
        this.issuedDate = issuedDate;
    }

    public LocalDate getExpiryDate()
    {
        return expiryDate;
    }

    public void setExpiryDate(LocalDate expiryDate)
    {
        this.expiryDate = expiryDate;
    }
    
    public Client getClient()
    {
        return client;
    }

    public void setClient(Client client)
    {
        this.client = client;
    }
    
    public String getQuotationNumber()
    {
        return quotationNumber;
    }

    public void setQuotationNumber(String quotationNumber)
    {
        this.quotationNumber = quotationNumber;
    }

    public double getTotalAmount()
    {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount)
    {
        this.totalAmount = totalAmount;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public void genCode()
    {
        if (getQuotationNumber() != null)
        {
            setQuotationNumber(getQuotationNumber());
        } else
        {
            setQuotationNumber(SystemUtils.generateRefNo());
        }
    }
    
    @Override
    public String toString()
    {
        return quotationNumber;
    }
}
