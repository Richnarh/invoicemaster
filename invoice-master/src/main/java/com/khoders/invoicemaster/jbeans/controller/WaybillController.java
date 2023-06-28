/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.khoders.invoicemaster.jbeans.controller;

import com.khoders.invoicemaster.DefaultService;
import com.khoders.invoicemaster.reportData.ProformaInvoiceDto;
import com.khoders.invoicemaster.reportData.ProformaInvoiceItemDto;
import com.khoders.invoicemaster.entities.PaymentData;
import com.khoders.invoicemaster.entities.ProformaInvoice;
import com.khoders.invoicemaster.entities.ProformaInvoiceItem;
import com.khoders.invoicemaster.enums.DeliveryStatus;
import com.khoders.invoicemaster.jbeans.ReportFiles;
import com.khoders.invoicemaster.listener.AppSession;
import com.khoders.invoicemaster.service.ProformaInvoiceService;
import com.khoders.resource.jpa.CrudApi;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 *
 * @author Pascal
 */
@Named(value = "waybillController")
@SessionScoped
public class WaybillController implements Serializable {
    @Inject CrudApi crudApi;
    @Inject AppSession appSession;
    @Inject ProformaInvoiceService proformaInvoiceService;
    @Inject private DefaultService ds;
    
    private List<PaymentData> deliveryList = new LinkedList<>();
    private String invoiceId;
    private ProformaInvoice proformaInvoice;
    
    public void searchInvoice(){
        proformaInvoice = ds.getInvoice(invoiceId);
        deliveryList = proformaInvoiceService.getPendingDeliveryInvoice(proformaInvoice);
    }
    
    public void clearSearch(){
       invoiceId = null;
       deliveryList = new LinkedList<>();
    }
    
    public void updateDeliveryStatus(){
        PaymentData data = deliveryList.get(0);
        data.setDeliveryStatus(DeliveryStatus.FULLY_DELIVERED);
        crudApi.save(data);
    }
    
    public void loadPending(){
        deliveryList = proformaInvoiceService.getPendingDeliveryInvoice();
    }

    public void generateWaybill() {

        List<ProformaInvoiceDto> proformaInvoiceDtoList = new LinkedList<>();

        List<ProformaInvoiceItemDto> invoiceItemDtoList = new LinkedList<>();

        List<ProformaInvoiceItem> invoiceItemList = proformaInvoiceService.getProformaInvoiceItemReceipt(proformaInvoice);

        ProformaInvoiceDto proformaInvoiceDto = new ProformaInvoiceDto();

        if (proformaInvoice.getClient() != null) {
            proformaInvoiceDto.setClientName(proformaInvoice.getClient().getClientName().toUpperCase());
            proformaInvoiceDto.setEmailAddress(proformaInvoice.getClient().getEmailAddress());
            proformaInvoiceDto.setAddress(proformaInvoice.getClient().getAddress().toUpperCase());
        }
        proformaInvoiceDto.setIssuedDate(proformaInvoice.getIssuedDate());
        proformaInvoiceDto.setQuotationNumber(proformaInvoice.getQuotationNumber());

        if (appSession.getCurrentUser().getCompanyBranch() != null) {
            proformaInvoiceDto.setTelephoneNo(appSession.getCurrentUser().getCompanyBranch().getTelephoneNo());
        }
        if (appSession.getCurrentUser().getCompanyBranch() != null) {
            proformaInvoiceDto.setBranchName(appSession.getCurrentUser().getCompanyBranch() + "");
        }
        if (appSession.getCurrentUser().getCompanyBranch() != null) {
            proformaInvoiceDto.setGpsAddress(appSession.getCurrentUser().getCompanyBranch().getGpsAddress());
        }
        if (appSession.getCurrentUser().getCompanyBranch() != null) {
            proformaInvoiceDto.setWebsite(appSession.getCurrentUser().getCompanyBranch().getCompanyProfile().getWebsite());
        }
        if (appSession.getCurrentUser().getCompanyBranch() != null) {
            proformaInvoiceDto.setTinNo(appSession.getCurrentUser().getCompanyBranch().getCompanyProfile().getTinNo());
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

            if (appSession.getCurrentUser().getFrame() != null) {
                invoiceItemDto.setFrameUnit(appSession.getCurrentUser().getFrame().getLabel());
            }

            if (appSession.getCurrentUser().getWidth() != null) {
                invoiceItemDto.setWidthHeightUnits(appSession.getCurrentUser().getWidth().getLabel());
            }

            invoiceItemDtoList.add(invoiceItemDto);
        }
        proformaInvoiceDto.setInvoiceItemList(invoiceItemDtoList);

        proformaInvoiceDtoList.add(proformaInvoiceDto);

        try {
            Map<String, Object> reportParams = new HashMap<>();

            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(proformaInvoiceDtoList);
            InputStream stream = getClass().getResourceAsStream(ReportFiles.WAYBILL_FILE);

            reportParams.put("logo", ReportFiles.LOGO);

            JasperPrint jasperPrint = JasperFillManager.fillReport(stream, reportParams, dataSource);
            HttpServletResponse httpServletResponse = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
            httpServletResponse.setContentType("application/pdf");
            ServletOutputStream outputStream = httpServletResponse.getOutputStream();
            JasperExportManager.exportReportToPdfStream(jasperPrint, outputStream);
            FacesContext.getCurrentInstance().responseComplete();

        } catch (IOException | JRException e) {
            e.printStackTrace();
        }
        
        updateDeliveryStatus();
    }

    public List<PaymentData> getDeliveryList() {
        return deliveryList;
    }

    public String getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(String invoiceId) {
        this.invoiceId = invoiceId;
    }
    
}
