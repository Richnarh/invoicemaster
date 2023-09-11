/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.khoders.invoicemaster.controller;

import com.khoders.invoicemaster.ApiEndpoint;
import com.khoders.invoicemaster.dto.sms.GroupContactDto;
import com.khoders.invoicemaster.service.MsgService;
import com.khoders.resource.jaxrs.JaxResponse;
import com.khoders.resource.utilities.Msg;
import java.util.List;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Pascal
 */
@Path(ApiEndpoint.GROUP_CONTACT_ENDPOINT)
public class GroupContactController {
    private static final Logger log = LoggerFactory.getLogger(GroupContactController.class);
    @Inject private MsgService msgService;
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response groupContact(GroupContactDto contactDto){
        List<GroupContactDto> dto = msgService.createGroupContact(contactDto);
        return JaxResponse.created(Msg.CREATED, dto);
    }
    @GET
    @Path("/list")
    @Produces(MediaType.APPLICATION_JSON)
    public Response fetchAll(){
        return JaxResponse.ok(Msg.RECORD_FOUND, msgService.findAllContactgroups());
    }
    @GET
    @Path("/list/{groupId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response findContactByGroup(String groupId){
        return JaxResponse.ok(Msg.RECORD_FOUND, msgService.findContactByGroup(groupId));
    }
}
