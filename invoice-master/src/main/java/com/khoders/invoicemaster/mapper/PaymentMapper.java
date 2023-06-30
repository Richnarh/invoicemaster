/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.khoders.invoicemaster.mapper;

import com.khoders.invoicemaster.dto.PaymentDataDto;
import com.khoders.invoicemaster.entities.PaymentData;
import com.khoders.resource.utilities.DateUtil;
import com.khoders.resource.utilities.Pattern;

/**
 *
 * @author Pascal
 */
public class PaymentMapper {
    
    public PaymentDataDto toDto(PaymentData data){
        PaymentDataDto dto = new PaymentDataDto();
        if(data.getId() == null) return null;
        dto.setId(data.getId());
        dto.setDeliveryStatus(data.getDeliveryStatus().getLabel());
        dto.setPaymentMethod(data.getPaymentMethod().getLabel());
        dto.setPaymentStatus(data.getPaymentStatus().getLabel());
        dto.setPaymentMessage(data.isPaymentMessage());
        dto.setPartialAmountPaid(data.getPartialAmountPaid());
        dto.setPaymentCode(data.getPaymentCode());
        dto.setProformaInvoice(data.getProformaInvoice() != null ? data.getProformaInvoice().getQuotationNumber() : null);
        dto.setProformaInvoiceId(data.getProformaInvoice() != null ? data.getProformaInvoice().getId() : null);
        dto.setValueDate(DateUtil.parseLocalDateString(data.getValueDate(), Pattern._ddMMyyyy));
        return dto;
    }
}
