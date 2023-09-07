/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.khoders.invoicemaster.controller;

import com.khoders.invoicemaster.mapper.AppParam;
import com.khoders.invoicemaster.service.TransactionService;
import com.khoders.invoicemaster.ApiEndpoint;
import com.khoders.invoicemaster.dto.PaymentDataDto;
import com.khoders.resource.jaxrs.JaxResponse;
import com.khoders.resource.utilities.Msg;
import com.khoders.invoicemaster.enums.DeliveryStatus;
import java.util.Base64;
import java.util.List;
import javax.inject.Inject;
import javax.ws.rs.BeanParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Pascal
 */
@Path(ApiEndpoint.PAYMENT_ENDPOINT)
public class TransactionController {
    private static final Logger log = LoggerFactory.getLogger(TransactionController.class);
    @Inject private TransactionService transactionService;
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response fetchTransaction(@BeanParam AppParam param){
        List<PaymentDataDto> dtoList = transactionService.paymentDataList(param);
        return JaxResponse.ok(Msg.RECORD_FOUND,dtoList);
    }
    
    @PUT
    @Path("/{paymentStatus}/{paymentDataId}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateStatus(@PathParam("paymentStatus") String paymentStatus, @PathParam("paymentDataId") String paymentDataId){
        String dto = transactionService.updateStatus(paymentStatus,paymentDataId);
        return JaxResponse.ok(Msg.UPDATED,dto);
    }
    
    @PUT
    @Path("/{paymentDataId}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateDeliveryStatus(@PathParam("paymentDataId") String paymentDataId){
        PaymentDataDto dto = transactionService.updateDeliveryStatus(paymentDataId);
        return JaxResponse.ok(Msg.UPDATED,dto);
    }
    
    @GET
    @Path("/delivery/{deliveryStatus}")
    public Response delivery(@PathParam("deliveryStatus") DeliveryStatus deliveryStatus){
        List<PaymentDataDto> dtoList = transactionService.deliveryList(deliveryStatus);
        return JaxResponse.ok(dtoList);
    }
    @GET
    @Path("/{paymentDataId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response findById(@PathParam("paymentDataId") String paymentDataId){
        return JaxResponse.ok(transactionService.findById(paymentDataId));
    }
    
    @GET
    @Path("/{paymentDataId}/report")
    @Produces(MediaType.APPLICATION_JSON)
    public Response report(@PathParam("paymentDataId") String paymentDataId){
        log.debug("reporting...");
        byte[] reportByte = transactionService.printReport(paymentDataId);
        return JaxResponse.ok(Msg.RECORD_FOUND, Base64.getEncoder().encodeToString(reportByte));
    }
    
    @DELETE
    @Path("/{paymentDataId}")
    public Response delete(@PathParam("paymentDataId") String paymentDataId){
        boolean delete = transactionService.delete(paymentDataId);
        if(delete)
            return JaxResponse.ok(Msg.DELETE_MESSAGE,delete);
        return JaxResponse.ok("Could not delete products",delete);
    }
}
