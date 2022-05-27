/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.khoders.restapi.services;

import com.khoders.restapi.mapper.SalesMapper;
import com.khoders.restapi.payload.SaleDto;
import com.khoders.invoicemaster.entities.OnlineClient;
import com.khoders.invoicemaster.entities.SaleItem;
import com.khoders.resource.jpa.CrudApi;
import javax.inject.Inject;

/**
 *
 * @author richa
 */
public class SalesService
{
    @Inject private CrudApi crudApi;
    @Inject private SalesMapper salesMapper;
    
    public SaleDto save(SaleDto dto)
    {
        OnlineClient client = salesMapper.toEntity(dto);
        if(crudApi.save(client) != null)
        {
            for (SaleItem saleItem : client.getSaleItemList())
            {
                saleItem.setOnlineClient(client);
                crudApi.save(saleItem);
            }
           return salesMapper.toDto(client);
        }
        return null;
    }
}