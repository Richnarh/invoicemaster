/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.khoders.admin.services;

import com.khoders.admin.mapper.AppParam;
import com.khoders.invoicemaster.dto.PaymentDataDto;
import com.khoders.invoicemaster.entities.PaymentData;
import com.khoders.invoicemaster.enums.DeliveryStatus;
import com.khoders.invoicemaster.mapper.PaymentMapper;
import com.khoders.invoicemaster.service.PaymentService;
import com.khoders.resource.enums.PaymentStatus;
import com.khoders.resource.jpa.CrudApi;
import com.khoders.resource.utilities.DateRangeUtil;
import com.khoders.resource.utilities.DateUtil;
import com.khoders.resource.utilities.Msg;
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
public class TransactionService {
    @Inject private CrudApi crudApi;
    @Inject private PaymentMapper mapper;
    @Inject private PaymentService paymentService;
    
    public List<PaymentDataDto> paymentDataList(AppParam param){
        double totalAmount = 0.0;
        List<PaymentDataDto> dtoList = new LinkedList<>();
        
        PaymentStatus paymentStatus = PaymentStatus.resolve(param.getPaymentStatus());
        LocalDate fromDate = DateUtil.parseLocalDate(param.getFromDate(), Pattern._yyyyMMdd);
        LocalDate toDate = DateUtil.parseLocalDate(param.getToDate(), Pattern._yyyyMMdd);
        DateRangeUtil dateRange = new DateRangeUtil(fromDate, toDate);
        
        List<PaymentData> paymentList = paymentService.getInvoiceTransaction(paymentStatus, dateRange);
        
        PaymentDataDto dto = null;
        for (PaymentData data : paymentList) {
            if (data.getProformaInvoice() != null) {
                totalAmount += data.getProformaInvoice().getTotalAmount();
                dto = mapper.toDto(data);
                dto.setTotalAmount(totalAmount);
                dtoList.add(dto);
            }
        }
        return dtoList;
    }

    public String updateStatus(String paymentStatus, String paymentDataId) {
        PaymentData data = crudApi.find(PaymentData.class, paymentDataId);
        data.setPaymentStatus(PaymentStatus.resolve(paymentStatus));
        if(crudApi.save(data) != null){
            return Msg.SUCCESS_MESSAGE;
        }
        return null;
    }
    public boolean delete(String paymentDataId){
        PaymentData paymentData = paymentService.getPaymentData(paymentDataId);
        return paymentData != null ? crudApi.delete(paymentData) : false;
    }

    public PaymentDataDto findById(String paymentDataId) {
        PaymentData paymentData = paymentService.getPaymentData(paymentDataId);
        return mapper.toDto(paymentData);
    }

    public List<PaymentDataDto> deliveryList(String deliveryStatus) {
        List<PaymentDataDto> dtoList = new LinkedList<>();
        if(deliveryStatus == null) return dtoList;
        List<PaymentData> paymentList = paymentService.getInvoiceByDeliveryStatus(DeliveryStatus.valueOf(deliveryStatus));
        paymentList.forEach(item ->{
            dtoList.add(mapper.toDto(item));
        });
        return dtoList;
    }
}
