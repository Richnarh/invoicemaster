/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.khoders.invoicemaster.entities;

import com.khoders.resource.enums.PaymentMethod;
import com.khoders.resource.utilities.SystemUtils;
import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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
    
    @Column(name = "sub_total_amount")
    private double subTotalAmount;

    @Column(name = "mode_of_payment")
    @Enumerated(EnumType.STRING)
    private PaymentMethod modeOfPayment;

    @Column(name = "discount_rate")
    private double discountRate;
    
    @Column(name = "installation_fee")
    private double installationFee;

    @Column(name = "description")
    @Lob
    private String description;

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

    public PaymentMethod getModeOfPayment()
    {
        return modeOfPayment;
    }

    public void setModeOfPayment(PaymentMethod modeOfPayment)
    {
        this.modeOfPayment = modeOfPayment;
    }

    public double getDiscountRate()
    {
        return discountRate;
    }

    public void setDiscountRate(double discountRate)
    {
        this.discountRate = discountRate;
    }

    public double getInstallationFee()
    {
        return installationFee;
    }

    public void setInstallationFee(double installationFee)
    {
        this.installationFee = installationFee;
    }

    public double getSubTotalAmount()
    {
        return subTotalAmount;
    }

    public void setSubTotalAmount(double subTotalAmount)
    {
        this.subTotalAmount = subTotalAmount;
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
