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
public class ReportData {
    private String invoiceCover;
    private String invoiceReport;

    public String getInvoiceCover() {
        return invoiceCover;
    }

    public void setInvoiceCover(String invoiceCover) {
        this.invoiceCover = invoiceCover;
    }

    public String getInvoiceReport() {
        return invoiceReport;
    }

    public void setInvoiceReport(String invoiceReport) {
        this.invoiceReport = invoiceReport;
    }
    
}
