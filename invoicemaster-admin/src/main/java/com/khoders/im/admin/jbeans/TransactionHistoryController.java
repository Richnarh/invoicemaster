/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.khoders.im.admin.jbeans;

import com.khoders.im.admin.services.InvoiceService;
import com.khoders.invoicemaster.entities.ProformaInvoice;
import com.khoders.invoicemaster.entities.ProformaInvoiceItem;
import com.khoders.invoicemaster.entities.UserAccount;
import com.khoders.invoicemaster.entities.system.CompanyBranch;
import com.khoders.resource.jpa.CrudApi;
import com.khoders.resource.utilities.DateRangeUtil;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author richa
 */
@Named(value = "transactionHistoryController")
@SessionScoped
public class TransactionHistoryController implements Serializable
{
    @Inject private CrudApi crudApi;
    @Inject private InvoiceService invoiceService;
    
    private ProformaInvoice selectedInvoice = new ProformaInvoice();
    private UserAccount selectedUserAccount = new UserAccount();
    private ProformaInvoiceItem invoiceItem = new ProformaInvoiceItem();
    private List<ProformaInvoice> invoiceList = new LinkedList<>();
    private List<ProformaInvoiceItem> invoiceItemList = new LinkedList<>();
    
    private DateRangeUtil dateRange = new DateRangeUtil();
    private CompanyBranch selectedBranch = new CompanyBranch();
    private String clientName;
    private String phoneNumber;
    
    private LocalDate valueDate;
        
    public void fetchByParams()
    {
      invoiceList = invoiceService.getInvoiceList(selectedBranch, selectedUserAccount);
    }
    
    public void selectSale(ProformaInvoice proformaInvoice)
    {
      clientName = proformaInvoice.getClient().getClientName();
      phoneNumber = proformaInvoice.getClient().getPhone();
      valueDate = proformaInvoice.getValueDate();
      
      invoiceItemList = invoiceService.getInvoiceDetailList(proformaInvoice);
    }
    
    public void fetchByDates()
    {
       invoiceList = invoiceService.getInvoiceByDates(dateRange);
    }
    
    public void fetchByBranch()
    {
        invoiceList = invoiceService.getInvoiceByBranch(selectedBranch);
    }
    
    public void fetchByEmployee()
    {
        invoiceList = invoiceService.getInvoiceByEmployee(selectedUserAccount);
    }
    
    public void resetPage()
    {
      selectedBranch = new CompanyBranch();
      selectedUserAccount = new UserAccount();
      
      clientName = null;
      phoneNumber = null;
      valueDate = null;
      
      invoiceList = new LinkedList<>();
      invoiceItemList = new LinkedList<>();
    }

    public ProformaInvoice getSelectedInvoice()
    {
        return selectedInvoice;
    }

    public void setSelectedInvoice(ProformaInvoice selectedInvoice)
    {
        this.selectedInvoice = selectedInvoice;
    }

    public DateRangeUtil getDateRange()
    {
        return dateRange;
    }

    public void setDateRange(DateRangeUtil dateRange)
    {
        this.dateRange = dateRange;
    }

    public CompanyBranch getSelectedBranch()
    {
        return selectedBranch;
    }

    public void setSelectedBranch(CompanyBranch selectedBranch)
    {
        this.selectedBranch = selectedBranch;
    }

    public UserAccount getSelectedUserAccount()
    {
        return selectedUserAccount;
    }

    public void setSelectedUserAccount(UserAccount selectedUserAccount)
    {
        this.selectedUserAccount = selectedUserAccount;
    } 

    public List<ProformaInvoice> getInvoiceList()
    {
        return invoiceList;
    }

    public List<ProformaInvoiceItem> getInvoiceItemList()
    {
        return invoiceItemList;
    }

    public String getClientName()
    {
        return clientName;
    }

    public String getPhoneNumber()
    {
        return phoneNumber;
    }

    public LocalDate getValueDate()
    {
        return valueDate;
    }
    
}
