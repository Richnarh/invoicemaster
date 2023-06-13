/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.khoders.im.admin.jbeans;

import com.khoders.im.admin.services.InvoiceService;
import com.khoders.invoicemaster.entities.ProformaInvoice;
import com.khoders.resource.jpa.CrudApi;
import com.khoders.resource.utilities.DateRangeUtil;
import com.khoders.resource.utilities.Msg;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author Khoders
 */
@Named(value = "manageInvoiceController")
@SessionScoped
public class ManageInvoiceController implements Serializable
{
    @Inject private CrudApi crudApi;
    @Inject private InvoiceService invoiceService;
    
    private ProformaInvoice proformaInvoice = new ProformaInvoice();
    private DateRangeUtil dateRange = new DateRangeUtil();
    private String invoiceId;
    
    private List<ProformaInvoice> proformaInvoiceList = new LinkedList<>();
    
    public void filterProformaInvoice()
    {
      proformaInvoiceList = invoiceService.getProformaInvoice(dateRange);   
    }
    
    public void reverseInvoice(ProformaInvoice proformaInvoice){
        boolean paymentData = invoiceService.deletePaymentData(proformaInvoice);
        boolean taxInfo = invoiceService.deleteSalesTax(proformaInvoice);
        boolean saleItem = invoiceService.deleteSaleItem(proformaInvoice);
        
        if(paymentData){
            System.out.println("Payment data deleted");
            Msg.info("Payment data and delivery info cleared!");
        }
        if(taxInfo){
            System.out.println("SalesTax data deleted");
            Msg.info("Tax info cleared");
        }
        if(saleItem){
            System.out.println("Invoice item info cleared and inventory quantity updated!");
            Msg.info("Invoice item info cleared and inventory quantity updated!");
        }
    }
    
    public void searchInvoice(){
        proformaInvoiceList = invoiceService.getInvoice(invoiceId); 
    }
    
    public void clearSearch(){
        proformaInvoiceList = new LinkedList<>();
        invoiceId = null;
    }
    
    public void reset()
    {
      proformaInvoiceList = new LinkedList<>();
    }

    public List<ProformaInvoice> getProformaInvoiceList()
    {
        return proformaInvoiceList;
    }

    public DateRangeUtil getDateRange()
    {
        return dateRange;
    }

    public void setDateRange(DateRangeUtil dateRange)
    {
        this.dateRange = dateRange;
    }

    public String getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(String invoiceId) {
        this.invoiceId = invoiceId;
    }
    
}
