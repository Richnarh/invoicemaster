/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.khoders.invoicemaster.entites;

import com.khoders.resource.enums.PaymentMethod;
import com.khoders.resource.enums.PaymentStatus;
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
@Table(name = "payment_receipt")
public class PaymentReceipt extends UserAccountRecord implements Serializable
{
    @Column(name = "receipt_no")
    private String receiptNumber = SystemUtils.generateRefNo();
    
    @Column(name = "payment_code")
    private String paymentCode  = SystemUtils.generateCode();
    
    @JoinColumn(name = "received_from", referencedColumnName = "id")
    @ManyToOne
    private Client receivedFrom;
    
    @Column(name = "payment_date")
    private LocalDate paymentDate = LocalDate.now();
    
    @JoinColumn(name = "invoice", referencedColumnName = "id")
    @ManyToOne
    private Invoice invoice;
    
    @Column(name = "received_amount")
    private double receivedAmount;
    
    @Column(name = "amount_unpaid")
    private double amountUnpaid;
    
    @Column(name = "payment_method")
    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;
 
    @Column(name = "payment_status")
    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;

    @Column(name = "description")
    @Lob
    private String description;

    public String getReceiptNumber()
    {
        return receiptNumber;
    }

    public void setReceiptNumber(String receiptNumber)
    {
        this.receiptNumber = receiptNumber;
    }

    public Client getReceivedFrom()
    {
        return receivedFrom;
    }

    public void setReceivedFrom(Client receivedFrom)
    {
        this.receivedFrom = receivedFrom;
    }
    
    public LocalDate getPaymentDate()
    {
        return paymentDate;
    }

    public void setPaymentDate(LocalDate paymentDate)
    {
        this.paymentDate = paymentDate;
    }

    public Invoice getInvoice()
    {
        return invoice;
    }

    public void setInvoice(Invoice invoice)
    {
        this.invoice = invoice;
    }

    public double getReceivedAmount()
    {
        return receivedAmount;
    }

    public void setReceivedAmount(double receivedAmount)
    {
        this.receivedAmount = receivedAmount;
    }

    public double getAmountUnpaid()
    {
        return amountUnpaid;
    }

    public void setAmountUnpaid(double amountUnpaid)
    {
        this.amountUnpaid = amountUnpaid;
    }

    public PaymentMethod getPaymentMethod()
    {
        return paymentMethod;
    }

    public void setPaymentMethod(PaymentMethod paymentMethod)
    {
        this.paymentMethod = paymentMethod;
    }

    public PaymentStatus getPaymentStatus()
    {
        return paymentStatus;
    }

    public void setPaymentStatus(PaymentStatus paymentStatus)
    {
        this.paymentStatus = paymentStatus;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public String getPaymentCode()
    {
        return paymentCode;
    }

    public void setPaymentCode(String paymentCode)
    {
        this.paymentCode = paymentCode;
    }

    public void genCode()
    {
        if (getReceiptNumber() != null || getPaymentCode() != null)
        {
            setReceiptNumber(getReceiptNumber());
            setPaymentCode(getPaymentCode());
        } else
        {
            setReceiptNumber(SystemUtils.generateRefNo());
            setPaymentCode(SystemUtils.generateCode());
        }
    }
}
