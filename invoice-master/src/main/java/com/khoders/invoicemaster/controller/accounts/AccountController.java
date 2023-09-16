/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.khoders.invoicemaster.controller.accounts;

import com.khoders.invoicemaster.ApiEndpoint;
import com.khoders.invoicemaster.dto.accounts.AccountDto;
import com.khoders.invoicemaster.service.accounts.AccountService;
import com.khoders.resource.jaxrs.JaxResponse;
import com.khoders.resource.utilities.Msg;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
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
 * @author Richard Narh
 */
@Path(ApiEndpoint.ACCOUNT_ENDPOINT)
public class AccountController {
    @Inject private AccountService accountService;
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response create(AccountDto accountDto){
        AccountDto dto = accountService.saveAccount(accountDto);
        return JaxResponse.created(dto);
    }
    
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public Response update(AccountDto accountDto){
        AccountDto dto = accountService.saveAccount(accountDto);
        return JaxResponse.ok(Msg.UPDATED, dto);
    }
    
    @GET
    @Path("/{accountId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response findById(@PathParam("accountId") String accountId){
        return JaxResponse.ok(Msg.RECORD_FOUND, accountService.findById(accountId));
    }
    
    @GET
    @Path("/list")
    @Produces(MediaType.APPLICATION_JSON)
    public Response findAll(){
        return JaxResponse.ok(Msg.RECORD_FOUND, accountService.findAllAccounts());
    }
    
    @DELETE
    @Path("/{accountId}")
    public Response delete(@PathParam("accountId") String accountId){
        boolean delete = accountService.delete(accountId);
        if(delete)
            return JaxResponse.ok(Msg.DELETE_MESSAGE,delete);
        return JaxResponse.ok("Could not delete account",delete);
    }
}
