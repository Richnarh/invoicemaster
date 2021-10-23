/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.khoders.im.admin.jbeans;

import com.khoders.im.admin.services.InvoiceService;
import com.khoders.invoicemaster.entities.ProformaInvoice;
import com.khoders.resource.jpa.CrudApi;
import com.khoders.resource.utilities.DateRangeUtil;
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
    
    private List<ProformaInvoice> proformaInvoiceList = new LinkedList<>();
    
    public void filterProformaInvoice()
    {
      proformaInvoiceList = invoiceService.getProformaInvoice(dateRange);   
    }
    
    public void deleteInvoice(ProformaInvoice proformaInvoice){
        try
        {

        } catch (Exception e)
        {
            e.printStackTrace();
        }
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
    
}
