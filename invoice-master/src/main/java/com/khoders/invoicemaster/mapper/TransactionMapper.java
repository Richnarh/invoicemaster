/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.khoders.invoicemaster.mapper;

import com.khoders.invoicemaster.reportData.SalesTaxDto;
import com.khoders.invoicemaster.entities.OnlineClient;
import com.khoders.invoicemaster.entities.SaleItem;
import com.khoders.invoicemaster.entities.SalesTax;
import com.khoders.invoicemaster.entities.UserTransaction;
import com.khoders.invoicemaster.enums.DeliveryStatus;
import com.khoders.resource.enums.PaymentStatus;
import com.khoders.resource.exception.DataNotFoundException;
import com.khoders.resource.jpa.CrudApi;
import com.khoders.invoicemaster.dto.TransactionDto;
import com.khoders.invoicemaster.service.TransactionService;
import java.util.LinkedList;
import java.util.List;
import javax.inject.Inject;

/**
 *
 * @author richa
 */
public class TransactionMapper
{
    @Inject private CrudApi crudApi;
    @Inject private ClientSalesMapper salesMapper;
    @Inject private TransactionService transactionService;
    

    public TransactionDto toDto(OnlineClient client)
    {
        TransactionDto dto = new TransactionDto();
        if(client.getId() == null) return null;
        dto.setId(client.getId());
        dto.setClientName(client.getFirstname() + client.getLastname());
        dto.setInvoiceItemList(salesMapper.toDto(client.getInvoiceItemList()));
        dto.setDeliveryStatus(DeliveryStatus.PENDING_DELIVERY.getLabel());
        dto.setPaymentStatus(PaymentStatus.PENDING.getLabel());
        dto.setOnlineClientId(client.getId());
        dto.setOnlineClientName(client.getFirstname()+ " "+client.getLastname());
        
        double totalAmount = client.getInvoiceItemList().stream().mapToDouble(SaleItem::getSubTotal).sum();
        
        dto.setTotalAmount(totalAmount);
        toEntity(dto);
        
        return dto;
    }
    
    public UserTransaction toEntity(TransactionDto dto)
    {
        UserTransaction ut = new UserTransaction();
        
        if(dto.getId() != null){
            ut.setId(dto.getId());
        }
        try{
            ut.setDeliveryStatus(DeliveryStatus.PENDING_DELIVERY);
        } catch (Exception e){}

        try{
            ut.setPaymentStatus(PaymentStatus.PENDING);
        } catch (Exception e){}
        
        if(dto.getOnlineClientId() == null){
            throw new DataNotFoundException("Please Specify Client Id");
        }
        OnlineClient client = crudApi.find(OnlineClient.class, dto.getOnlineClientId());
        if(client != null){
            ut.setOnlineClient(client);
        }
        
        ut.setTotalAmount(dto.getTotalAmount());
        
        transactionService.save(ut);
         
        return ut;
    }
    
    public List<SalesTaxDto> toDto(List<SalesTax> salesTaxList)
    {
        List<SalesTaxDto> taxDtoList = new LinkedList<>();
        
        for (SalesTax salesTax : salesTaxList)
        {
            SalesTaxDto dto = new SalesTaxDto();
            dto.setId(salesTax.getId());
            dto.setSalesTaxId(salesTax.getSalesTaxId());
            dto.setReOrder(salesTax.getReOrder());
            dto.setTaxName(salesTax.getTaxName());
            dto.setTaxAmount(salesTax.getTaxAmount());
            dto.setTaxRate(salesTax.getTaxRate());
            
            taxDtoList.add(dto);
        }
        
        return taxDtoList;
    }
}
