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
    
    public InvoiceDto findById(String invoiceId){
        ProformaInvoice invoice = ds.getInvoice(invoiceId);
        return mapper.toDto(invoice);
    }

    public ReverseData reverseInvoice(String invoiceId) {
        ProformaInvoice proformaInvoice = ds.getInvoice(invoiceId);
        boolean paymentData = ds.deletePaymentData(proformaInvoice);
        boolean taxInfo = ds.deleteSalesTax(proformaInvoice);
        boolean saleItem = ds.deleteSaleItem(proformaInvoice);
        
        ReverseData reverseData = new ReverseData();
        
        if(paymentData){
            reverseData.setPaymentData("Payment data and delivery info cleared!");
            log.info("Payment data and delivery info cleared!");
        }
        if(taxInfo){
            reverseData.setTaxInfo("Tax info cleared");
            log.info("Tax info cleared");
        }
        if(saleItem){
            reverseData.setSaleItem("Invoice item info cleared and inventory quantity updated!");
            log.info("Invoice item info cleared and inventory quantity updated!");
        }
        proformaInvoice.setConverted(false);
        crudApi.save(proformaInvoice);
        return reverseData;
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
        ProformaInvoice proformaInvoice = ds.getInvoice(invoiceId);
        List<InvoiceItemDto> dtoList = new LinkedList<>();
        List<ProformaInvoiceItem> invoiceItemList = invoiceService.getProformaInvoiceItemReceipt(proformaInvoice);
        invoiceItemList.forEach(item ->{
            dtoList.add(mapper.toDto(item));
        });
        return dtoList;
    }
}
