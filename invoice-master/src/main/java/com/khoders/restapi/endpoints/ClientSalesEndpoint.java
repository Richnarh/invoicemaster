/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.khoders.restapi.endpoints;

import com.khoders.resource.utilities.Msg;
import com.khoders.restapi.ApiEndpoint;
import com.khoders.restapi.services.SalesService;
import com.khoders.resource.jaxrs.JaxResponse;
import com.khoders.resource.utilities.SystemUtils;
import com.khoders.restapi.payload.OnlineClientDto;
import com.khoders.restapi.payload.TransactionDto;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Path(ApiEndpoint.SALES_ENDPOINT)
@Tag(name = "Online Client")
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
        return JaxResponse.created(Msg.UPDATED, saleDto);
    }
    
    @GET
    @Path("/{saleId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response findById(@PathParam("saleId") String saleId) {
        return JaxResponse.ok(saleId, saleId);
    }
}
