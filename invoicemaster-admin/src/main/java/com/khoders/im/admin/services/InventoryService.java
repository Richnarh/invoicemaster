/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.khoders.im.admin.services;

import com.khoders.im.admin.listener.AppSession;
import com.khoders.invoicemaster.entities.Client;
import com.khoders.invoicemaster.entities.Inventory;
import com.khoders.invoicemaster.entities.Product;
import com.khoders.invoicemaster.entities.ProductType;
import com.khoders.invoicemaster.entities.ProformaInvoice;
import com.khoders.invoicemaster.entities.PurchaseOrder;
import com.khoders.invoicemaster.entities.PurchaseOrderItem;
import com.khoders.invoicemaster.entities.ReturnGood;
import com.khoders.resource.jpa.CrudApi;
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
public class InventoryService
{
     private @Inject CrudApi crudApi;
     private @Inject AppSession appSession;
     
    public List<Product> getProductList()
    {
        try
        {
//            String qryString = "SELECT e FROM Product e WHERE  e.companyBranch=?1";
            String qryString = "SELECT e FROM Product e";
            TypedQuery<Product> typedQuery = crudApi.getEm().createQuery(qryString, Product.class);
//                                typedQuery.setParameter(1, appSession.getCompanyBranch());
                            return typedQuery.getResultList();
            
        } catch (Exception e)
        {
            e.printStackTrace();
        }

        return Collections.emptyList();
    }
    
    public List<ReturnGood> getReturnGoodList()
    {
        try
        {
            String qryString = "SELECT e FROM ReturnGood e";
            TypedQuery<ReturnGood> typedQuery = crudApi.getEm().createQuery(qryString, ReturnGood.class);
                            return typedQuery.getResultList();
            
        } catch (Exception e)
        {
            e.printStackTrace();
        }

        return Collections.emptyList();
    }
    
    public Product getsingleImage(String id)
    {
        try
        {
            String qryString = "SELECT e FROM Product e WHERE e.id=?1";
            TypedQuery<Product> typedQuery = crudApi.getEm().createQuery(qryString, Product.class);
                                typedQuery.setParameter(1, id);
                                
                            return typedQuery.getResultStream().findFirst().orElse(null);
            
        } catch (Exception e)
        {
            e.printStackTrace();
        }

        return null;
    }
    
    public List<ProductType> getProductTypeList()
    {
        try
        {
            String qryString = "SELECT e FROM ProductType e";
            TypedQuery<ProductType> typedQuery = crudApi.getEm().createQuery(qryString, ProductType.class);
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
//            String qryString = "SELECT e FROM Inventory e WHERE e.companyBranch=?1";
            String qryString = "SELECT e FROM Inventory e";
            TypedQuery<Inventory> typedQuery = crudApi.getEm().createQuery(qryString, Inventory.class);
//                            typedQuery.setParameter(1, appSession.getCompanyBranch());
                            return typedQuery.getResultList();
            
        } catch (Exception e)
        {
            e.printStackTrace();
        }

        return Collections.emptyList();
    }
    
    public List<PurchaseOrder> getPurchaseOrderList()
    {
        try
        {
            String qryString = "SELECT e FROM PurchaseOrder e";
            TypedQuery<PurchaseOrder> typedQuery = crudApi.getEm().createQuery(qryString, PurchaseOrder.class);
                            return typedQuery.getResultList();
            
        } catch (Exception e)
        {
            e.printStackTrace();
        }

        return Collections.emptyList();
    }
    
    public List<PurchaseOrderItem> getPurchaseOrderItem(PurchaseOrder purchaseOrder)
    {
        try
        {
           TypedQuery<PurchaseOrderItem> typedQuery = crudApi.getEm().createQuery("SELECT e FROM PurchaseOrderItem e WHERE e.purchaseOrder=?1 AND e.companyBranch=?2", PurchaseOrderItem.class);
                            typedQuery.setParameter(1, purchaseOrder);
                            
                            return  typedQuery.getResultList();
           
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }
    
    public List<Client> getClientList()
    {
        try
        {
            String qryString = "SELECT e FROM Client e";
            TypedQuery<Client> typedQuery = crudApi.getEm().createQuery(qryString, Client.class);
                            return typedQuery.getResultList();
            
        } catch (Exception e)
        {
            e.printStackTrace();
        }

        return Collections.emptyList();
    }
    public Client clientExist(String phone)
    {
        try
        {
            String qryString = "SELECT e FROM Client e WHERE e.phone=?1";
            TypedQuery<Client> typedQuery = crudApi.getEm().createQuery(qryString, Client.class)
                    .setParameter(1, phone);
            
                    return typedQuery.getResultStream().findFirst().orElse(null);
                    
        } catch (Exception e)
        {
            e.printStackTrace();
        }

        return null;
    }
    public ProformaInvoice invoiceExist(String refNo, Client client)
    {
        try
        {
            String qryString = "SELECT e FROM ProformaInvoice e WHERE e.quotationNumber=?1 AND e.client=?2";
            TypedQuery<ProformaInvoice> typedQuery = crudApi.getEm().createQuery(qryString, ProformaInvoice.class)
                    .setParameter(1, refNo)
                    .setParameter(2, client);
            
                    return typedQuery.getResultStream().findFirst().orElse(null);
                    
        } catch (Exception e)
        {
            e.printStackTrace();
        }

        return null;
    }

}
