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
import com.khoders.invoicemaster.entites.ProformaInvoice;
import com.khoders.invoicemaster.entites.ProformaInvoiceItem;
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
public class ProformaInvoiceService
{

    private @Inject
    CrudApi crudApi;

    public List<ProformaInvoiceItem> getProformaInvoiceItemList(ProformaInvoice proformaInvoice)
    {
        try
        {
          String query = "SELECT e FROM ProformaInvoiceItem e WHERE e.proformaInvoice=?1";
        
        TypedQuery<ProformaInvoiceItem> typedQuery = crudApi.getEm().createQuery(query, ProformaInvoiceItem.class)
                                .setParameter(1, proformaInvoice);
                return typedQuery.getResultList();      
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }

    public List<InvoiceConfigItems> getInvoiceConfigItemsList(ProformaInvoice proformaInvoice)
    {
        try
        {
            String qryString = "SELECT e FROM InvoiceConfigItems e WHERE e.proformaInvoice = ?1";
            TypedQuery<InvoiceConfigItems> typedQuery = crudApi.getEm().createQuery(qryString, InvoiceConfigItems.class);
                                            typedQuery.setParameter(1, proformaInvoice);
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
            String qryString = "SELECT e FROM ProformaInvoice e";
            TypedQuery<ProformaInvoice> typedQuery = crudApi.getEm().createQuery(qryString, ProformaInvoice.class);
                         return typedQuery.getResultList();

        } catch (Exception e)
        {
            e.printStackTrace();
        }

        return Collections.emptyList();
    }
    
    public List<ProformaInvoice> getProformaInvoice(DateRangeUtil dateRange, ProformaInvoice proformaInvoice)
    {
        try {
            if(dateRange.getFromDate() == null || dateRange.getToDate() == null)
            {
                  String  queryString = "SELECT e FROM ProformaInvoice e";
                  TypedQuery<ProformaInvoice> typedQuery = crudApi.getEm().createQuery(queryString, ProformaInvoice.class);

                return typedQuery.getResultList();
            }
            
            String qryString = "SELECT e FROM ProformaInvoice e WHERE e.issuedDate BETWEEN ?1 AND ?2";
            
            TypedQuery<ProformaInvoice> typedQuery = crudApi.getEm().createQuery(qryString, ProformaInvoice.class)
                    .setParameter(1, dateRange.getFromDate())
                    .setParameter(2, dateRange.getToDate());
            
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

    
    
    public Invoice extractFromProformerInvoice(ProformaInvoice proformaInvoice)
    {
        Invoice standardInvoice = new Invoice();
        standardInvoice.setIssuedDate(proformaInvoice.getIssuedDate());
        standardInvoice.setClient(proformaInvoice.getClient());
        standardInvoice.setInvoiceNumber(proformaInvoice.getQuotationNumber());
        standardInvoice.setProject(proformaInvoice.getProject());
        standardInvoice.setSubject(proformaInvoice.getSubject());
        standardInvoice.setDescription(proformaInvoice.getDescription());
        standardInvoice.setTotalAmount(proformaInvoice.getTotalAmount());
        
        if(crudApi.save(standardInvoice) != null)
        {
            List<ProformaInvoiceItem> invoiceItemList = getProformaInvoiceItemList(proformaInvoice);
            
            for (ProformaInvoiceItem item : invoiceItemList)
            {
                ProformaInvoiceItem invoiceItem = new ProformaInvoiceItem();
                invoiceItem.setProformaInvoice(proformaInvoice);
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
