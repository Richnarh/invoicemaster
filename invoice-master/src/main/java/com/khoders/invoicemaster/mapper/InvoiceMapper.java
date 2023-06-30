/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.khoders.invoicemaster.mapper;

import com.khoders.invoicemaster.dto.InvoiceDto;
import com.khoders.invoicemaster.dto.InvoiceItemDto;
import com.khoders.invoicemaster.entities.ProformaInvoice;
import com.khoders.invoicemaster.entities.ProformaInvoiceItem;
import com.khoders.resource.jpa.CrudApi;
import javax.inject.Inject;

/**
 *
 * @author Pascal
 */
public class InvoiceMapper {
    @Inject private CrudApi crudApi;
    
    public ProformaInvoice toEntity(InvoiceDto dto){
        ProformaInvoice invoice = new ProformaInvoice();
        return invoice;
    }
    public InvoiceDto toDto(ProformaInvoice invoice){
        InvoiceDto dto = new InvoiceDto();
        if(invoice.getId() == null) return null;
        dto.setId(invoice.getId());
        return dto;
    }
    
    public ProformaInvoiceItem toEntity(InvoiceItemDto dto){
        ProformaInvoiceItem invoiceItem = new ProformaInvoiceItem();
        if(dto.getId() != null){
            invoiceItem.setId(dto.getId());
        }
        invoiceItem.setDescription(dto.getDescription());
        invoiceItem.setItemCode(dto.getItemCode());
        return invoiceItem;
    }
    public InvoiceItemDto toDto(ProformaInvoiceItem invoiceItem){
        InvoiceItemDto dto = new InvoiceItemDto();
        if(invoiceItem.getId() == null) return null;
        dto.setId(invoiceItem.getId());
        dto.setDescription(invoiceItem.getDescription());
        dto.setItemCode(invoiceItem.getItemCode());
        dto.setQuantity(invoiceItem.getQuantity());
        dto.setUnitPrice(invoiceItem.getUnitPrice());
        dto.setInventory(invoiceItem.getInventory() != null ? invoiceItem.getInventory() +"" : null);
        dto.setInventory(invoiceItem.getInventory() != null ? invoiceItem.getInventory().getId() : null);
        dto.setProformaInvoice(invoiceItem.getProformaInvoice()  != null ? invoiceItem.getProformaInvoice().getQuotationNumber() : null);
        dto.setProformaInvoiceId(invoiceItem.getProformaInvoice() != null ? invoiceItem.getProformaInvoice().getId() : null);
        return dto;
    }
}
