/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.khoders.invoicemaster.controller;

import com.khoders.admin.mapper.AppParam;
import com.khoders.invoicemaster.ApiEndpoint;
import com.khoders.invoicemaster.DefaultService;
import com.khoders.invoicemaster.dto.InvoiceDto;
import com.khoders.invoicemaster.dto.InvoiceItemDto;
import com.khoders.invoicemaster.dto.PaymentDataDto;
import com.khoders.invoicemaster.dto.ReportData;
import com.khoders.invoicemaster.dto.SalesDto;
import com.khoders.invoicemaster.entities.PaymentData;
import com.khoders.invoicemaster.service.InvoiceService;
import com.khoders.invoicemaster.service.ProformaInvoiceService;
import com.khoders.resource.enums.PaymentStatus;
import com.khoders.resource.jaxrs.JaxResponse;
import com.khoders.resource.utilities.Msg;
import java.util.Base64;
import java.util.List;
import javax.inject.Inject;
import javax.ws.rs.BeanParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Pascal
 */
@Path(ApiEndpoint.INVOICE_ENDPOINT)
public class InvoiceController {
    private static final Logger log = LoggerFactory.getLogger(InvoiceController.class);
    @Inject private InvoiceService invoiceService;
    @Inject private ProformaInvoiceService pis;
    @Inject private DefaultService ds;
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response create(@BeanParam AppParam param,InvoiceDto invoiceDto){
        InvoiceDto dto = invoiceService.save(invoiceDto, param);
        return JaxResponse.created(Msg.CREATED, dto);
    }
    
    @POST
    @Path("/save-all")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response saveAll(@BeanParam AppParam param, SalesDto salesDto){
        PaymentData paymentData = pis.invoiceRecord(ds.getInvoiceById(salesDto.getId()));
        if(paymentData != null){
            if(paymentData.getPaymentStatus() == PaymentStatus.FULLY_PAID){
                return JaxResponse.error(Msg.FAILED, "This invoice cannot be saved again!");
            }
        }
        SalesDto dto = invoiceService.saveAll(salesDto, param);
        return JaxResponse.created(Msg.CREATED,dto);
    }
    
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public Response update(@BeanParam AppParam param, InvoiceDto invoiceDto){
        InvoiceDto dto = invoiceService.save(invoiceDto, param);
        return JaxResponse.created(Msg.UPDATED, dto);
    }
    
    @GET
    @Path("/list")
    @Produces(MediaType.APPLICATION_JSON)
    public Response findAll(){
        return JaxResponse.ok(Msg.RECORD_FOUND, invoiceService.findAll());
    }
    
