/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.khoders.invoicemaster.jbeans.controller;

import com.khoders.invoicemaster.DefaultService;
import com.khoders.invoicemaster.entities.ProformaInvoice;
import com.khoders.invoicemaster.jbeans.ReportFiles;
import com.khoders.invoicemaster.reportData.ProformaInvoiceDto;
import com.khoders.invoicemaster.service.XtractService;
import com.khoders.resource.reports.ReportManager;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import net.sf.jasperreports.engine.JasperPrint;

/**
 *
 * @author Pascal
 */
@Named(value="reportController")
@ViewScoped
public class ReportController implements Serializable{
    @Inject private ReportManager reportManager;
    @Inject private XtractService xtractService;
    @Inject private DefaultService ds;
    
    public void printInvoice(String id) {
        System.out.println("invoiceId => " + id);
        ProformaInvoice proformaInvoice = ds.getInvoice(id);
        generateProformaInvoice(proformaInvoice);
    }
    
    public void generateProformaInvoice(ProformaInvoice proformaInvoice) {
        List<JasperPrint> jasperPrintList = new LinkedList<>();
        List<ProformaInvoiceDto> proformaInvoiceDtoList = new LinkedList<>();
        List<ProformaInvoiceDto> coverDataList = new LinkedList<>();

        ReportManager.param.put("logo", ReportFiles.LOGO);

        ProformaInvoiceDto coverData = xtractService.extractToProformaInvoiceCover(proformaInvoice);
        coverDataList.add(coverData);
        JasperPrint coverPrint = reportManager.createJasperPrint(coverDataList, ReportFiles.PRO_INVOICE_COVER, ReportManager.param);
        jasperPrintList.add(coverPrint);

        ProformaInvoiceDto proformaInvoiceDto = xtractService.extractToProformaInvoice(proformaInvoice);
        proformaInvoiceDtoList.add(proformaInvoiceDto);
        JasperPrint invoicePrint = reportManager.createJasperPrint(proformaInvoiceDtoList, ReportFiles.PRO_INVOICE_FILE, ReportManager.param);
        jasperPrintList.add(invoicePrint);

        reportManager.generateReport(jasperPrintList, proformaInvoice.getQuotationNumber());

        System.out.println("Done!");
    }
}
