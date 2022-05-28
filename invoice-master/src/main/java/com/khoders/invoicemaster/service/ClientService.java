/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.khoders.invoicemaster.service;

import com.khoders.invoicemaster.entities.OnlineClient;
import com.khoders.invoicemaster.entities.SaleItem;
import com.khoders.invoicemaster.entities.UserTransaction;
import com.khoders.resource.jpa.CrudApi;
import com.khoders.resource.jpa.QueryBuilder;
import com.khoders.resource.utilities.DateRangeUtil;
import java.util.Collections;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 *
 * @author richa
 */
@Stateless
public class ClientService
{
    @Inject private QueryBuilder builder;
    @Inject private CrudApi crudApi;

    public List<UserTransaction> userTransactionList()
    {
        return builder.findAll(UserTransaction.class); 
    }
    
    public List<UserTransaction> userTransactionList(OnlineClient onlineClient)
    {
        CriteriaBuilder cb = crudApi.getEm().getCriteriaBuilder();
        CriteriaQuery<UserTransaction> criteriaQuery = cb.createQuery(UserTransaction.class);
        Root<UserTransaction> root = criteriaQuery.from(UserTransaction.class);
        criteriaQuery.where(cb.equal(root.get(UserTransaction._onlineClient), onlineClient));
        Query query = crudApi.getEm().createQuery(criteriaQuery);
        List<UserTransaction> itemList = query.getResultList();
        
        return itemList;
        
//        return builder.queryOne(UserTransaction.class, onlineClient);
    }
    
    public List<SaleItem> salesList(OnlineClient onlineClient)
    {
        CriteriaBuilder cb = crudApi.getEm().getCriteriaBuilder();
        CriteriaQuery<SaleItem> criteriaQuery = cb.createQuery(SaleItem.class);
        Root<SaleItem> root = criteriaQuery.from(SaleItem.class);
        criteriaQuery.where(cb.equal(root.get(SaleItem._onlineClient), onlineClient));
        Query query = crudApi.getEm().createQuery(criteriaQuery);
        List<SaleItem> itemList = query.getResultList();
        
        return itemList;
        
//        return builder.queryOne(UserTransaction.class, onlineClient);
    }

    public List<OnlineClient> getOnlineClientList()
    {
        return builder.findAll(OnlineClient.class); 
    }

    public List<OnlineClient> filterTransaction(DateRangeUtil dateRange)
    {
       try 
        {
            if(dateRange.getFromDate() == null || dateRange.getToDate() == null)
            {
                  String  queryString = "SELECT e FROM OnlineClient e ORDER BY e.valueDate DESC";
                  TypedQuery<OnlineClient> typedQuery = crudApi.getEm().createQuery(queryString, OnlineClient.class);
                                    return typedQuery.getResultList();
            }
            
            String qryString = "SELECT e FROM OnlineClient e WHERE e.valueDate BETWEEN ?1 AND ?2 ORDER BY e.valueDate DESC";
            TypedQuery<OnlineClient> typedQuery = crudApi.getEm().createQuery(qryString, OnlineClient.class)
                    .setParameter(1, dateRange.getFromDate())
                    .setParameter(2, dateRange.getToDate());
           return typedQuery.getResultList();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }
    
}
