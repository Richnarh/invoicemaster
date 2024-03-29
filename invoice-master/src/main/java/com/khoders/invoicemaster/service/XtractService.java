/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.khoders.invoicemaster.service;

import com.khoders.invoicemaster.reportData.ProformaInvoiceDto;
import com.khoders.invoicemaster.reportData.ProformaInvoiceItemDto;
import com.khoders.invoicemaster.reportData.Receipt;
import com.khoders.invoicemaster.reportData.SalesTaxDto;
import com.khoders.invoicemaster.entities.Inventory;
import com.khoders.invoicemaster.entities.ProformaInvoice;
import com.khoders.invoicemaster.entities.ProformaInvoiceItem;
import com.khoders.invoicemaster.entities.ReturnGood;
import com.khoders.invoicemaster.entities.SalesTax;
import com.khoders.invoicemaster.listener.AppSession;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author richa
 */
@Stateless
public class XtractService
{
    @Inject private AppSession appSession;
    @Inject private ProformaInvoiceService proformaInvoiceService;
    
    public Inventory extractReturnGoodToInventory(ReturnGood returnGood)
    {
        Inventory inventory=null;
        
        inventory.setProduct(returnGood.getInventory().getProduct());
        
        return inventory;
    }
    
    public Receipt extractToReceipt(ProformaInvoice proformaInvoice)
    {
        Receipt receipt = new Receipt();
        
        List<SalesTax> salesTaxesList  = proformaInvoiceService.getSalesTaxList(proformaInvoice);
        
        double totalTax = salesTaxesList.stream().mapToDouble(SalesTax::getTaxAmount).sum();
        double invoiceValue = totalTax + proformaInvoice.getTotalAmount() + proformaInvoice.getInstallationFee();
        
        if (proformaInvoice.getUserAccount().getCompanyBranch() != null)
        {
            receipt.setBranchName(proformaInvoice.getUserAccount().getCompanyBranch() + "");
        }
        if (proformaInvoice.getUserAccount().getCompanyBranch() != null)
        {
            receipt.setWebsite(proformaInvoice.getUserAccount().getCompanyBranch().getCompanyProfile().getWebsite());
        }

        receipt.setReceiptNumber(proformaInvoice.getQuotationNumber());
        receipt.setTotalTax(totalTax);
        receipt.setInstallationFee(proformaInvoice.getInstallationFee());
        receipt.setTotalAmount(proformaInvoice.getTotalAmount());
        receipt.setDate(LocalDateTime.now());
        receipt.setTotalPayable(invoiceValue);
        try
        {
            receipt.setModeOfPayment(proformaInvoice.getModeOfPayment().getLabel());
        } catch (Exception e)
        {
        }
        
        return receipt;
    }
    
