/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.khoders.invoicemaster.service;

import com.khoders.invoicemaster.entites.Colours;
import com.khoders.invoicemaster.entites.ColoursConfigItems;
import com.khoders.invoicemaster.entites.DeliveryTerm;
import com.khoders.invoicemaster.entites.DeliveryTermConfigItems;
import com.khoders.invoicemaster.entites.Inventory;
import com.khoders.invoicemaster.entites.Invoice;
import com.khoders.invoicemaster.entites.InvoiceItem;
import com.khoders.invoicemaster.entites.ProformaInvoice;
import com.khoders.invoicemaster.entites.ProformaInvoiceItem;
import com.khoders.invoicemaster.entites.ReceivedDocumentConfigItems;
import com.khoders.invoicemaster.entites.Validation;
import com.khoders.invoicemaster.entites.ValidationConfigItems;
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

    public List<DeliveryTermConfigItems> getDeliveryTermConfigItemsList(ProformaInvoice proformaInvoice)
    {
        try
        {
            String qryString = "SELECT e FROM DeliveryTermConfigItems e WHERE e.proformaInvoice = ?1";
            TypedQuery<DeliveryTermConfigItems> typedQuery = crudApi.getEm().createQuery(qryString, DeliveryTermConfigItems.class);
                                            typedQuery.setParameter(1, proformaInvoice);
                                     return typedQuery.getResultList();

        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }

    public List<ValidationConfigItems> getValidationConfigItemsList(ProformaInvoice proformaInvoice)
    {
        try
        {
            String qryString = "SELECT e FROM ValidationConfigItems e WHERE e.proformaInvoice = ?1";
            TypedQuery<ValidationConfigItems> typedQuery = crudApi.getEm().createQuery(qryString, ValidationConfigItems.class);
                                            typedQuery.setParameter(1, proformaInvoice);
                                     return typedQuery.getResultList();

        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }

    public List<ColoursConfigItems> getColoursConfigItemsList(ProformaInvoice proformaInvoice)
    {
        try
        {
            String qryString = "SELECT e FROM ColoursConfigItems e WHERE e.proformaInvoice = ?1";
            TypedQuery<ColoursConfigItems> typedQuery = crudApi.getEm().createQuery(qryString, ColoursConfigItems.class);
                                            typedQuery.setParameter(1, proformaInvoice);
                                     return typedQuery.getResultList();

        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }

    public List<ReceivedDocumentConfigItems> getReceivedDocumentConfigItemsList(ProformaInvoice proformaInvoice)
    {
        try
        {
            String qryString = "SELECT e FROM ReceivedDocumentConfigItems e WHERE e.proformaInvoice = ?1";
            TypedQuery<ReceivedDocumentConfigItems> typedQuery = crudApi.getEm().createQuery(qryString, ReceivedDocumentConfigItems.class);
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

    public List<ValidationConfigItems> getValidationConfigItems(ProformaInvoice proformaInvoice)
    {
        try
        {
            String qryString = "SELECT e FROM ValidationConfigItems e WHERE e.proformaInvoice=?1";
            TypedQuery<ValidationConfigItems> typedQuery = crudApi.getEm().createQuery(qryString, ValidationConfigItems.class);
            typedQuery.setParameter(1, proformaInvoice);
            return typedQuery.getResultList();
            
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        
        return Collections.emptyList();
    }
    
    public List<ReceivedDocumentConfigItems> getReceivedDocumentConfigItems(ProformaInvoice proformaInvoice)
    {
        try
        {
            String qryString = "SELECT e FROM ReceivedDocumentConfigItems e WHERE e.proformaInvoice=?1";
            TypedQuery<ReceivedDocumentConfigItems> typedQuery = crudApi.getEm().createQuery(qryString, ReceivedDocumentConfigItems.class);
            typedQuery.setParameter(1, proformaInvoice);
            return typedQuery.getResultList();
            
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        
        return Collections.emptyList();
    }
    
    public List<ColoursConfigItems> getColoursConfigItems(ProformaInvoice proformaInvoice)
    {
        try
        {
            String qryString = "SELECT e FROM ColoursConfigItems e WHERE e.proformaInvoice=?1";
            TypedQuery<ColoursConfigItems> typedQuery = crudApi.getEm().createQuery(qryString, ColoursConfigItems.class);
            typedQuery.setParameter(1, proformaInvoice);
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
            String qryString = "SELECT e FROM ProformaInvoiceItem e WHERE e.proformaInvoice=?1";
            TypedQuery<ProformaInvoiceItem> typedQuery = crudApi.getEm().createQuery(qryString, ProformaInvoiceItem.class);
            typedQuery.setParameter(1, proformaInvoice);
            return typedQuery.getResultList();
            
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        
        return Collections.emptyList();
    }
    
    
 public Invoice extractFromProformaInvoice(ProformaInvoice proformaInvoice)
    {
        Invoice invoice = new Invoice();
        invoice.setInvoiceNumber(proformaInvoice.getQuotationNumber());
        invoice.setIssuedDate(proformaInvoice.getIssuedDate());
        invoice.setClient(proformaInvoice.getClient());
        invoice.setProject(proformaInvoice.getProject());
        invoice.setSubject(proformaInvoice.getSubject());
        invoice.setDescription(proformaInvoice.getDescription());
        invoice.setTotalAmount(proformaInvoice.getTotalAmount());
        
        if(crudApi.save(invoice) != null)
        {
            List<ProformaInvoiceItem> proformaInvoiceItemList = getProformaInvoiceItemList(proformaInvoice);
            
            for (ProformaInvoiceItem proformaInvoiceItem : proformaInvoiceItemList)
            {
                InvoiceItem invoiceItem = new InvoiceItem();
                invoiceItem.setInvoice(invoice);
                invoiceItem.setItemCode(proformaInvoiceItem.getItemCode());
                invoiceItem.setInventoryProduct(proformaInvoiceItem.getInventoryProduct());
                invoiceItem.setUnitPrice(proformaInvoiceItem.getUnitPrice());
                invoiceItem.setQuantity(proformaInvoiceItem.getQuantity());
                invoiceItem.setCharges(proformaInvoiceItem.getCharges());
                invoiceItem.setTotalAmount(proformaInvoiceItem.getTotalAmount());

                crudApi.save(invoiceItem);
            }
        }
        
        return invoice;
    }
}
