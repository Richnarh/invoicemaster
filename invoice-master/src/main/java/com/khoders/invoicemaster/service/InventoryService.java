/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.khoders.invoicemaster.service;

import com.khoders.invoicemaster.entities.Client;
import com.khoders.invoicemaster.entities.Inventory;
import com.khoders.invoicemaster.entities.Product;
import com.khoders.invoicemaster.entities.ProductType;
import com.khoders.invoicemaster.entities.PurchaseOrder;
import com.khoders.invoicemaster.entities.PurchaseOrderItem;
import com.khoders.invoicemaster.listener.AppSession;
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
            String qryString = "SELECT e FROM Product e WHERE  e.userAccount=?1";
            TypedQuery<Product> typedQuery = crudApi.getEm().createQuery(qryString, Product.class);
                                typedQuery.setParameter(1, appSession.getCurrentUser());
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
            String qryString = "SELECT e FROM Product e WHERE e.id=?1 AND  e.userAccount=?2";
            TypedQuery<Product> typedQuery = crudApi.getEm().createQuery(qryString, Product.class);
                                typedQuery.setParameter(1, id);
                                typedQuery.setParameter(2, appSession.getCurrentUser());
                                
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
            String qryString = "SELECT e FROM ProductType e WHERE e.userAccount=?1";
            TypedQuery<ProductType> typedQuery = crudApi.getEm().createQuery(qryString, ProductType.class);
                                    typedQuery.setParameter(1, appSession.getCurrentUser());
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
            String qryString = "SELECT e FROM Inventory e WHERE e.userAccount=?1";
            TypedQuery<Inventory> typedQuery = crudApi.getEm().createQuery(qryString, Inventory.class);
                            typedQuery.setParameter(1, appSession.getCurrentUser());
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
            String qryString = "SELECT e FROM PurchaseOrder e WHERE e.userAccount=?1";
            TypedQuery<PurchaseOrder> typedQuery = crudApi.getEm().createQuery(qryString, PurchaseOrder.class);
                                   typedQuery.setParameter(1, appSession.getCurrentUser());
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
           TypedQuery<PurchaseOrderItem> typedQuery = crudApi.getEm().createQuery("SELECT e FROM PurchaseOrderItem e WHERE e.purchaseOrder=?1 AND e.userAccount=?2", PurchaseOrderItem.class);
                            typedQuery.setParameter(1, purchaseOrder);
                            typedQuery.setParameter(2, appSession.getCurrentUser());
                            
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
            String qryString = "SELECT e FROM Client e WHERE e.userAccount=?1";
            TypedQuery<Client> typedQuery = crudApi.getEm().createQuery(qryString, Client.class);
                                typedQuery.setParameter(1, appSession.getCurrentUser());
                            return typedQuery.getResultList();
            
        } catch (Exception e)
        {
            e.printStackTrace();
        }

        return Collections.emptyList();
    }
}
