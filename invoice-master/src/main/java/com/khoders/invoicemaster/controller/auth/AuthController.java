/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.khoders.invoicemaster.controller.auth;

import com.khoders.invoicemaster.ApiEndpoint;
import com.khoders.invoicemaster.payload.AuthRequest;
import com.khoders.invoicemaster.payload.AuthResponse;
import com.khoders.invoicemaster.service.AuthService;
import com.khoders.resource.jaxrs.JaxResponse;
import com.khoders.resource.utilities.Msg;
import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author richa
 */
@Path(ApiEndpoint.AUTH_ENDPOINT)
public class AuthController{
    private static final Logger log = LoggerFactory.getLogger(AuthController.class);
    @Inject private AuthService authService;
    
    @POST
    @Path(value="/login")
    @Produces(MediaType.APPLICATION_JSON)
    public Response doLogin(AuthRequest authRequest){
        log.info("Signing in...");
        AuthResponse authResponse = authService.doLogin(authRequest);
        return JaxResponse.ok(Msg.RECORD_FOUND, authResponse);
    }
}
