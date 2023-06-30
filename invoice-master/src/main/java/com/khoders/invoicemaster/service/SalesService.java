/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.khoders.invoicemaster.service;

import com.khoders.invoicemaster.entities.OnlineClient;
import com.khoders.invoicemaster.entities.SaleItem;
import com.khoders.resource.jpa.CrudApi;
import com.khoders.invoicemaster.mapper.ClientSalesMapper;
import com.khoders.invoicemaster.dto.OnlineClientDto;
import com.khoders.invoicemaster.dto.TransactionDto;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author richa
 */
@Stateless
public class SalesService
{
    @Inject private CrudApi crudApi;
    @Inject private ClientSalesMapper salesMapper;
    @Inject private TransactionService transactionService;
    
    public TransactionDto save(OnlineClientDto dto)
    {
        OnlineClient client = salesMapper.toEntity(dto);
        if(crudApi.save(client) != null)
        {
            for (SaleItem saleItem : client.getInvoiceItemList())
            {
                saleItem.setOnlineClient(client);
                crudApi.save(saleItem);
            }
           return transactionService.processTransaction(client);
        }
        return null;
    }
}