    public ProformaInvoiceDto extractToProformaInvoice(ProformaInvoice proformaInvoice)
    {
        ProformaInvoiceDto proformaInvoiceDto = new ProformaInvoiceDto();
        
        List<ProformaInvoiceItem> invoiceItemList  = proformaInvoiceService.getProformaInvoiceItemReceipt(proformaInvoice);
        List<SalesTax> salesTaxesList  = proformaInvoiceService.getSalesTaxList(proformaInvoice);
        
        List<ProformaInvoiceItemDto> invoiceItemDtoList = new LinkedList<>();
        List<SalesTaxDto> salesTaxs = new LinkedList<>();
        
        if(proformaInvoice.getClient() != null)
        {
          proformaInvoiceDto.setClientName(proformaInvoice.getClient().getClientName().toUpperCase());
          proformaInvoiceDto.setEmailAddress(proformaInvoice.getClient().getEmailAddress());
          proformaInvoiceDto.setAddress(proformaInvoice.getClient().getAddress().toUpperCase());  
        }
        proformaInvoiceDto.setIssuedDate(proformaInvoice.getIssuedDate());
        proformaInvoiceDto.setExpiryDate(proformaInvoice.getExpiryDate());
        proformaInvoiceDto.setQuotationNumber(proformaInvoice.getQuotationNumber());
        proformaInvoiceDto.setDescription(proformaInvoice.getDescription());
        proformaInvoiceDto.setTotalAmount(proformaInvoice.getTotalAmount());
        proformaInvoiceDto.setSubTotalAmount(proformaInvoice.getSubTotalAmount());

        double sTaxAmount = salesTaxesList.stream().mapToDouble(SalesTax::getTaxAmount).sum();

        double invoiceValue = sTaxAmount + proformaInvoice.getTotalAmount() + proformaInvoice.getInstallationFee();

        proformaInvoiceDto.setInstallationFee(proformaInvoice.getInstallationFee());
        proformaInvoiceDto.setTotalDiscount(proformaInvoice.getDiscountRate());
        proformaInvoiceDto.setTotalPayable(invoiceValue);
        proformaInvoiceDto.setExtraDiscount(!salesTaxesList.isEmpty() && salesTaxesList.get(0) != null && salesTaxesList.get(0).getSaleLead() != null ? salesTaxesList.get(0).getSaleLead().getRate() : 0);

        if(proformaInvoice.getUserAccount() != null){
            if (proformaInvoice.getUserAccount().getCompanyBranch() != null)
            {
                proformaInvoiceDto.setTelephoneNo(proformaInvoice.getUserAccount().getCompanyBranch().getTelephoneNo());
            }
            if (proformaInvoice.getUserAccount().getCompanyBranch() != null)
            {
                proformaInvoiceDto.setBranchName(proformaInvoice.getUserAccount().getCompanyBranch() + "");
            }
            if (proformaInvoice.getUserAccount().getCompanyBranch() != null)
            {
                proformaInvoiceDto.setGpsAddress(proformaInvoice.getUserAccount().getCompanyBranch().getGpsAddress());
            }
            if (proformaInvoice.getUserAccount().getCompanyBranch() != null)
            {
                proformaInvoiceDto.setWebsite(proformaInvoice.getUserAccount().getCompanyBranch().getCompanyProfile().getWebsite());
            }
            if (proformaInvoice.getUserAccount().getCompanyBranch() != null)
            {
                proformaInvoiceDto.setTinNo(proformaInvoice.getUserAccount().getCompanyBranch().getCompanyProfile().getTinNo());
            }
        }
        for (SalesTax salesTax : salesTaxesList)
        {
            SalesTaxDto taxItem = new SalesTaxDto();
            taxItem.setTaxName(salesTax.getTaxName());
            taxItem.setTaxRate(salesTax.getTaxRate());
            taxItem.setTaxAmount(salesTax.getTaxAmount());

            salesTaxs.add(taxItem);
        }
        
        for (ProformaInvoiceItem invoiceItem : invoiceItemList)
        {
            ProformaInvoiceItemDto invoiceItemDto = new ProformaInvoiceItemDto();
           
                if (invoiceItem.getInventory() != null && invoiceItem.getInventory().getProduct() != null)
                {
                    try
                    {
                        byte[] image = invoiceItem.getInventory().getProduct().getProductImage();
                        if (image != null)
                        {
                            InputStream imageStream = new ByteArrayInputStream(image);
                            invoiceItemDto.setProductImage(imageStream);
                        }
                    } catch (Exception e)
                    {
                        e.printStackTrace();
                    }

                    invoiceItemDto.setProductCode(invoiceItem.getInventory().getProduct().getProductCode());
                    invoiceItemDto.setDescription(invoiceItem.getInventory().getProduct().getDescription());
                }
                invoiceItemDto.setFrameSize(invoiceItem.getInventory().getFrameSize());
                invoiceItemDto.setWidth(invoiceItem.getInventory().getWidth());
                invoiceItemDto.setHeight(invoiceItem.getInventory().getHeight());
            
            invoiceItemDto.setQuantity(invoiceItem.getQuantity());
            invoiceItemDto.setUnitPrice(invoiceItem.getUnitPrice());
            invoiceItemDto.setTotalAmount(invoiceItem.getSubTotal());
            if(proformaInvoice.getUserAccount() != null){
                if(proformaInvoice.getUserAccount().getFrame() != null)
                {
                    invoiceItemDto.setFrameUnit(proformaInvoice.getUserAccount().getFrame().getLabel());
                }

                if(proformaInvoice.getUserAccount().getWidth() != null)
                {
                    invoiceItemDto.setWidthHeightUnits(proformaInvoice.getUserAccount().getWidth().getLabel());
                }
            }
            invoiceItemDtoList.add(invoiceItemDto);
        }
            proformaInvoiceDto.setInvoiceItemList(invoiceItemDtoList);
            proformaInvoiceDto.setTaxList(salesTaxs);
            
        return proformaInvoiceDto;
    }
    
    public ProformaInvoiceDto extractToProformaInvoiceCover(ProformaInvoice proformaInvoice)
    {
        ProformaInvoiceDto proformaInvoiceDto = new ProformaInvoiceDto();
        
        proformaInvoiceDto.setClientName(proformaInvoice.getClient().getClientName());
        proformaInvoiceDto.setAddress(proformaInvoice.getClient().getAddress());
        proformaInvoiceDto.setIssuedDate(proformaInvoice.getIssuedDate());
        proformaInvoiceDto.setQuotationNumber(proformaInvoice.getQuotationNumber());
        
        if(proformaInvoice.getUserAccount() != null)
        {
            if (proformaInvoice.getUserAccount().getCompanyBranch() != null)
            {
                proformaInvoiceDto.setTelephoneNo(proformaInvoice.getUserAccount().getCompanyBranch().getTelephoneNo());
            }
            if (proformaInvoice.getUserAccount().getCompanyBranch() != null)
            {
                proformaInvoiceDto.setBranchName(proformaInvoice.getUserAccount().getCompanyBranch() + "");
            }
            if (proformaInvoice.getUserAccount().getCompanyBranch() != null)
            {
                proformaInvoiceDto.setGpsAddress(proformaInvoice.getUserAccount().getCompanyBranch().getGpsAddress());
            }
            if (proformaInvoice.getUserAccount().getCompanyBranch() != null)
            {
                proformaInvoiceDto.setWebsite(proformaInvoice.getUserAccount().getCompanyBranch().getCompanyProfile().getWebsite());
            }
            if (proformaInvoice.getUserAccount().getCompanyBranch() != null)
            {
                proformaInvoiceDto.setTinNo(proformaInvoice.getUserAccount().getCompanyBranch().getCompanyProfile().getTinNo());
            }
            if (proformaInvoice.getUserAccount().getCompanyBranch() != null)
            {
                proformaInvoiceDto.setEmailAddress(proformaInvoice.getUserAccount().getCompanyBranch().getCompanyProfile().getCompanyEmail());
            }
        }
        return proformaInvoiceDto;
    }
}
