/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.khoders.invoicemaster.service;

import com.khoders.invoicemaster.entities.Inventory;
import com.khoders.invoicemaster.entities.OnlineClient;
import com.khoders.invoicemaster.entities.PaymentData;
import com.khoders.invoicemaster.entities.ProformaInvoice;
import com.khoders.invoicemaster.entities.ProformaInvoiceItem;
import com.khoders.invoicemaster.entities.SalesTax;
import com.khoders.invoicemaster.entities.Tax;
import com.khoders.invoicemaster.entities.UserTransaction;
import com.khoders.invoicemaster.entities.system.CompanyBranch;
import com.khoders.invoicemaster.enums.InvoiceStatus;
import com.khoders.invoicemaster.listener.AppSession;
import com.khoders.resource.jpa.CrudApi;
import com.khoders.resource.utilities.DateRangeUtil;
import com.khoders.resource.utilities.DateUtil;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.TypedQuery;

/**
 *
 * @author pascal
 */
@Stateless
public class ProformaInvoiceService
{

    private @Inject AppSession appSession;
    private @Inject CrudApi crudApi;

    public List<ProformaInvoiceItem> getProformaInvoiceItemList(ProformaInvoice proformaInvoice)
    {
        try
        {
          String query = "SELECT e FROM ProformaInvoiceItem e WHERE e.proformaInvoice=?1 AND e.userAccount=?2";
        
        TypedQuery<ProformaInvoiceItem> typedQuery = crudApi.getEm().createQuery(query, ProformaInvoiceItem.class)
                                .setParameter(1, proformaInvoice)
                                .setParameter(2, appSession.getCurrentUser());
                return typedQuery.getResultList();      
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }
    public List<SalesTax> getSalesTaxList(ProformaInvoice proformaInvoice)
    {
        try
        {
          String query = "SELECT e FROM SalesTax e WHERE e.proformaInvoice=?1 AND e.userAccount=?2 ORDER BY e.reOrder ASC";
        
        TypedQuery<SalesTax> typedQuery = crudApi.getEm().createQuery(query, SalesTax.class)
                                .setParameter(1, proformaInvoice)
                                .setParameter(2, appSession.getCurrentUser());
                return typedQuery.getResultList();      
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }
    
    public List<SalesTax> getSalesTaxList(UserTransaction userTransaction)
    {
        try
        {
          String query = "SELECT e FROM SalesTax e WHERE e.userTransaction=?1 ORDER BY e.reOrder ASC";
        
        TypedQuery<SalesTax> typedQuery = crudApi.getEm().createQuery(query, SalesTax.class)
                                .setParameter(1, userTransaction);
                return typedQuery.getResultList();      
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }
   
    public List<ProformaInvoice> getProformaInvoiceList()
    {
        try
        {
            String qryString = "SELECT e FROM ProformaInvoice e WHERE e.userAccount=?1 AND e.valueDate=?2 ORDER BY e.issuedDate DESC";
            TypedQuery<ProformaInvoice> typedQuery = crudApi.getEm().createQuery(qryString, ProformaInvoice.class)
                                        .setParameter(1, appSession.getCurrentUser())
                                        .setParameter(2, LocalDate.now());
                         return typedQuery.getResultList();
                         
        } catch (Exception e)
        {
            e.printStackTrace();
        }

        return Collections.emptyList();
    }
    
    public List<ProformaInvoice> getProformaInvoice(DateRangeUtil dateRange, ProformaInvoice proformaInvoice)
    {
        try 
        {
            if(dateRange.getFromDate() == null || dateRange.getToDate() == null)
            {
                  String  queryString = "SELECT e FROM ProformaInvoice e WHERE e.userAccount=?1 ORDER BY e.issuedDate DESC";
                  TypedQuery<ProformaInvoice> typedQuery = crudApi.getEm().createQuery(queryString, ProformaInvoice.class)
                                              .setParameter(1, appSession.getCurrentUser());
                                    return typedQuery.getResultList();
            }
            
            String qryString = "SELECT e FROM ProformaInvoice e WHERE e.valueDate BETWEEN ?1 AND ?2 AND e.userAccount=?3 ORDER BY e.issuedDate DESC";
            TypedQuery<ProformaInvoice> typedQuery = crudApi.getEm().createQuery(qryString, ProformaInvoice.class)
                    .setParameter(1, dateRange.getFromDate())
                    .setParameter(2, dateRange.getToDate())
                    .setParameter(3, appSession.getCurrentUser());
           return typedQuery.getResultList();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }
    
    public List<ProformaInvoice> getProformaInvoice(InvoiceStatus invoiceStatus)
    {
        try {
            
            String qryString = "";
            TypedQuery<ProformaInvoice> typedQuery = null;
            if(InvoiceStatus.OVERDUE_INVOICE == invoiceStatus)
            {
                qryString = "SELECT e FROM ProformaInvoice e WHERE e.expiryDate <= ?1 AND e.userAccount=?2 ORDER BY e.issuedDate DESC";   
            }
            else if(InvoiceStatus.VALID_INVOICE == invoiceStatus)
            {
                qryString = "SELECT e FROM ProformaInvoice e WHERE e.expiryDate >= ?1 AND e.userAccount=?2 ORDER BY e.issuedDate DESC";
            }
            
             typedQuery = crudApi.getEm().createQuery(qryString, ProformaInvoice.class)
              .setParameter(1, DateUtil._7DaysBeforeToday())
              .setParameter(2, appSession.getCurrentUser());
            
           return typedQuery.getResultList();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }

    public List<Inventory> getInventoryList()
    {
        try
        {
            String qryString = "SELECT e FROM Inventory e WHERE e.companyBranch=?1";
            TypedQuery<Inventory> typedQuery = crudApi.getEm().createQuery(qryString, Inventory.class)
                                    .setParameter(1, appSession.getCompanyBranch());
            return typedQuery.getResultList();

        } catch (Exception e)
        {
            e.printStackTrace();
        }

        return Collections.emptyList();
    }

    public List<ProformaInvoiceItem> getProformaInvoiceItemReceipt(ProformaInvoice proformaInvoice)
    {
        try
        {
            String qryString = "SELECT e FROM ProformaInvoiceItem e WHERE e.proformaInvoice=?1 AND e.userAccount=?2";
            TypedQuery<ProformaInvoiceItem> typedQuery = crudApi.getEm().createQuery(qryString, ProformaInvoiceItem.class)
                    .setParameter(1, proformaInvoice)
                    .setParameter(2, appSession.getCurrentUser());
            return typedQuery.getResultList();
            
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        
        return Collections.emptyList();
    }
    
    public List<Tax> getTaxList()
    {
        try
        {
            return crudApi.getEm().createQuery("SELECT e FROM Tax e ORDER BY e.reOrder ASC", Tax.class).getResultList();
        } catch (Exception e)
        {
            e.printStackTrace();
        }

        return Collections.emptyList();
    }
    
    public List<PaymentData> getPaymentInfoList(ProformaInvoice proformaInvoice)
    {
        try
        {
            String qryString = "SELECT e FROM PaymentData e WHERE e.proformaInvoice=?1";
            return crudApi.getEm().createQuery(qryString, PaymentData.class)
                    .setParameter(1, proformaInvoice).getResultList();
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }
    
    public PaymentData invoiceRecord(ProformaInvoice proformaInvoice)
    {
        try
        {
          String qryString = "SELECT e FROM PaymentData e WHERE e.proformaInvoice=?1";
            return crudApi.getEm().createQuery(qryString, PaymentData.class)
                    .setParameter(1, proformaInvoice).getResultStream().findFirst().orElse(null);
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }
        
    public List<Inventory> getStockShortageList()
    {
        try
        {
          String qryString = "SELECT e FROM Inventory e WHERE e.quantity <= 5 ORDER BY e.product ASC";
          return crudApi.getEm().createQuery(qryString, Inventory.class)
//                  .setParameter(1, companyBranch)
                  .getResultList();
           
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        
        return Collections.emptyList();
    }
}
