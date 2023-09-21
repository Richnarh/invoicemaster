package com.khoders.invoicemaster.controller.accounts;

import com.khoders.invoicemaster.ApiEndpoint;
import com.khoders.invoicemaster.dto.accounts.CashDepositeDto;
import com.khoders.invoicemaster.service.DepositeService;
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
@Path(ApiEndpoint.CASH_DEPOSITE_ENDPOINT)
public class DepositeController {
    @Inject private DepositeService depositeService;
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response create(CashDepositeDto cashDepositeDto){
        CashDepositeDto dto = depositeService.saveCashDeposite(cashDepositeDto);
        return JaxResponse.created(Msg.CREATED,dto);
    }
    
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public Response update(CashDepositeDto cashDepositeDto){
        CashDepositeDto dto = depositeService.saveCashDeposite(cashDepositeDto);
        return JaxResponse.ok(Msg.UPDATED, dto);
    }
    
    @GET
    @Path("/{pettyCashId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response findById(@PathParam("pettyCashId") String pettyCashId){
        return JaxResponse.ok(Msg.RECORD_FOUND, depositeService.findCashDepositeById(pettyCashId));
    }
    
    @GET
    @Path("/list")
    @Produces(MediaType.APPLICATION_JSON)
    public Response findAll(){
        return JaxResponse.ok(Msg.RECORD_FOUND, depositeService.findAllCashDepositeList());
    }
    
    @DELETE
    @Path("/{pettyCashId}")
    public Response delete(@PathParam("pettyCashId") String pettyCashId){
        boolean delete = depositeService.deleteCashDeposite(pettyCashId);
        if(delete)
            return JaxResponse.ok(Msg.DELETE_MESSAGE,delete);
        return JaxResponse.ok("Could not delete CashDeposite",delete);
    }
}
