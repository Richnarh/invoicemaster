/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.khoders.im.admin.services;

import com.khoders.im.admin.listener.AppSession;
import com.khoders.invoicemaster.entities.Inventory;
import com.khoders.invoicemaster.entities.ProformaInvoice;
import com.khoders.invoicemaster.entities.ProformaInvoiceItem;
import com.khoders.invoicemaster.entities.UserAccount;
import com.khoders.invoicemaster.entities.system.CompanyBranch;
import com.khoders.resource.jpa.CrudApi;
import com.khoders.resource.utilities.DateRangeUtil;
import java.util.Collections;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.TypedQuery;

/**
 *
 * @author richa
 */
@Stateless
public class InvoiceService
{
    @Inject private CrudApi crudApi;
    
    public List<ProformaInvoice> getInvoiceList(CompanyBranch companyBranch, UserAccount userAccount)
    {
        try
        {
            if(companyBranch != null || userAccount != null)
            {
                String qryString = "SELECT e FROM ProformaInvoice e WHERE e.companyBranch=?1 AND e.userAccount=?2";
                TypedQuery<ProformaInvoice> typedQuery = crudApi.getEm().createQuery(qryString, ProformaInvoice.class)
                    .setParameter(1, companyBranch)
                    .setParameter(2, userAccount);
                return typedQuery.getResultList();  
            }
            
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        
        return Collections.emptyList();
    }
    
     
    public List<ProformaInvoice> getInvoiceByDates(DateRangeUtil dateRange)
    {
        try {
            if(dateRange.getFromDate() == null || dateRange.getToDate() == null)
            {
                String  queryString = "SELECT e FROM ProformaInvoice e ";
                return crudApi.getEm().createQuery(queryString, ProformaInvoice.class).getResultList();
            }
            
            String qryString = "SELECT e FROM ProformaInvoice e WHERE e.valueDate BETWEEN ?1 AND ?2";
            
            TypedQuery<ProformaInvoice> typedQuery = crudApi.getEm().createQuery(qryString, ProformaInvoice.class)
                    .setParameter(1, dateRange.getFromDate())
                    .setParameter(2, dateRange.getToDate());
           return typedQuery.getResultList();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }
     
    public List<ProformaInvoice> getInvoiceByBranch(CompanyBranch companyBranch)
    {
        try {
           
            String qryString = "SELECT e FROM ProformaInvoice e WHERE e.companyBranch=?1";
            
            TypedQuery<ProformaInvoice> typedQuery = crudApi.getEm().createQuery(qryString, ProformaInvoice.class)
                    .setParameter(1, companyBranch);
           return typedQuery.getResultList();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }
  
    public List<ProformaInvoice> getInvoiceByEmployee(UserAccount userAccount)
    {
        try 
        {
         
        String qryString = "SELECT e FROM ProformaInvoice e WHERE e.userAccount=?1";
            
        TypedQuery<ProformaInvoice> typedQuery = crudApi.getEm().createQuery(qryString, ProformaInvoice.class)
                    .setParameter(1, userAccount);
        return typedQuery.getResultList();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }

    
    public List<ProformaInvoiceItem> getInvoiceDetailList(ProformaInvoice proformaInvoice)
    {
        try
        {
           String qryString = "SELECT e FROM ProformaInvoiceItem e WHERE e.proformaInvoice=?1";
           return crudApi.getEm().createQuery(qryString, ProformaInvoiceItem.class)
                   .setParameter(1, proformaInvoice)
                   .getResultList();
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        
        return Collections.emptyList();
    }
    
    public List<Inventory> getStockList(CompanyBranch companyBranch)
    {
        try
        {
          String qryString = "SELECT e FROM Inventory e WHERE e.companyBranch=?1 ORDER BY e.product ASC";
          return crudApi.getEm().createQuery(qryString, Inventory.class)
                  .setParameter(1, companyBranch)
                  .getResultList();
           
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        
        return Collections.emptyList();
    }
    
    public List<Inventory> getStockShortageList(CompanyBranch companyBranch)
    {
        try
        {
          String qryString = "SELECT e FROM Inventory e WHERE e.companyBranch=?1 AND e.quantity <= 1 ORDER BY e.product ASC";
          return crudApi.getEm().createQuery(qryString, Inventory.class)
                  .setParameter(1, companyBranch)
                  .getResultList();
           
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        
        return Collections.emptyList();
    }
    
      
    public List<ProformaInvoice> getProformaInvoice(DateRangeUtil dateRange)
    {
        try {
            if(dateRange.getFromDate() == null || dateRange.getToDate() == null)
            {
                  String  queryString = "SELECT e FROM ProformaInvoice e ORDER BY e.issuedDate DESC";
                  TypedQuery<ProformaInvoice> typedQuery = crudApi.getEm().createQuery(queryString, ProformaInvoice.class);
                                    return typedQuery.getResultList();
            }
            
            String qryString = "SELECT e FROM ProformaInvoice e WHERE e.valueDate BETWEEN ?1 AND ?2 ORDER BY e.issuedDate DESC";
            
            TypedQuery<ProformaInvoice> typedQuery = crudApi.getEm().createQuery(qryString, ProformaInvoice.class)
                    .setParameter(1, dateRange.getFromDate())
                    .setParameter(2, dateRange.getToDate());
           return typedQuery.getResultList();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }

}
