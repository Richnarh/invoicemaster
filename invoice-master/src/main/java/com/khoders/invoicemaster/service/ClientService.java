/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.khoders.invoicemaster.service;

import com.khoders.invoicemaster.entities.OnlineClient;
import com.khoders.invoicemaster.entities.UserTransaction;
import com.khoders.resource.jpa.CrudApi;
import com.khoders.resource.jpa.QueryBuilder;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.Query;
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
        CriteriaBuilder builder = crudApi.getEm().getCriteriaBuilder();
        CriteriaQuery<UserTransaction> criteriaQuery = builder.createQuery(UserTransaction.class);
        Root<UserTransaction> root = criteriaQuery.from(UserTransaction.class);
        criteriaQuery.where(builder.equal(root.get(UserTransaction._onlineClient), onlineClient));
        Query query = crudApi.getEm().createQuery(criteriaQuery);
        List<UserTransaction> itemList = query.getResultList();
        
        return itemList;
        
//        return builder.queryOne(UserTransaction.class, onlineClient);
    }

    public List<OnlineClient> getOnlineClientList()
    {
        return builder.findAll(OnlineClient.class); 
    }
    
}