/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.khoders.invoicemaster.entites;

import com.khoders.resource.enums.PaymentMethod;
import com.khoders.resource.enums.PaymentStatus;
import com.khoders.resource.jpa.BaseModel;
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
@Table(name = "payment")
public class Payment extends BaseModel implements Serializable
{
    @Column(name = "payment_code")
    private String paymentCode;
    
    @Column(name = "payment_date")
    private LocalDate paymentDate = LocalDate.now();
    
    @JoinColumn(name = "invoice", referencedColumnName = "id")
    @ManyToOne
    private Invoice invoice;
    
    @Column(name = "amount_paid")
    private double amountPaid;
    
    @Column(name = "payment_method")
    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;
 
    @Column(name = "payment_status")
    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;

    @Column(name = "description")
    @Lob
    private String description;

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }
    
    
    public Invoice getInvoice()
    {
        return invoice;
    }

    public void setInvoice(Invoice invoice)
    {
        this.invoice = invoice;
    }

    public double getAmountPaid()
    {
        return amountPaid;
    }

    public void setAmountPaid(double amountPaid)
    {
        this.amountPaid = amountPaid;
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
    
    public String getPaymentCode()
    {
        return paymentCode;
    }

    public void setPaymentCode(String paymentCode)
    {
        this.paymentCode = paymentCode;
    }

    public LocalDate getPaymentDate()
    {
        return paymentDate;
    }

    public void setPaymentDate(LocalDate paymentDate)
    {
        this.paymentDate = paymentDate;
    }
    
    
    public void genCode()
    {
        if (getPaymentCode() != null)
        {
            setPaymentCode(getPaymentCode());
        } else
        {
            setPaymentCode(SystemUtils.generateRefNo());
        }
    }

}
