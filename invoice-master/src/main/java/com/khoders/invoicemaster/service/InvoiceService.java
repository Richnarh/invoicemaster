/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.khoders.invoicemaster.service;

import com.khoders.admin.mapper.AppParam;
import com.khoders.invoicemaster.DefaultService;
import com.khoders.invoicemaster.dto.InvoiceDto;
import com.khoders.invoicemaster.dto.InvoiceItemDto;
import com.khoders.invoicemaster.dto.ReverseData;
import com.khoders.invoicemaster.entities.ProformaInvoice;
import com.khoders.invoicemaster.entities.ProformaInvoiceItem;
import com.khoders.invoicemaster.entities.UserAccount;
import com.khoders.invoicemaster.entities.system.CompanyBranch;
import com.khoders.invoicemaster.mapper.InvoiceMapper;
import com.khoders.resource.jpa.CrudApi;
import com.khoders.resource.utilities.DateRangeUtil;
import com.khoders.resource.utilities.DateUtil;
import com.khoders.resource.utilities.Pattern;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Pascal
 */
@Stateless
public class InvoiceService {
    private static final Logger log = LoggerFactory.getLogger(InvoiceService.class);
    @Inject private CrudApi crudApi;
    @Inject private AppService as;
    @Inject private InvoiceMapper mapper;
    @Inject private ProformaInvoiceService invoiceService;
    @Inject private DefaultService ds;
    
    public InvoiceDto findByInvoiceNo(String invoiceNo){
        ProformaInvoice invoice = ds.getInvoice(invoiceNo);
        if(invoice == null) return null;
        return mapper.toDto(invoice);
    }

    public String reverseInvoice(String invoiceId) {
        ProformaInvoice proformaInvoice = crudApi.find(ProformaInvoice.class, invoiceId);
        boolean paymentData = ds.deletePaymentData(proformaInvoice);
        boolean taxInfo = ds.deleteSalesTax(proformaInvoice);
        boolean saleItem = ds.deleteSaleItem(proformaInvoice);
        
        ReverseData reverseData = new ReverseData();
        boolean reversed = false;
        if(paymentData){
            reverseData.setPaymentData("Payment data and delivery info cleared!");
            reversed = true;
            log.info("Payment data and delivery info cleared!");
        }
        if(taxInfo){
            reverseData.setTaxInfo("Tax info cleared");
            reversed = true;
            log.info("Tax info cleared");
        }
        if(saleItem){
            reverseData.setSaleItem("Invoice item info cleared and inventory quantity updated!");
            reversed = true;
            log.info("Invoice item info cleared and inventory quantity updated!");
        }
        proformaInvoice.setConverted(false);
        crudApi.save(proformaInvoice);
        
        if(reversed){
            return "Invoice reverse successful!";
        }else{
            return "No item to reverse";
        }
    }

    public List<InvoiceDto> searchByDate(AppParam param) {
        LocalDate fromDate = DateUtil.parseLocalDate(param.getFromDate(), Pattern._yyyyMMdd);
        LocalDate toDate = DateUtil.parseLocalDate(param.getToDate(), Pattern._yyyyMMdd);
        DateRangeUtil dateRange = new DateRangeUtil(fromDate, toDate);
        List<ProformaInvoice> proformaInvoiceList = invoiceService.getInvoiceByDates(dateRange);
        
        List<InvoiceDto> dtoList = new LinkedList<>();
        proformaInvoiceList.forEach(item -> {
            dtoList.add(mapper.toDto(item));
        });
        return dtoList;
    }
    public List<InvoiceDto> searchByBranch(String branchId) {
        CompanyBranch branch = as.getBranch(branchId);
        List<ProformaInvoice> proformaInvoiceList = invoiceService.getInvoiceByBranch(branch);
        List<InvoiceDto> dtoList = new LinkedList<>();
        proformaInvoiceList.forEach(item -> {
            dtoList.add(mapper.toDto(item));
        });
        return dtoList;
    }
    public List<InvoiceDto> searchByUser(String userAccountId) {
        UserAccount userAccount = as.getUser(userAccountId);
        List<ProformaInvoice> proformaInvoiceList = invoiceService.getInvoiceByEmployee(userAccount);
        List<InvoiceDto> dtoList = new LinkedList<>();
        proformaInvoiceList.forEach(item -> {
            dtoList.add(mapper.toDto(item));
        });
        return dtoList;
    }
    public List<InvoiceItemDto> salesDetails(String invoiceId) {
        ProformaInvoice proformaInvoice = ds.getInvoiceById(invoiceId);
        List<InvoiceItemDto> dtoList = new LinkedList<>();
        List<ProformaInvoiceItem> invoiceItemList = invoiceService.getProformaInvoiceItemReceipt(proformaInvoice);
        invoiceItemList.forEach(item ->{
            dtoList.add(mapper.toDto(item));
        });
        return dtoList;
    }

    public List<InvoiceDto> searchByParam(String branchId, String userId) {
        List<ProformaInvoice> proformaInvoiceList = invoiceService.getInvoiceList(as.getBranch(branchId), as.getUser(userId));
        List<InvoiceDto> dtoList = new LinkedList<>();
        proformaInvoiceList.forEach(item -> {
            dtoList.add(mapper.toDto(item));
        });
        return dtoList;
    }
}
