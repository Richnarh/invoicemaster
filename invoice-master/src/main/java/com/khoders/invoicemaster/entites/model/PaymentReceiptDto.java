/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.khoders.invoicemaster.entites.model;

import java.time.LocalDate;

/**
 *
 * @author richa
 */
public class PaymentReceiptDto
{
    private String receiptNo;
    private LocalDate paymentDate;
    private String recievedFrom;
    private String amountInWords;
    private String paymentMethod;
    private double receivedAmount;
    private double dueAmount;
    private double unpaidAmount;
    private String description;
    private String receivedBy;

    public String getReceiptNo()
    {
        return receiptNo;
    }

    public void setReceiptNo(String receiptNo)
    {
        this.receiptNo = receiptNo;
    }

    public String getReceivedBy()
    {
        return receivedBy;
    }

    public void setReceivedBy(String receivedBy)
    {
        this.receivedBy = receivedBy;
    }

    public LocalDate getPaymentDate()
    {
        return paymentDate;
    }

    public void setPaymentDate(LocalDate paymentDate)
    {
        this.paymentDate = paymentDate;
    }

    public String getRecievedFrom()
    {
        return recievedFrom;
    }

    public void setRecievedFrom(String recievedFrom)
    {
        this.recievedFrom = recievedFrom;
    }

    public String getAmountInWords()
    {
        return amountInWords;
    }

    public void setAmountInWords(String amountInWords)
    {
        this.amountInWords = amountInWords;
    }

    public String getPaymentMethod()
    {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod)
    {
        this.paymentMethod = paymentMethod;
    }

    public double getReceivedAmount()
    {
        return receivedAmount;
    }

    public void setReceivedAmount(double receivedAmount)
    {
        this.receivedAmount = receivedAmount;
    }

    public double getDueAmount()
    {
        return dueAmount;
    }

    public void setDueAmount(double dueAmount)
    {
        this.dueAmount = dueAmount;
    }

    public double getUnpaidAmount()
    {
        return unpaidAmount;
    }

    public void setUnpaidAmount(double unpaidAmount)
    {
        this.unpaidAmount = unpaidAmount;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }
    
    
}
