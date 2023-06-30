/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.khoders.admin.controller;

import com.khoders.admin.mapper.AppParam;
import com.khoders.invoicemaster.ApiEndpoint;
import com.khoders.invoicemaster.dto.InvoiceDto;
import com.khoders.invoicemaster.dto.InvoiceItemDto;
import com.khoders.invoicemaster.service.InvoiceService;
import com.khoders.resource.jaxrs.JaxResponse;
import java.util.List;
import javax.inject.Inject;
import javax.ws.rs.BeanParam;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author Pascal
 */
@Path(ApiEndpoint.SALES_ENDPOINT)
public class SalesController {
    @Inject private InvoiceService invoiceService;
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response searchByDate(@BeanParam AppParam param){
        List<InvoiceDto> invoices = invoiceService.searchByDate(param);
        return JaxResponse.ok(invoices);
    }
    @GET
    @Path("/{invoiceId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response searchDetails(@PathParam("invoiceId") String invoiceId){
        List<InvoiceItemDto> itemDtos = invoiceService.salesDetails(invoiceId);
        return JaxResponse.ok(itemDtos);
    }
    @GET
    @Path("search/{branchId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response searchByBranch(@PathParam("branchId") String branchId){
        List<InvoiceDto> invoices = invoiceService.searchByBranch(branchId);
        return JaxResponse.ok(invoices);
    }
    @GET
    @Path("search/{userAccountId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response searchByUser(@PathParam("userAccountId") String userAccountId){
        List<InvoiceDto> invoices = invoiceService.searchByUser(userAccountId);
        return JaxResponse.ok(invoices);
    }
}
