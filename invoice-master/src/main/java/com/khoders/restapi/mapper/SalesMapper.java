/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.khoders.restapi.mapper;

import com.khoders.invoicemaster.entities.Inventory;
import com.khoders.invoicemaster.entities.OnlineClient;
import com.khoders.invoicemaster.entities.SaleItem;
import com.khoders.resource.exception.DataNotFoundException;
import com.khoders.resource.jpa.CrudApi;
import static com.khoders.resource.utilities.SecurityUtil.hashText;
import com.khoders.restapi.payload.SaleDto;
import com.khoders.restapi.payload.SaleItemDto;
import com.khoders.invoicemaster.enums.ClientStatus;
import java.util.LinkedList;
import java.util.List;
import javax.inject.Inject;

/**
 *
 * @author richa
 */
public class SalesMapper
{
    @Inject private CrudApi crudApi;

    public OnlineClient toEntity(SaleDto dto)
    {
        OnlineClient client = new OnlineClient();
        if (dto.getId() != null)
        {
            client.setId(dto.getId());
        }

        client.setCity(dto.getCity());
        client.setCountry(dto.getCountry());
        client.setEmailAddress(dto.getEmailAddress());
        client.setFirstname(dto.getFirstname());
        client.setLastname(dto.getLastname());
        client.setOrderNote(dto.getOrderNote());
        client.setPhoneNumber(dto.getPhoneNumber());
        client.setPostalCode(dto.getPostalCode());
        client.setRegion(dto.getRegion());
        client.setUsername(dto.getUsername());
        client.setPassword(hashText(dto.getPassword()));
        client.setClientStatus(ClientStatus.NEW);
        client.setSaleItemList(toEntity(dto.getSaleItemList()));
        
        return client;
    }

    public List<SaleItem> toEntity(List<SaleItemDto> itemDtoList)
    {
        List<SaleItem> saleItemList = new LinkedList<>();
        for (SaleItemDto dto : itemDtoList)
        {
            SaleItem saleItem = new SaleItem();
            if (dto.getId() != null)
            {
                saleItem.setId(dto.getId());
            }
            saleItem.setPrice(dto.getPrice());
            saleItem.setQuantity(dto.getQuantity());

            if (dto.getInventoryId() == null || dto.getInventoryId().equals(""))
            {
                throw new DataNotFoundException("Please Specify a Valid InventoryId");
            }
            Inventory inventory = crudApi.find(Inventory.class, dto.getInventoryId());
            if (inventory != null)
            {
                saleItem.setInventory(inventory);
            }
            saleItem.setSubTotal(dto.getPrice() * dto.getQuantity());
            
            saleItemList.add(saleItem);
        }

        return saleItemList;
    }

    public SaleDto toDto(OnlineClient client)
    {
        SaleDto dto = new SaleDto();
        if (client.getId() == null) return null;

        dto.setId(client.getId());
        dto.setCity(client.getCity());
        dto.setCountry(client.getCountry());
        dto.setEmailAddress(client.getEmailAddress());
        dto.setFirstname(client.getFirstname());
        dto.setLastname(client.getLastname());
        dto.setOrderNote(client.getOrderNote());
        dto.setPhoneNumber(client.getPhoneNumber());
        dto.setPostalCode(client.getPostalCode());
        dto.setRegion(client.getRegion());
        dto.setUsername(client.getUsername());
        dto.setSaleItemList(toDto(client.getSaleItemList()));
        
        return dto;
    }
    
    public List<SaleItemDto> toDto(List<SaleItem> saleItemList){
        List<SaleItemDto> saleItems = new LinkedList<>();
        for (SaleItem saleItem : saleItemList)
        {
            SaleItemDto dto = new SaleItemDto();
            
            if (saleItem.getId() == null) return null;
            
            dto.setId(saleItem.getId());
            dto.setPrice(saleItem.getPrice());
            dto.setQuantity(saleItem.getQuantity());

            if (saleItem.getInventory() != null && saleItem.getInventory().getProduct() != null)
            {
                dto.setInventory(saleItem.getInventory().getId());
                dto.setInventory(saleItem.getInventory().getProduct().getProductName());
            }
            dto.setSubTotal(saleItem.getPrice() * saleItem.getQuantity());
            
            saleItems.add(dto);
        }

        return saleItems;
    }
}
