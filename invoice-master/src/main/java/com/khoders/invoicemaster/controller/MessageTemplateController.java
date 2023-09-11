/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.khoders.invoicemaster.controller;

import com.khoders.invoicemaster.ApiEndpoint;
import com.khoders.invoicemaster.dto.sms.MessageTemplateDto;
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
@Path(ApiEndpoint.MESSAGE_TEMPLATE_ENDPOINT)
public class MessageTemplateController {
    private static final Logger log = LoggerFactory.getLogger(MessageTemplateController.class);
    @Inject private MsgService msgService;
    
    @POST
    public Response create(MessageTemplateDto templateDto){
        MessageTemplateDto dto = msgService.saveTemplate(templateDto);
        return JaxResponse.created(Msg.CREATED, dto);
    }
    @PUT
    public Response update(MessageTemplateDto templateDto){
        MessageTemplateDto dto = msgService.saveTemplate(templateDto);
        return JaxResponse.created(Msg.UPDATED, dto);
    }
    @GET
    @Path("/{templateId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response findById(@PathParam("templateId") String templateId){
        return JaxResponse.ok(Msg.RECORD_FOUND, msgService.findTemplateById(templateId));
    }
    @GET
    @Path("/list")
    @Produces(MediaType.APPLICATION_JSON)
    public Response findAll(){
        return JaxResponse.ok(Msg.RECORD_FOUND, msgService.findAllTemplates());
    }
    
    @DELETE
    @Path("/{templateId}")
    public Response delete(@PathParam("templateId") String templateId){
        boolean delete = msgService.deleteTemplate(templateId);
        if(delete)
            return JaxResponse.ok(Msg.DELETE_MESSAGE,delete);
        return JaxResponse.ok("Could not delete template",delete);
    }
}
