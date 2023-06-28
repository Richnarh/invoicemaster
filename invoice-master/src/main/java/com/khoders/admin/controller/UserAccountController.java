/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.khoders.admin.controller;

import com.khoders.invoicemaster.dto.UserDto;
import com.khoders.admin.services.UserService;
import com.khoders.invoicemaster.ApiEndpoint;
import com.khoders.resource.jaxrs.JaxResponse;
import com.khoders.resource.utilities.Msg;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author Pascal
 */
@Path(ApiEndpoint.USER_ENDPOINT)
public class UserAccountController {
    @Inject private UserService userService;
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response create(UserDto dto){
        UserDto userDto = userService.save(dto);
        return JaxResponse.created(Msg.CREATED, userDto);
    }
    
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public Response update(UserDto dto){
        UserDto userDto = userService.save(dto);
        return JaxResponse.ok(Msg.UPDATED, userDto);
    }
    
    @GET
    @Path("/{userAccountId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response findById(@PathParam("userAccountId") String userAccountId){
        UserDto userDto = userService.findById(userAccountId);
        return JaxResponse.ok(Msg.RECORD_FOUND, userDto);
    }
    
    @POST
    @Path("/user/{userAccountId}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response loginUser(@PathParam("userAccountId") String userAccountId){
        UserDto dto = userService.loginUser(userAccountId);
        return JaxResponse.ok(dto);
    }
    
    @POST
    @Path("/{userAccountId}/{password}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateUserPassword(@PathParam("userAccountId") String userAccountId, @PathParam("password") String password){
        String updateMsg = userService.updatePassword(userAccountId,password);
        return JaxResponse.ok(Msg.UPDATED, updateMsg);
    }
    
    @GET
    @Path("/list")
    @Produces(MediaType.APPLICATION_JSON)
    public Response findAll(){
        return JaxResponse.ok(userService.fetchAllUsers());
    }
}
