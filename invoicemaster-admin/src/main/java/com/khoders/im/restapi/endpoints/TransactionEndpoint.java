/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.khoders.im.restapi.endpoints;

import com.khoders.im.restapi.ApiEndpoint;
import com.khoders.im.restapi.payload.SaleItemDto;
import com.khoders.im.restapi.services.TransactionService;
import com.khoders.resource.jaxrs.JaxResponse;
import java.util.List;
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
@Path(ApiEndpoint.TRANSACTION_ENDPOINT)
public class TransactionEndpoint
{
    @Inject private TransactionService transactionService;
    
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    public Response userTransactions(@HeaderParam("userAccountId") String userAccountId)
    {
        List<SaleItemDto> dtos = transactionService.getSales(userAccountId);
        return JaxResponse.ok(dtos);
    }
}
