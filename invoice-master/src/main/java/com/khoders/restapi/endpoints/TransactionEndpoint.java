/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.khoders.restapi.endpoints;

import com.khoders.restapi.ApiEndpoint;
import com.khoders.restapi.payload.SaleItemDto;
import com.khoders.restapi.services.TransactionService;
import com.khoders.resource.jaxrs.JaxResponse;
import com.khoders.resource.utilities.Msg;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author richa
 */
@Stateless
@Tag(name = "User Transaction")
@Path(ApiEndpoint.TRANSACTION_ENDPOINT)
public class TransactionEndpoint
{
    @Inject private TransactionService transactionService;
    
    @GET
    @Operation(summary = "Get user transaction record")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response userTransactions(@HeaderParam("userAccountId") String userAccountId)
    {
        List<SaleItemDto> dtos = transactionService.getSales(userAccountId);
        return JaxResponse.ok(Msg.RECORD_FOUND,dtos);
    }
    
    public Response invoice(@HeaderParam("userAccountId") String userAccountId)
    {
        return JaxResponse.ok("");
    }
}
