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
public class ReverseData {

    private String paymentData;
    private String taxInfo;
    private String saleItem;

    public ReverseData() {
    }

    public ReverseData(String paymentData, String taxInfo, String saleItem) {
        this.paymentData = paymentData;
        this.taxInfo = taxInfo;
        this.saleItem = saleItem;
    }

    public String getPaymentData() {
        return paymentData;
    }

    public void setPaymentData(String paymentData) {
        this.paymentData = paymentData;
    }

    public String getTaxInfo() {
        return taxInfo;
    }

    public void setTaxInfo(String taxInfo) {
        this.taxInfo = taxInfo;
    }

    public String getSaleItem() {
        return saleItem;
    }

    public void setSaleItem(String saleItem) {
        this.saleItem = saleItem;
    }

}
