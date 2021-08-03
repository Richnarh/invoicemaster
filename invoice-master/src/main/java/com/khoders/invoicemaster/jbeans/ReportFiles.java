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
    public static final String PRO_INVOICE_FILE = BASE_DIR+"pro_invoice.jasper";
    public static final String PRO_INVOICE_COVER = BASE_DIR+"pro_invoice_cover.jasper";
    public static final String INVOICE_FILE = BASE_DIR+"invoice.jasper";
    public static final String RECEIPT_FILE = BASE_DIR+"receipt.jasper";
    
}
