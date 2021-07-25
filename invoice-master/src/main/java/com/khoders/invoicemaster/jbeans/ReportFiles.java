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
    private static final String SUB_REPORTS_DIR = "/com/khoders/invoicemaster/resources/reports/subreports/";
    
    public static final String LOGO = LOGO_DIR+"logo.png";
    
    public static final String DELIVERY_TERM_FILE = SUB_REPORTS_DIR+"delivery_term.jasper";
    public static final String VALIDATION_FILE = SUB_REPORTS_DIR+"validation.jasper";
    public static final String COLOURS_FILE = SUB_REPORTS_DIR+"colours.jasper";
    public static final String RECEIVED_DOCUMENT_FILE = SUB_REPORTS_DIR+"received_document.jasper";
    
    public static final String INVOICE_ITEM_FILE = SUB_REPORTS_DIR+"invoice_item.jasper";
    public static final String PROFORMA_INVOICE_ITEM_FILE = SUB_REPORTS_DIR+"proforma_invoice_item.jasper";
    
    public static final String PAYMENT_RECEIPT_FILE = BASE_DIR+"payment_receipt.jasper";
    public static final String PROFORMA_INVOICE_FILE = BASE_DIR+"proforma_invoice.jasper";
    public static final String PRO_INVOICE_FILE = BASE_DIR+"pro_invoice.jasper";
    public static final String PRO_INVOICE_COVER = BASE_DIR+"pro_invoice_cover.jasper";
    public static final String INVOICE_FILE = BASE_DIR+"invoice.jasper";
    public static final String RECEIPT_FILE = BASE_DIR+"receipt.jasper";
    
}