    @DELETE
    @Path("/{invoiceId}")
    public Response delete(@PathParam("invoiceId") String invoiceId){
        boolean delete = invoiceService.delete(invoiceId);
        if(delete)
            return JaxResponse.ok(Msg.DELETE_MESSAGE,delete);
        return JaxResponse.ok("Could not delete inventory",delete);
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response search(@QueryParam("q") String q) throws Exception{
        InvoiceDto dto = invoiceService.findByInvoiceNo(q);
        if(dto == null)
            return JaxResponse.okNotFound(Msg.RECORD_NOT_FOUND, "Invoice with Id: "+q+" does not exist.");
        return JaxResponse.ok(dto);
    }
    
    @POST
    @Path("/{invoiceId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response reverseInvoice(@PathParam("invoiceId") String invoiceId){
        String data = invoiceService.reverseInvoice(invoiceId);
        return JaxResponse.ok(data);
    }
    
    @GET
    @Path("/{invoiceId}/manage")
    @Produces(MediaType.APPLICATION_JSON)
    public Response manage(@PathParam("invoiceId") String invoiceId){
        SalesDto manageInvoice = invoiceService.manageInvoice(invoiceId);
        return JaxResponse.ok(Msg.RECORD_FOUND, manageInvoice);
    }
    
    @GET
    @Path("/search")
    @Produces(MediaType.APPLICATION_JSON)
    public Response searchByDate(@BeanParam AppParam param){
        List<InvoiceDto> invoices = invoiceService.searchByDate(param);
        return JaxResponse.ok(invoices);
    }
    @GET
    @Path("/today")
    @Produces(MediaType.APPLICATION_JSON)
    public Response invoicesToday(@BeanParam AppParam param){
        List<InvoiceDto> invoices = invoiceService.invoicesToday(param);
        return JaxResponse.ok(invoices);
    }
    
    @GET
    @Path("/{invoiceId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response searchDetails(@PathParam("invoiceId") String invoiceId){
        List<InvoiceItemDto> itemDtos = invoiceService.salesDetails(invoiceId);
        return JaxResponse.ok(itemDtos);
    }
    @POST
    @Path("/{invoiceId}/payment-data")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createPaymentData(@BeanParam AppParam param,PaymentDataDto paymentData, @PathParam("invoiceId") String invoiceId){
        log.info("paymentData: {} invoiceId: {} ", paymentData,invoiceId);
        String result = invoiceService.savePaymentData(paymentData,param,invoiceId);
        return JaxResponse.ok(Msg.CREATED,result);
    }
    @GET
    @Path("/{invoiceId}/payment-data")
    public Response fetchPaymentInfo(@PathParam("invoiceId") String invoiceId){
        PaymentDataDto dataDto = invoiceService.getPaymentDataByInvoiceId(invoiceId);
        log.info("fetching payment info");
        return JaxResponse.ok(dataDto);
    }
    
    @GET
    @Path("/{invoiceId}/report")
    @Produces(MediaType.APPLICATION_JSON)
    public Response generateInvoice(@PathParam("invoiceId") String invoiceId){
        log.debug("reporting...");
        ReportData reportData = invoiceService.generateInvoice(invoiceId);
        return JaxResponse.ok(Msg.RECORD_FOUND, reportData);
    }
    @GET
    @Path("/{invoiceId}/receipt")
    @Produces(MediaType.APPLICATION_JSON)
    public Response generateReceipt(@PathParam("invoiceId") String invoiceId){
        log.debug("receipt...");
        byte[] reportByte = invoiceService.generateReceipt(invoiceId);
        return JaxResponse.ok(Msg.RECORD_FOUND, Base64.getEncoder().encodeToString(reportByte));
    }
    @GET
    @Path("/{invoiceId}/reverse")
    @Produces(MediaType.APPLICATION_JSON)
    public Response reverseApproval(@BeanParam AppParam param, @PathParam("invoiceId") String invoiceId){
        log.debug("reverse approval...");
        String result = invoiceService.reverseApproval(param,invoiceId);
        return JaxResponse.ok(Msg.RECORD_FOUND, result);
    }
    
    @GET
    @Path("/search/{branchId}")
    public Response searchByBranch(@PathParam("branchId") String branchId){
        List<InvoiceDto> invoices = invoiceService.searchByBranch(branchId);
        return JaxResponse.ok(invoices);
    }
    @GET
    @Path("search/{userId}/user")
    @Produces(MediaType.APPLICATION_JSON)
    public Response searchByUser(@PathParam("userId") String userId){
        List<InvoiceDto> invoices = invoiceService.searchByUser(userId);
        return JaxResponse.ok(invoices);
    }
    @GET
    @Path("/{branchId}/{userId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response searchByParam(@PathParam("branchId") String branchId, @PathParam("userId") String userId){
        List<InvoiceDto> invoices = invoiceService.searchByParam(branchId,userId);
        return JaxResponse.ok(invoices);
    }
    @GET
    @Path("/invoice-params")
    @Produces(MediaType.APPLICATION_JSON)
    public Response searchByParam(){
        return JaxResponse.ok(new InvoiceDto());
    }
}
