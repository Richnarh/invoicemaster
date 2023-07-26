/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.khoders.invoicemaster.mapper;

import com.khoders.invoicemaster.dto.InvoiceDto;
import com.khoders.invoicemaster.dto.InvoiceItemDto;
import com.khoders.invoicemaster.entities.Client;
import com.khoders.invoicemaster.entities.Inventory;
import com.khoders.invoicemaster.entities.ProformaInvoice;
import com.khoders.invoicemaster.entities.ProformaInvoiceItem;
import com.khoders.resource.enums.PaymentMethod;
import com.khoders.resource.exception.DataNotFoundException;
import com.khoders.resource.jpa.CrudApi;
import com.khoders.resource.utilities.DateUtil;
import com.khoders.resource.utilities.Pattern;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author Pascal
 */
@Stateless
public class InvoiceMapper {
    @Inject private CrudApi crudApi;
    
    public ProformaInvoice toEntity(InvoiceDto dto){
        ProformaInvoice invoice = new ProformaInvoice();
        if(dto.getClientId() == null){
            throw new DataNotFoundException("Client Id is required");
        }
        Client client = crudApi.find(Client.class, dto.getClientId());
        if(client != null){
            invoice.setClient(client);
        }
        invoice.setDescription(dto.getDescription());
        invoice.setDiscountRate(invoice.getDiscountRate());
        invoice.setExpiryDate(DateUtil.parseLocalDate(dto.getExpiryDate(), Pattern._yyyyMMdd));
        invoice.setIssuedDate(DateUtil.parseLocalDate(dto.getIssuedDate(), Pattern._yyyyMMdd));
        invoice.setInstallationFee(dto.getInstallationFee());
        invoice.setModeOfPayment(PaymentMethod.valueOf(dto.getModeOfPayment()));
        invoice.setQuotationNumber(dto.getQuotationNumber());
        invoice.setSubTotalAmount(dto.getSubTotalAmount());
        invoice.setTotalAmount(dto.getTotalAmount());
        return invoice;
    }
    
    public InvoiceDto toDto(ProformaInvoice invoice){
        InvoiceDto dto = new InvoiceDto();
        if(invoice.getId() == null) return null;
        dto.setId(invoice.getId());
        dto.setQuotationNumber(invoice.getQuotationNumber());
        dto.setDescription(invoice.getDescription());
        dto.setConverted(invoice.isConverted());
        dto.setDiscountRate(invoice.getDiscountRate());
        dto.setExpiryDate(DateUtil.parseLocalDateString(invoice.getExpiryDate(), Pattern.ddMMyyyy));
        dto.setIssuedDate(DateUtil.parseLocalDateString(invoice.getIssuedDate(), Pattern.ddMMyyyy));
        dto.setInstallationFee(invoice.getInstallationFee());
        dto.setModeOfPayment(invoice.getModeOfPayment() != null ? invoice.getModeOfPayment().getLabel() : null);
        dto.setSubTotalAmount(invoice.getSubTotalAmount());
        dto.setTotalAmount(invoice.getTotalAmount());
        if(invoice.getClient() != null){
            dto.setClient(invoice.getClient().getClientName());
            dto.setClientId(invoice.getClient().getId());
            dto.setPhoneNumber(invoice.getClient().getPhone());
        }
        dto.setValueDate(DateUtil.parseLocalDateString(invoice.getValueDate(), Pattern.EEEMMMdyyyy));
        return dto;
    }
    
    public ProformaInvoiceItem toEntity(InvoiceItemDto dto){
        ProformaInvoiceItem invoiceItem = new ProformaInvoiceItem();
        if(dto.getId() != null){
            invoiceItem.setId(dto.getId());
        }
        invoiceItem.setDescription(dto.getDescription());
        invoiceItem.setItemCode(dto.getItemCode());
        if(dto.getInventoryId() == null){
            throw new DataNotFoundException("Inventory Id is required");
        }
        if(dto.getProformaInvoiceId() == null){
            throw new DataNotFoundException("Invoice Id is required");
        }
        Inventory inventory = crudApi.find(Inventory.class, dto.getInventoryId());
        if(inventory != null){
            invoiceItem.setInventory(inventory);
        }
        ProformaInvoice invoice = crudApi.find(ProformaInvoice.class, dto.getProformaInvoiceId());
        if(invoice != null){
            invoiceItem.setProformaInvoice(invoice);
        }
        invoiceItem.setQuantity(dto.getQuantity());
        invoiceItem.setSubTotal(dto.getSubTotal());
        invoiceItem.setUnitPrice(dto.getUnitPrice());
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
        dto.setSubTotal(invoiceItem.getQuantity() * invoiceItem.getUnitPrice());
        if(invoiceItem.getInventory() != null){
            dto.setInventory(invoiceItem.getInventory() +"");
            dto.setInventory(invoiceItem.getInventory().getId());
            if(invoiceItem.getInventory().getProduct() != null){
                dto.setProductName(invoiceItem.getInventory().getProduct().getProductName());
                dto.setProductId(invoiceItem.getInventory().getProduct().getId());
            }
        }
        dto.setProformaInvoice(invoiceItem.getProformaInvoice()  != null ? invoiceItem.getProformaInvoice().getQuotationNumber() : null);
        dto.setProformaInvoiceId(invoiceItem.getProformaInvoice() != null ? invoiceItem.getProformaInvoice().getId() : null);
        return dto;
    }
}
