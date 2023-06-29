/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.khoders.invoicemaster.mapper;

import com.khoders.invoicemaster.dto.InvoiceDto;
import com.khoders.invoicemaster.entities.ProformaInvoice;
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
}
