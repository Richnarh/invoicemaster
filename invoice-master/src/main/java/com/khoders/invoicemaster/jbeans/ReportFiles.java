/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.khoders.invoicemaster.jbeans;

/**
 *
 * @author richa
 */
public class ReportFiles
{
    private static final String BASE_DIR = "/com/khoders/invoicemaster/resources/reports/";
    private static final String LOGO_DIR = "/com/khoders/invoicemaster/resources/images/";
    
    public static final String LOGO = LOGO_DIR+"logo.png";
    
    public static final String PAYMENT_RECEIPT_FILE = BASE_DIR+"payment_receipt.jasper";
    public static final String PROFORMA_INVOICE_FILE = BASE_DIR+"proforma_invoice.jasper";
    
}
