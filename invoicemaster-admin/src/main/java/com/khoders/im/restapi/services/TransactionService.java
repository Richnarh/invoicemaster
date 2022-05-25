/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.khoders.im.restapi.services;

import com.khoders.im.restapi.mapper.SalesMapper;
import com.khoders.im.restapi.payload.SaleItemDto;
import com.khoders.invoicemaster.entities.Register;
import com.khoders.invoicemaster.entities.UserTransaction;
import com.khoders.resource.exception.DataNotFoundException;
import com.khoders.resource.jpa.CrudApi;
import com.khoders.resource.utilities.SystemUtils;
import java.util.LinkedList;
import java.util.List;
import javax.inject.Inject;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;


/**
 *
 * @author richa
 */
public class TransactionService
{
    @Inject private CrudApi crudApi;
    @Inject private SalesMapper salesMapper;
    
    public List<SaleItemDto> getSales(String userAccountId)
    {
        Register register = crudApi.find(Register.class, userAccountId);
        if(register == null)
        {
            throw new DataNotFoundException("Please user with ID "+userAccountId+ " not found!");
        }
        List<SaleItemDto> saleItemList = new LinkedList<>();
        
//        CriteriaBuilder builder = crudApi.getEm().getCriteriaBuilder();
//        CriteriaQuery<SaleItem> criteriaQuery = builder.createQuery(SaleItem.class);
//        Root<SaleItem> root = criteriaQuery.from(SaleItem.class);
//        criteriaQuery.where(builder.equal(root.get(SaleItem._register), register));
//        Query query = crudApi.getEm().createQuery(criteriaQuery);
//        List<SaleItem> itemList = query.getResultList();
//        
//        
//        saleItemList.addAll(salesMapper.toDto(itemList));
//        return saleItemList;
            

        CriteriaBuilder builder  = crudApi.getEm().getCriteriaBuilder();
        CriteriaQuery<UserTransaction> criteriaQuery = builder.createQuery(UserTransaction.class);
        Root<UserTransaction> root = criteriaQuery.from(UserTransaction.class);
        Join<Register, UserTransaction> regTrans = root.join(UserTransaction._register);
        criteriaQuery.where(builder.equal(root.get(UserTransaction._register), register));
        criteriaQuery.select(regTrans).distinct(true);
        Query query = crudApi.getEm().createQuery(criteriaQuery);
        List<Object[]> itemList = query.getResultList();
        
        System.out.println("Data -- "+SystemUtils.KJson().toJson(itemList));
        
        return saleItemList;
    }
}