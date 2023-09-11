/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.khoders.invoicemaster.controller;

import com.khoders.invoicemaster.ApiEndpoint;
import com.khoders.invoicemaster.dto.sms.SMSGrupDto;
import com.khoders.invoicemaster.service.MsgService;
import com.khoders.resource.jaxrs.JaxResponse;
import com.khoders.resource.utilities.Msg;
import javax.inject.Inject;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
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
@Path(ApiEndpoint.GROUP_ENDPOINT)
public class SMSGrupController {
    private static final Logger log = LoggerFactory.getLogger(SMSGrupController.class);
    @Inject private MsgService msgService;
    
    @POST
    public Response create(SMSGrupDto grupDto){
        SMSGrupDto dto = msgService.saveGroup(grupDto);
        return JaxResponse.created(Msg.CREATED, dto);
    }
    @PUT
    public Response update(SMSGrupDto grupDto){
        SMSGrupDto dto = msgService.saveGroup(grupDto);
        return JaxResponse.created(Msg.UPDATED, dto);
    }
    @GET
    @Path("/{smsgroupId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response findById(@PathParam("smsgroupId") String smsgroupId){
        return JaxResponse.ok(Msg.RECORD_FOUND, msgService.findGrupById(smsgroupId));
    }
    @GET
    @Path("/list")
    @Produces(MediaType.APPLICATION_JSON)
    public Response findAll(){
        return JaxResponse.ok(Msg.RECORD_FOUND, msgService.findAll());
    }
    
    @DELETE
    @Path("/{smsgroupId}")
    public Response delete(@PathParam("smsgroupId") String smsgroupId){
        boolean delete = msgService.delete(smsgroupId);
        if(delete)
            return JaxResponse.ok(Msg.DELETE_MESSAGE,delete);
        return JaxResponse.ok("Could not delete sms group",delete);
    }
}
