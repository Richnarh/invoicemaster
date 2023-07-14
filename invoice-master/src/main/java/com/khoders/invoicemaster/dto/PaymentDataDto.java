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
public class PaymentDataDto extends UserProp{
   private String paymentCode;
   private String clientName;
   private String clientId;
   private String proformaInvoice;
   private String proformaInvoiceId;
   private String paymentMethod;
   private String deliveryStatus;
   private String paymentStatus;
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

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getDeliveryStatus() {
        return deliveryStatus;
    }

    public void setDeliveryStatus(String deliveryStatus) {
        this.deliveryStatus = deliveryStatus;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
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
