/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.khoders.invoicemaster.mapper;

import com.khoders.admin.mapper.AppParam;
import com.khoders.invoicemaster.DefaultService;
import com.khoders.invoicemaster.dto.InvoiceDto;
import com.khoders.invoicemaster.dto.InvoiceItemDto;
import com.khoders.invoicemaster.dto.SalesTaxDto;
import com.khoders.invoicemaster.entities.Client;
import com.khoders.invoicemaster.entities.Inventory;
import com.khoders.invoicemaster.entities.ProformaInvoice;
import com.khoders.invoicemaster.entities.ProformaInvoiceItem;
import com.khoders.invoicemaster.entities.SalesTax;
import com.khoders.invoicemaster.service.AppService;
import com.khoders.resource.exception.DataNotFoundException;
import com.khoders.resource.jpa.CrudApi;
import com.khoders.resource.utilities.DateUtil;
import com.khoders.resource.utilities.Pattern;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author Pascal
 */
@Stateless
public class InvoiceMapper {
    @Inject private CrudApi crudApi;
    @Inject private AppService as;
    @Inject private DefaultService ds;
    
    public ProformaInvoice toEntity(InvoiceDto dto, AppParam param){
        ProformaInvoice invoice = new ProformaInvoice();
        if (dto.getId() != null){
            invoice.setId(dto.getId());
        }
        if(dto.getClientId() == null){
            throw new DataNotFoundException("Client Id is required");
        }
        Client client = crudApi.find(Client.class, dto.getClientId());
        if(client != null){
            invoice.setClient(client);
        }
        System.out.println("IssueDate: "+dto.getIssuedDate());
        System.out.println("ExpiredDate: "+dto.getExpiryDate());
        invoice.setDescription(dto.getDescription());
        invoice.setDiscountRate(dto.getDiscountRate());
        invoice.setExpiryDate(LocalDate.parse(dto.getExpiryDate()));
        invoice.setIssuedDate(LocalDate.parse(dto.getExpiryDate()));
        invoice.setInstallationFee(dto.getInstallationFee());
        invoice.setModeOfPayment(dto.getModeOfPayment());
//        invoice.setQuotationNumber(dto.getQuotationNumber());
        invoice.setSubTotalAmount(dto.getSubTotalAmount());
        invoice.setTotalAmount(dto.getTotalAmount());
        invoice.setUserAccount(as.getUser(param.getUserAccountId()));
        invoice.setCompanyBranch(as.getBranch(param.getCompanyBranchId()));
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
        dto.setModeOfPayment(invoice.getModeOfPayment());
        dto.setSubTotalAmount(invoice.getSubTotalAmount());
        dto.setTotalAmount(invoice.getTotalAmount());
        if(invoice.getClient() != null){
            dto.setClient(invoice.getClient()+"");
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
        Inventory inventory = ds.getInventory(dto.getInventoryId());
        if(inventory != null){
            invoiceItem.setInventory(inventory);
        }
        ProformaInvoice invoice = crudApi.find(ProformaInvoice.class, dto.getProformaInvoiceId());
        if(invoice != null){
            invoiceItem.setProformaInvoice(invoice);
        }
        invoiceItem.setQuantity(dto.getQuantity());
        invoiceItem.setSubTotal(dto.getQuantity() * dto.getUnitPrice());
        invoiceItem.setUnitPrice(dto.getUnitPrice());
        return invoiceItem;
    }
    public List<ProformaInvoiceItem> toEntity(List<InvoiceItemDto> dtoList){
        List<ProformaInvoiceItem> invoiceItemList = new LinkedList<>();
        for (InvoiceItemDto dto : dtoList) {
            invoiceItemList.add(toEntity(dto));
        }
        return invoiceItemList;
    }
    
    public InvoiceItemDto toDto(ProformaInvoiceItem invoiceItem){
        InvoiceItemDto dto = new InvoiceItemDto();
        if(invoiceItem.getId() == null) return null;
        dto.setId(invoiceItem.getId());
        dto.setDescription(invoiceItem.getDescription());
        dto.setItemCode(invoiceItem.getItemCode());
        dto.setQuantity(invoiceItem.getQuantity());
        dto.setUnitPrice(invoiceItem.getUnitPrice());
        dto.setSubTotal(dto.getSubTotal());
        if(invoiceItem.getInventory() != null){
            dto.setInventory(invoiceItem.getInventory() +"");
            dto.setInventoryId(invoiceItem.getInventory().getId());
            if(invoiceItem.getInventory().getProduct() != null){
                dto.setProductName(invoiceItem.getInventory().getProduct().getProductName());
                dto.setProductId(invoiceItem.getInventory().getProduct().getId());
            }
        }
        dto.setProformaInvoice(invoiceItem.getProformaInvoice()  != null ? invoiceItem.getProformaInvoice().getQuotationNumber() : null);
        dto.setProformaInvoiceId(invoiceItem.getProformaInvoice() != null ? invoiceItem.getProformaInvoice().getId() : null);
        return dto;
    }
    
    public List<InvoiceItemDto> toDto(List<ProformaInvoiceItem> invoiceItemList) {
        List<InvoiceItemDto> dtoList = new LinkedList<>();
        invoiceItemList.forEach(invoiceItem -> {
            dtoList.add(toDto(invoiceItem));
        });
        return dtoList;
    }
    
    public List<ProformaInvoiceItem> toEntity(List<InvoiceItemDto> invoiceItemDtoList, AppParam param){
        List<ProformaInvoiceItem> invoiceItemList = new LinkedList<>();
        for (InvoiceItemDto dto : invoiceItemDtoList) {
            ProformaInvoiceItem invoiceItem = new ProformaInvoiceItem();
            if (dto.getId() != null) {
                invoiceItem.setId(dto.getId());
            }
            invoiceItem.setCompanyBranch(as.getBranch(param.getCompanyBranchId()));
            invoiceItem.setUserAccount(as.getUser(param.getUserAccountId()));
            invoiceItem.setDescription(dto.getDescription());
            invoiceItem.setItemCode(dto.getItemCode());
            invoiceItem.setQuantity(dto.getQuantity());
            invoiceItem.setUnitPrice(dto.getUnitPrice());
            invoiceItem.setSubTotal(dto.getQuantity() * dto.getUnitPrice());
            if(dto.getInventoryId() == null){
                throw new DataNotFoundException("InventoryId cannot be null");
            }
            if(dto.getProformaInvoiceId() == null){
                throw new DataNotFoundException("ProformaInvoiceId cannot be null");
            }
            Inventory inventory = crudApi.find(Inventory.class, dto.getInventoryId());
            if(inventory != null)
                invoiceItem.setInventory(inventory);
            
            ProformaInvoice invoice = ds.getInvoiceById(dto.getProformaInvoiceId());
            if(invoice != null)
                invoiceItem.setProformaInvoice(invoice);
            
            invoiceItemList.add(invoiceItem);
        }
        return invoiceItemList;
    }

    public SalesTaxDto toSalesTaxEntity(SalesTaxDto dto) {
        SalesTax salesTax = new SalesTax();
        if(dto.getId() != null){
            salesTax.setId(dto.getId());
        }
        salesTax.setId(dto.getId());
        salesTax.setSalesTaxId(dto.getSalesTaxId());
        salesTax.setTaxName(dto.getTaxName());
        salesTax.setTaxRate(dto.getTaxRate());
        salesTax.setTaxAmount(dto.getTaxAmount());
        salesTax.setReOrder(dto.getReOrder());
        if (dto.getProformaInvoiceId() == null) {
            throw new DataNotFoundException("ProformaInvoiceId cannot be null");
        }
        ProformaInvoice invoice = ds.getInvoiceById(dto.getProformaInvoiceId());
        if(invoice != null)
            salesTax.setProformaInvoice(invoice);

        return dto;
    }
    
    public SalesTaxDto toSalesTaxDto(SalesTax salesTax) {
        SalesTaxDto dto = new SalesTaxDto();
        if(salesTax.getId() == null) return null;
        dto.setId(salesTax.getId());
        dto.setSalesTaxId(salesTax.getSalesTaxId());
        dto.setTaxName(salesTax.getTaxName());
        dto.setTaxRate(salesTax.getTaxRate());
        dto.setTaxAmount(salesTax.getTaxAmount());
        dto.setReOrder(salesTax.getReOrder());
        if(salesTax.getProformaInvoice() != null){
            dto.setProformaInvoice(salesTax.getProformaInvoice().getQuotationNumber());
            dto.setProformaInvoiceId(salesTax.getProformaInvoice().getId());
        }
        if(salesTax.getSaleLead() != null){
            dto.setSaleLead(salesTax.getSaleLead().getSurname() +" - "+salesTax.getSaleLead().getLeadCode());
            dto.setSaleLeadId(salesTax.getSaleLead().getId());
        }
        return dto;
    }
    
    public List<SalesTaxDto> toSalesTaxDto(List<SalesTax> salesTaxList) {
        List<SalesTaxDto> dtoList = new LinkedList<>();
        salesTaxList.forEach(salesTax -> {
            dtoList.add(toSalesTaxDto(salesTax));
        });
        return dtoList;
    }
}
