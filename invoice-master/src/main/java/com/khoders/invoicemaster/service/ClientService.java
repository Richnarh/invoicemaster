/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.khoders.invoicemaster.service;

import com.khoders.admin.mapper.AppParam;
import com.khoders.invoicemaster.dto.ClientDto;
import com.khoders.invoicemaster.dto.InvoiceDto;
import com.khoders.invoicemaster.entities.Client;
import com.khoders.invoicemaster.entities.OnlineClient;
import com.khoders.invoicemaster.entities.ProformaInvoice;
import com.khoders.invoicemaster.entities.SaleItem;
import com.khoders.invoicemaster.entities.UserTransaction;
import com.khoders.invoicemaster.mapper.ClientMapper;
import com.khoders.resource.jpa.CrudApi;
import com.khoders.resource.jpa.QueryBuilder;
import com.khoders.resource.utilities.DateRangeUtil;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author richa
 */
@Stateless
public class ClientService{
    
    private static final Logger log = LoggerFactory.getLogger(ClientService.class);
    
    @Inject private QueryBuilder builder;
    @Inject private CrudApi crudApi;
    @Inject private InventoryService inventoryService;
    @Inject private ClientMapper mapper;
    
    public ClientDto save(ClientDto clientDto, AppParam param) {
       Client client = mapper.toEntity(clientDto, param);
       ClientDto dto = null;
       if(crudApi.save(client) != null){
           dto = mapper.toDto(client);
       }
       return dto;
    }

    public ClientDto findById(String clientId) {
       Client invoice = inventoryService.getClientById(clientId);
       return mapper.toDto(invoice);
    }

    public List<ClientDto> findAll() {
        log.debug("Fetching all Client");
        List<Client> clientList = inventoryService.getClientList();
        List<ClientDto> dtoList = new LinkedList<>();
        clientList.forEach(item -> {
            dtoList.add(mapper.toDto(item));
        });
        return dtoList;
    }

    public boolean delete(String clientId) {
        Client client = inventoryService.getClientById(clientId);
        return client != null ? crudApi.delete(client) : false;
    }

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
