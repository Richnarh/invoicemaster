/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.khoders.admin.controller;

import com.khoders.admin.mapper.AppParam;
import com.khoders.admin.services.TransactionService;
import com.khoders.invoicemaster.ApiEndpoint;
import com.khoders.invoicemaster.dto.PaymentDataDto;
import com.khoders.resource.jaxrs.JaxResponse;
import com.khoders.resource.utilities.Msg;
import java.util.List;
import javax.inject.Inject;
import javax.ws.rs.BeanParam;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author Pascal
 */
@Path(ApiEndpoint.PAYMENT_ENDPOINT)
public class TransactionController {
    @Inject private TransactionService transactionService;

    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response fetchTransaction(@BeanParam AppParam param){
        List<PaymentDataDto> dtoList = transactionService.paymentDataList(param);
        return JaxResponse.ok(Msg.RECORD_FOUND,dtoList);
    }
    
    @PUT
    @Path("/{paymentStatus}/{paymentDataId}")
    public Response updateStatus(@PathParam("paymentStatus") String paymentStatus, @PathParam("paymentDataId") String paymentDataId){
        String dto = transactionService.updateStatus(paymentStatus,paymentDataId);
        return JaxResponse.ok(Msg.UPDATED,dto);
    }
    
    @GET
    @Path("/delivery/{deliveryStatus}")
    public Response delivery(@PathParam("deliveryStatus") String deliveryStatus){
        List<PaymentDataDto> dtoList = transactionService.deliveryList(deliveryStatus);
        return JaxResponse.ok(dtoList);
    }
    @GET
    @Path("/paymentDataId")
    public Response findById(@PathParam("paymentDataId") String paymentDataId){
        return JaxResponse.ok(transactionService.findById(paymentDataId));
    }
    
    @DELETE
    @Path("{paymentDataId}")
    public Response delete(@PathParam("paymentDataId") String paymentDataId){
        boolean delete = transactionService.delete(paymentDataId);
        if(delete)
            return JaxResponse.ok(Msg.DELETE_MESSAGE,delete);
        return JaxResponse.ok("Could not delete products",delete);
    }
}
