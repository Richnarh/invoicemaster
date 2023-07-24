/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.khoders.invoicemaster.controller;

import com.khoders.invoicemaster.ApiEndpoint;
import com.khoders.invoicemaster.dto.InvoiceDto;
import com.khoders.invoicemaster.dto.ReverseData;
import com.khoders.invoicemaster.service.InvoiceService;
import com.khoders.resource.jaxrs.JaxResponse;
import com.khoders.resource.utilities.Msg;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author Pascal
 */
@Path(ApiEndpoint.INVOICE_ENDPOINT)
public class InvoiceController {
    @Inject private InvoiceService invoiceService;
    
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
}
