/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.khoders.restapi.services;

import com.khoders.restapi.payload.SaleItemDto;
import com.khoders.invoicemaster.entities.OnlineClient;
import com.khoders.invoicemaster.entities.SaleItem;
import com.khoders.resource.exception.DataNotFoundException;
import com.khoders.resource.jpa.CrudApi;
import com.khoders.resource.jpa.QueryBuilder;
import com.khoders.restapi.mapper.ClientSalesMapper;
import com.khoders.restapi.payload.TransactionDto;
import java.util.LinkedList;
import java.util.List;
import javax.inject.Inject;


/**
 *
 * @author richa
 */
public class TransactionService
{
    @Inject private CrudApi crudApi;
    @Inject private QueryBuilder builder;
    @Inject private ClientSalesMapper salesMapper;
    
    public List<SaleItemDto> getSales(String userAccountId)
    {
        OnlineClient onlineClient = crudApi.find(OnlineClient.class, userAccountId);
        if(onlineClient == null)
        {
            throw new DataNotFoundException("Please user with ID "+userAccountId+ " not found!");
        }
        List<SaleItemDto> saleItemList = new LinkedList<>();
        
//        CriteriaBuilder builder = crudApi.getEm().getCriteriaBuilder();
//        CriteriaQuery<SaleItem> criteriaQuery = builder.createQuery(SaleItem.class);
//        Root<SaleItem> root = criteriaQuery.from(SaleItem.class);
//        criteriaQuery.where(builder.equal(root.get(SaleItem._onlineClient), onlineClient));
//        Query query = crudApi.getEm().createQuery(criteriaQuery);

        List<SaleItem> itemList = builder.queryOne(SaleItem.class, onlineClient);
        
        
        saleItemList.addAll(salesMapper.toDto(itemList));

            

//        CriteriaBuilder builder  = crudApi.getEm().getCriteriaBuilder();
//        CriteriaQuery<UserTransaction> criteriaQuery = builder.createQuery(UserTransaction.class);
//        Root<UserTransaction> root = criteriaQuery.from(UserTransaction.class);
//        Join<Register, UserTransaction> regTrans = root.join(UserTransaction._register);
//        criteriaQuery.where(builder.equal(root.get(UserTransaction._register), register));
//        criteriaQuery.select(regTrans).distinct(true);
//        Query query = crudApi.getEm().createQuery(criteriaQuery);
//        List<Object[]> itemList = query.getResultList();
//        
//        System.out.println("Data -- "+SystemUtils.KJson().toJson(itemList));
        
        return saleItemList;
    }
    

    public TransactionDto processTransaction(OnlineClient client)
    {
        TransactionDto dto = new TransactionDto();
        
        return dto;
    }
}
