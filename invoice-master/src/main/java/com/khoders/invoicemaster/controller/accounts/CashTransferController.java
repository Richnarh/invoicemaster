/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.khoders.invoicemaster.controller.accounts;

import com.khoders.invoicemaster.ApiEndpoint;
import com.khoders.invoicemaster.dto.accounts.CashTransferDto;
import com.khoders.invoicemaster.dto.accounts.CashTransferDto;
import com.khoders.invoicemaster.service.AccountService;
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
@Path(ApiEndpoint.CASH_TRANSFER_ENDPOINT)
public class CashTransferController {
    @Inject private AccountService accountService;
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response create(CashTransferDto cashTransferDto){
        CashTransferDto dto = accountService.saveCashTransfer(cashTransferDto);
        return JaxResponse.created(Msg.CREATED,dto);
    }
    
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public Response update(CashTransferDto cashTransferDto){
        CashTransferDto dto = accountService.saveCashTransfer(cashTransferDto);
        return JaxResponse.ok(Msg.UPDATED, dto);
    }
    
    @GET
    @Path("/{cashTransferId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response findById(@PathParam("cashTransferId") String cashTransferId){
        return JaxResponse.ok(Msg.RECORD_FOUND, accountService.findCashTransferById(cashTransferId));
    }
    
    @GET
    @Path("/list")
    @Produces(MediaType.APPLICATION_JSON)
    public Response findAll(){
        return JaxResponse.ok(Msg.RECORD_FOUND, accountService.findAllCashTransfers());
    }
    
    @DELETE
    @Path("/{cashTransferId}")
    public Response delete(@PathParam("cashTransferId") String cashTransferId){
        boolean delete = accountService.deleteCashTransfer(cashTransferId);
        if(delete)
            return JaxResponse.ok(Msg.DELETE_MESSAGE,delete);
        return JaxResponse.ok("Could not delete CashTransfer",delete);
    }
}
