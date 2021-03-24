/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.khoders.invoicemaster.service;

import com.khoders.invoicemaster.entites.Colours;
import com.khoders.invoicemaster.entites.DeliveryTerm;
import com.khoders.invoicemaster.entites.Inventory;
import com.khoders.invoicemaster.entites.Invoice;
import com.khoders.invoicemaster.entites.InvoiceConfigItems;
import com.khoders.invoicemaster.entites.InvoiceItem;
import com.khoders.invoicemaster.entites.Validation;
import com.khoders.invoicemaster.entites.enums.InvoiceType;
import com.khoders.resource.jpa.CrudApi;
import com.khoders.resource.utilities.DateRangeUtil;
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
public class InvoiceService
{

    private @Inject
    CrudApi crudApi;

    public List<InvoiceItem> getInvoiceItemList(Invoice invoice)
    {
        try
        {
          String query = "SELECT e FROM InvoiceItem e WHERE e.invoice=?1";
        
        TypedQuery<InvoiceItem> typedQuery = crudApi.getEm().createQuery(query, InvoiceItem.class)
                                .setParameter(1, invoice);
                return typedQuery.getResultList();      
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }

    public List<InvoiceConfigItems> getInvoiceConfigItemsList(Invoice invoice)
    {
        try
        {
            String qryString = "SELECT e FROM InvoiceConfigItems e WHERE e.invoice = ?1";
            TypedQuery<InvoiceConfigItems> typedQuery = crudApi.getEm().createQuery(qryString, InvoiceConfigItems.class);
                                            typedQuery.setParameter(1, invoice);
                                     return typedQuery.getResultList();

        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }

    public List<Invoice> getInvoiceList()
    {
        try
        {
            String qryString = "SELECT e FROM Invoice e WHERE e.invoiceType=?1";
            TypedQuery<Invoice> typedQuery = crudApi.getEm().createQuery(qryString, Invoice.class);
                                typedQuery.setParameter(1, InvoiceType.PROFORMA_INVOICE);
                         return typedQuery.getResultList();

        } catch (Exception e)
        {
            e.printStackTrace();
        }

        return Collections.emptyList();
    }
    
    public List<Invoice> getProformaInvoice(DateRangeUtil dateRange, Invoice invoice)
    {
        try {
            if(dateRange.getFromDate() == null || dateRange.getToDate() == null)
            {
                  String  queryString = "SELECT e FROM Invoice e WHERE e.invoiceType=?1";
                  TypedQuery<Invoice> typedQuery = crudApi.getEm().createQuery(queryString, Invoice.class)
                                .setParameter(1, InvoiceType.PROFORMA_INVOICE);

                return typedQuery.getResultList();
            }
            
            String qryString = "SELECT e FROM Invoice e WHERE e.issuedDate BETWEEN ?1 AND ?2 AND e.invoiceType=?3";
            
            TypedQuery<Invoice> typedQuery = crudApi.getEm().createQuery(qryString, Invoice.class)
                    .setParameter(1, dateRange.getFromDate())
                    .setParameter(2, dateRange.getToDate())
                    .setParameter(3, InvoiceType.PROFORMA_INVOICE);
            
           return typedQuery.getResultList();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }
    
    
    public List<Invoice> getStandardInvoice(DateRangeUtil dateRange, Invoice invoice)
    {
        try {
            if(dateRange.getFromDate() == null || dateRange.getToDate() == null)
            {
                  String  queryString = "SELECT e FROM Invoice e WHERE e.invoiceType=?1";
                  TypedQuery<Invoice> typedQuery = crudApi.getEm().createQuery(queryString, Invoice.class)
                                .setParameter(1, InvoiceType.STANDARD_INVOICE);

                return typedQuery.getResultList();
            }
            
            String qryString = "SELECT e FROM Invoice e WHERE e.issuedDate BETWEEN ?1 AND ?2 AND e.invoiceType=?3";
            
            TypedQuery<Invoice> typedQuery = crudApi.getEm().createQuery(qryString, Invoice.class)
                    .setParameter(1, dateRange.getFromDate())
                    .setParameter(2, dateRange.getToDate())
                    .setParameter(3, InvoiceType.STANDARD_INVOICE);
            
           return typedQuery.getResultList();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }
    

    public List<DeliveryTerm> getDeliveryTermList()
    {
        try
        {
            String qryString = "SELECT e FROM DeliveryTerm e";
            TypedQuery<DeliveryTerm> typedQuery = crudApi.getEm().createQuery(qryString, DeliveryTerm.class);
            return typedQuery.getResultList();

        } catch (Exception e)
        {
            e.printStackTrace();
        }

        return Collections.emptyList();
    }

    public List<Validation> getValidationList()
    {
        try
        {
            String qryString = "SELECT e FROM Validation e";
            TypedQuery<Validation> typedQuery = crudApi.getEm().createQuery(qryString, Validation.class);
            return typedQuery.getResultList();

        } catch (Exception e)
        {
            e.printStackTrace();
        }

        return Collections.emptyList();
    }

    public List<Colours> getColoursList()
    {
        try
        {
            String qryString = "SELECT e FROM Colours e";
            TypedQuery<Colours> typedQuery = crudApi.getEm().createQuery(qryString, Colours.class);
            return typedQuery.getResultList();

        } catch (Exception e)
        {
            e.printStackTrace();
        }

        return Collections.emptyList();
    }

    public List<Inventory> getInventoryList()
    {
        try
        {
            String qryString = "SELECT e FROM Inventory e";
            TypedQuery<Inventory> typedQuery = crudApi.getEm().createQuery(qryString, Inventory.class);
            return typedQuery.getResultList();

        } catch (Exception e)
        {
            e.printStackTrace();
        }

        return Collections.emptyList();
    }

    
    
    public Invoice extractFromProformerInvoice(Invoice proformaInvoice)
    {
        Invoice standardInvoice = new Invoice();
        standardInvoice.setIssuedDate(proformaInvoice.getIssuedDate());
        standardInvoice.setDueDate(proformaInvoice.getDueDate());
        standardInvoice.setClient(proformaInvoice.getClient());
        standardInvoice.setInvoiceNumber(proformaInvoice.getQuotationNumber());
        standardInvoice.setInvoiceType(InvoiceType.STANDARD_INVOICE);
        standardInvoice.setProject(proformaInvoice.getProject());
        standardInvoice.setSubject(proformaInvoice.getSubject());
        standardInvoice.setDescription(proformaInvoice.getDescription());
        standardInvoice.setTotalAmount(proformaInvoice.getTotalAmount());
        
        if(crudApi.save(standardInvoice) != null)
        {
            List<InvoiceItem> invoiceItemList = getInvoiceItemList(proformaInvoice);
            
            for (InvoiceItem item : invoiceItemList)
            {
                InvoiceItem invoiceItem = new InvoiceItem();
                invoiceItem.setInvoice(standardInvoice);
                invoiceItem.setItemCode(item.getItemCode());
                invoiceItem.setInventoryProduct(item.getInventoryProduct());
                invoiceItem.setUnitPrice(item.getUnitPrice());
                invoiceItem.setQuantity(item.getQuantity());
                invoiceItem.setCharges(item.getCharges());
                invoiceItem.setTotalAmount(item.getTotalAmount());
                
                invoiceItemList.add(invoiceItem);
                
                crudApi.save(invoiceItem);
            }
        }
        
        return standardInvoice;
    }
}
