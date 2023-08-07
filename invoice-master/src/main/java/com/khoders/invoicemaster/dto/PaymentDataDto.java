/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.khoders.invoicemaster.dto;

import com.khoders.invoicemaster.enums.DeliveryStatus;
import com.khoders.resource.enums.PaymentMethod;
import com.khoders.resource.enums.PaymentStatus;

/**
 *
 * @author Pascal
 */
public class PaymentDataDto extends UserProp{
   private String paymentCode;
   private String clientName;
   private String clientId;
   private String proformaInvoice;
   private String proformaInvoiceId;
   private PaymentMethod paymentMethod;
   private DeliveryStatus deliveryStatus;
   private PaymentStatus paymentStatus;
   private String deliveryAdress;
   private double partialAmountPaid;
   private double totalAmount;
   private boolean paymentMessage=false;

    public String getPaymentCode() {
        return paymentCode;
    }

    public void setPaymentCode(String paymentCode) {
        this.paymentCode = paymentCode;
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

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public DeliveryStatus getDeliveryStatus() {
        return deliveryStatus;
    }

    public void setDeliveryStatus(DeliveryStatus deliveryStatus) {
        this.deliveryStatus = deliveryStatus;
    }

    public PaymentStatus getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(PaymentStatus paymentStatus) {
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

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getDeliveryAdress() {
        return deliveryAdress;
    }

    public void setDeliveryAdress(String deliveryAdress) {
        this.deliveryAdress = deliveryAdress;
    }
   
}
