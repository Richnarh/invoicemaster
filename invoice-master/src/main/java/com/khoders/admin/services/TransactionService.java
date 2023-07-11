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
import com.khoders.invoicemaster.jbeans.ReportFiles;
import com.khoders.invoicemaster.mapper.PaymentMapper;
import com.khoders.invoicemaster.reportData.ProformaInvoiceDto;
import com.khoders.invoicemaster.service.PaymentService;
import com.khoders.resource.enums.PaymentStatus;
import com.khoders.resource.jpa.CrudApi;
import com.khoders.resource.reports.ReportManager;
import com.khoders.resource.utilities.DateRangeUtil;
import com.khoders.resource.utilities.DateUtil;
import com.khoders.resource.utilities.Msg;
import com.khoders.resource.utilities.Pattern;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import javax.ejb.Stateless;
import javax.inject.Inject;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 *
 * @author Pascal
 */
@Stateless
public class TransactionService {
    @Inject private CrudApi crudApi;
    @Inject private PaymentMapper mapper;
    @Inject private PaymentService paymentService;
    @Inject private ReportManager reportManager;
    
    public List<PaymentDataDto> paymentDataList(AppParam param){
        double totalAmount = 0.0;
        LocalDate fromDate=null,toDate=null;
        List<PaymentDataDto> dtoList = new LinkedList<>();
        
        PaymentStatus paymentStatus = PaymentStatus.resolve(param.getPaymentStatus());
        System.out.println("getPaymentStatus: "+param.getPaymentStatus());
        System.out.println("getFromDate: "+param.getFromDate());
        System.out.println("getToDate: "+param.getToDate());
        if(!"null".equals(param.getFromDate()))
            fromDate = DateUtil.parseLocalDate(param.getFromDate(), Pattern._yyyyMMdd);
        if(!"null".equals(param.getToDate()))
            toDate = DateUtil.parseLocalDate(param.getToDate(), Pattern._yyyyMMdd);
        
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

    public byte[] printReport(String paymentDataId) {
        List<ProformaInvoiceDto> dtoList = mapper.xtractData(paymentDataId);
        Map<String, Object> reportParams = new HashMap<>();
        reportParams.put("logo", ReportFiles.LOGO);
        return reportManager.createByteReport(dtoList, ReportFiles.WAYBILL_FILE, reportParams);
    }       
}
