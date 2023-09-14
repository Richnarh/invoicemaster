/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.khoders.invoicemaster.controller;

import com.khoders.invoicemaster.ApiEndpoint;
import com.khoders.invoicemaster.dto.sms.SmsMessage;
import com.khoders.invoicemaster.service.MsgService;
import com.khoders.resource.jaxrs.JaxResponse;
import com.khoders.resource.utilities.Msg;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Pascal
 */
@Path(ApiEndpoint.SMS_ENDPOINT)
public class SmsController {
    private static final Logger log = LoggerFactory.getLogger(SmsController.class);
    @Inject private MsgService msgService;
    
    @POST
    @Path("/single-message")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response singleMessage(SmsMessage smsMessage){
        String message = msgService.singleMessage(smsMessage);
        return JaxResponse.ok(Msg.SUCCESS_MESSAGE, message);
    }
    @POST
    @Path("/bulk-message")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response bulkMessage(SmsMessage smsMessage){
        String message = msgService.bulkMessage(smsMessage);
        return JaxResponse.ok(Msg.SUCCESS_MESSAGE, message);
    }
}
