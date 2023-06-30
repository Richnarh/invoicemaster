/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.khoders.invoicemaster.entities;

import com.khoders.invoicemaster.enums.DeliveryStatus;
import com.khoders.resource.enums.PaymentMethod;
import com.khoders.resource.enums.PaymentStatus;
import com.khoders.resource.utilities.SystemUtils;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author richa
 */
@Entity
@Table(name = "payment_data")
public class PaymentData extends UserAccountRecord implements Serializable
{
   @Column(name = "payment_code")
   private String paymentCode;
   
   public static final String _proformaInvoice = "proformaInvoice";
   @JoinColumn(name = "proforma_invoice", referencedColumnName = "id")
   @ManyToOne
   private ProformaInvoice proformaInvoice;
   
   @Column(name = "payment_method")
   @Enumerated(EnumType.STRING)
   private PaymentMethod paymentMethod;
   
   public static final String _deliveryStatus = "deliveryStatus";
   @Column(name = "delivery_status")
   @Enumerated(EnumType.STRING)
   private DeliveryStatus deliveryStatus;
   
   @Column(name = "payment_status")
   @Enumerated(EnumType.STRING)
   private PaymentStatus paymentStatus;
   
   @Column(name = "partial_amount_paid")
   private double partialAmountPaid;
   
   @Column(name = "payment_message")
   private boolean paymentMessage=false;

    public String getPaymentCode()
    {
        return paymentCode;
    }

    public void setPaymentCode(String paymentCode)
    {
        this.paymentCode = paymentCode;
    }

    public ProformaInvoice getProformaInvoice()
    {
        return proformaInvoice;
    }

    public void setProformaInvoice(ProformaInvoice proformaInvoice)
    {
        this.proformaInvoice = proformaInvoice;
    }

    public PaymentMethod getPaymentMethod()
    {
        return paymentMethod;
    }

    public void setPaymentMethod(PaymentMethod paymentMethod)
    {
        this.paymentMethod = paymentMethod;
    }

    public DeliveryStatus getDeliveryStatus()
    {
        return deliveryStatus;
    }

    public void setDeliveryStatus(DeliveryStatus deliveryStatus)
    {
        this.deliveryStatus = deliveryStatus;
    }

    public PaymentStatus getPaymentStatus()
    {
        return paymentStatus;
    }

    public void setPaymentStatus(PaymentStatus paymentStatus)
    {
        this.paymentStatus = paymentStatus;
    }

    public double getPartialAmountPaid() {
        return partialAmountPaid;
    }

    public void setPartialAmountPaid(double partialAmountPaid) {
        this.partialAmountPaid = partialAmountPaid;
    }

    public boolean isPaymentMessage() {
        return paymentMessage;
    }

    public void setPaymentMessage(boolean paymentMessage) {
        this.paymentMessage = paymentMessage;
    }

    public void genCode()
    {
        if (getPaymentCode() != null)
        {
            setPaymentCode(getPaymentCode());
        } else
        {
            setPaymentCode(SystemUtils.generateCode());
        }
    }

}
