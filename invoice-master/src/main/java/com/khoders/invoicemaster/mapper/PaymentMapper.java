/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.khoders.invoicemaster.mapper;

import com.khoders.invoicemaster.DefaultService;
import com.khoders.invoicemaster.dto.PaymentDataDto;
import com.khoders.invoicemaster.entities.PaymentData;
import com.khoders.invoicemaster.entities.ProformaInvoice;
import com.khoders.invoicemaster.entities.ProformaInvoiceItem;
import com.khoders.invoicemaster.entities.UserAccount;
import com.khoders.invoicemaster.reportData.ProformaInvoiceDto;
import com.khoders.invoicemaster.reportData.ProformaInvoiceItemDto;
import com.khoders.invoicemaster.service.AppService;
import com.khoders.invoicemaster.service.PaymentService;
import com.khoders.invoicemaster.service.ProformaInvoiceService;
import com.khoders.resource.jpa.CrudApi;
import com.khoders.resource.utilities.DateUtil;
import com.khoders.resource.utilities.Pattern;
import java.util.LinkedList;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author Pascal
 */
@Stateless
public class PaymentMapper {
    @Inject private CrudApi crudApi;
    @Inject private DefaultService ds;
    @Inject private AppService appService;
    @Inject private ProformaInvoiceService invoiceService;
    @Inject private PaymentService paymentService;
    
    public PaymentDataDto toDto(PaymentData data){
        PaymentDataDto dto = new PaymentDataDto();
        if(data.getId() == null) return null;
        dto.setId(data.getId());
        dto.setDeliveryStatus(data.getDeliveryStatus() != null ? data.getDeliveryStatus().getLabel() : null);
        dto.setPaymentMethod(data.getPaymentMethod() != null ? data.getPaymentMethod().getLabel() : null);
        dto.setPaymentStatus(data.getPaymentStatus() != null ? data.getPaymentStatus().getLabel() : null);
        dto.setPaymentMessage(data.isPaymentMessage());
        dto.setPartialAmountPaid(data.getPartialAmountPaid());
        dto.setPaymentCode(data.getPaymentCode());
        if(data.getProformaInvoice() != null){
            dto.setClientName(data.getProformaInvoice().getClient().getClientName());
            dto.setDeliveryAdress(data.getProformaInvoice().getClient().getAddress());
            dto.setClientId(data.getProformaInvoice().getClient().getId());
            dto.setProformaInvoice(data.getProformaInvoice().getQuotationNumber());
            dto.setProformaInvoiceId(data.getProformaInvoice().getId());
        }
        dto.setValueDate(DateUtil.parseLocalDateString(data.getValueDate(), Pattern.ddMMyyyy));
        return dto;
    }
    
    public List<ProformaInvoiceDto> xtractData(String paymentDataId) {

        List<ProformaInvoiceDto> proformaInvoiceDtoList = new LinkedList<>();

        List<ProformaInvoiceItemDto> invoiceItemDtoList = new LinkedList<>();
        
        PaymentData data = paymentService.getPaymentData(paymentDataId);
        ProformaInvoice proformaInvoice = crudApi.find(ProformaInvoice.class, data.getProformaInvoice().getId());
        UserAccount user = appService.getUser(data.getUserAccount().getId());
        List<ProformaInvoiceItem> invoiceItemList = invoiceService.getProformaInvoiceItemList(proformaInvoice);

        ProformaInvoiceDto proformaInvoiceDto = new ProformaInvoiceDto();

        if (proformaInvoice.getClient() != null) {
            proformaInvoiceDto.setClientName(proformaInvoice.getClient().getClientName().toUpperCase());
            proformaInvoiceDto.setEmailAddress(proformaInvoice.getClient().getEmailAddress());
            proformaInvoiceDto.setAddress(proformaInvoice.getClient().getAddress().toUpperCase());
        }
        proformaInvoiceDto.setIssuedDate(proformaInvoice.getIssuedDate());
        proformaInvoiceDto.setQuotationNumber(proformaInvoice.getQuotationNumber());

        if (user.getCompanyBranch() != null) {
            proformaInvoiceDto.setTelephoneNo(user.getCompanyBranch().getTelephoneNo());
        }
        if (user.getCompanyBranch() != null) {
            proformaInvoiceDto.setBranchName(user.getCompanyBranch() + "");
        }
        if (user.getCompanyBranch() != null) {
            proformaInvoiceDto.setGpsAddress(user.getCompanyBranch().getGpsAddress());
        }
        if (user.getCompanyBranch() != null) {
            proformaInvoiceDto.setWebsite(user.getCompanyBranch().getCompanyProfile().getWebsite());
        }
        if (user.getCompanyBranch() != null) {
            proformaInvoiceDto.setTinNo(user.getCompanyBranch().getCompanyProfile().getTinNo());
        }

        for (ProformaInvoiceItem invoiceItem : invoiceItemList) {
            ProformaInvoiceItemDto invoiceItemDto = new ProformaInvoiceItemDto();

            if (invoiceItem.getInventory() != null) {
                if (invoiceItem.getInventory().getProduct() != null) {
                    invoiceItemDto.setProductCode(invoiceItem.getInventory().getProduct().getProductCode());
                    invoiceItemDto.setProductName(invoiceItem.getInventory().getProduct().getProductName());
                    invoiceItemDto.setDescription(invoiceItem.getInventory().getProduct().getDescription());
                }
            }
            invoiceItemDto.setFrameSize(invoiceItem.getInventory().getFrameSize());
            invoiceItemDto.setWidth(invoiceItem.getInventory().getWidth());
            invoiceItemDto.setHeight(invoiceItem.getInventory().getHeight());
            invoiceItemDto.setQuantity(invoiceItem.getQuantity());
            invoiceItemDto.setUnitPrice(invoiceItem.getUnitPrice());

            if (user.getFrame() != null) {
                invoiceItemDto.setFrameUnit(user.getFrame().getLabel());
            }

            if (user.getWidth() != null) {
                invoiceItemDto.setWidthHeightUnits(user.getWidth().getLabel());
            }
            invoiceItemDtoList.add(invoiceItemDto);
        }
        proformaInvoiceDto.setInvoiceItemList(invoiceItemDtoList);
        proformaInvoiceDtoList.add(proformaInvoiceDto);
        return proformaInvoiceDtoList;
    }

}
