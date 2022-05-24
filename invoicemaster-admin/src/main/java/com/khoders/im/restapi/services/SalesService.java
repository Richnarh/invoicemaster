/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.khoders.im.restapi.services;

import com.khoders.im.restapi.mapper.SalesMapper;
import com.khoders.im.restapi.payload.SaleDto;
import com.khoders.invoicemaster.entities.Register;
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
        Register register = salesMapper.toEntity(dto);
        if(crudApi.save(register) != null)
        {
            for (SaleItem saleItem : register.getSaleItemList())
            {
                saleItem.setRegister(register);
                crudApi.save(saleItem);
            }
           return salesMapper.toDto(register);
        }
        return null;
    }
}
