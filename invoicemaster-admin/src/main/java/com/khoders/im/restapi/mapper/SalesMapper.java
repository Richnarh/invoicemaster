/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.khoders.im.restapi.mapper;

import com.khoders.invoicemaster.entities.Inventory;
import com.khoders.invoicemaster.entities.Register;
import com.khoders.invoicemaster.entities.SaleItem;
import com.khoders.resource.exception.DataNotFoundException;
import com.khoders.resource.jpa.CrudApi;
import static com.khoders.resource.utilities.SecurityUtil.hashText;
import com.khoders.im.restapi.payload.SaleDto;
import com.khoders.im.restapi.payload.SaleItemDto;
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

    public Register toEntity(SaleDto dto)
    {
        Register register = new Register();
        if (dto.getId() != null)
        {
            register.setId(dto.getId());
        }

        register.setCity(dto.getCity());
        register.setCountry(dto.getCountry());
        register.setEmailAddress(dto.getEmailAddress());
        register.setFirstname(dto.getFirstname());
        register.setLastname(dto.getLastname());
        register.setOrderNote(dto.getOrderNote());
        register.setPhoneNumber(dto.getPhoneNumber());
        register.setPostalCode(dto.getPostalCode());
        register.setRegion(dto.getRegion());
        register.setUsername(dto.getUsername());
        register.setPassword(hashText(dto.getPassword()));
        register.setSaleItemList(toEntity(dto.getSaleItemList()));
        
        return register;
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

    public SaleDto toDto(Register register)
    {
        SaleDto dto = new SaleDto();
        if (register.getId() == null) return null;

        dto.setId(register.getId());
        dto.setCity(register.getCity());
        dto.setCountry(register.getCountry());
        dto.setEmailAddress(register.getEmailAddress());
        dto.setFirstname(register.getFirstname());
        dto.setLastname(register.getLastname());
        dto.setOrderNote(register.getOrderNote());
        dto.setPhoneNumber(register.getPhoneNumber());
        dto.setPostalCode(register.getPostalCode());
        dto.setRegion(register.getRegion());
        dto.setUsername(register.getUsername());
        dto.setSaleItemList(toDto(register.getSaleItemList()));
        
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
