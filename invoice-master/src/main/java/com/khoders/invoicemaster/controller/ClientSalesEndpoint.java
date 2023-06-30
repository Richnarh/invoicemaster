/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.khoders.invoicemaster.controller;

import com.khoders.resource.utilities.Msg;
import com.khoders.invoicemaster.ApiEndpoint;
import com.khoders.invoicemaster.service.SalesService;
import com.khoders.resource.jaxrs.JaxResponse;
import com.khoders.invoicemaster.dto.OnlineClientDto;
import com.khoders.invoicemaster.dto.TransactionDto;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author richa
 */
@Path("client-sales")
public class ClientSalesEndpoint
{
    @Inject private SalesService salesService;
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response create(OnlineClientDto dto){
        TransactionDto saleDto = salesService.save(dto);
        return JaxResponse.created(Msg.CREATED, saleDto);
    }
    
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public Response update(OnlineClientDto dto){
        TransactionDto saleDto = salesService.save(dto);
        return JaxResponse.ok(Msg.UPDATED, saleDto);
    }
    
    @GET
    @Path("/{saleId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response findById(@PathParam("saleId") String saleId) {
        return JaxResponse.ok(saleId, saleId);
    }
}
