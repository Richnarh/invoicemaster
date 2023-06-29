/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.khoders.admin.controller;

import com.khoders.invoicemaster.dto.SaleLeadDto;
import com.khoders.admin.mapper.AppParam;
import com.khoders.admin.services.UserService;
import com.khoders.invoicemaster.ApiEndpoint;
import com.khoders.resource.jaxrs.JaxResponse;
import com.khoders.resource.jpa.CrudApi;
import com.khoders.resource.utilities.Msg;
import javax.inject.Inject;
import javax.ws.rs.BeanParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author Pascal
 */
@Path(ApiEndpoint.SALES_LEAD_ENDPOINT)
public class SaleLeadController {
    @Inject private CrudApi crudApi;
    @Inject private UserService userService;
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response create(@BeanParam AppParam param, SaleLeadDto leadDto){
        SaleLeadDto dto = userService.saveLead(leadDto, param);
        return JaxResponse.created(Msg.CREATED, dto);
    }
    
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public Response update(@BeanParam AppParam param, SaleLeadDto leadDto){
        SaleLeadDto dto = userService.saveLead(leadDto, param);
        return JaxResponse.ok(Msg.UPDATED, dto);
    }
    
    @GET
    @Path("/{saleLeadId}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response findById(@PathParam("saleLeadId") String saleLeadId){
        SaleLeadDto dto = userService.findbyId(saleLeadId);
        return JaxResponse.created(Msg.RECORD_FOUND, dto);
    }
    
    @DELETE
    @Path("/{saleLeadId}")
    public Response delete(@PathParam("saleLeadId") String saleLeadId){
        boolean delete = userService.delete(saleLeadId);
        if(delete)
            return JaxResponse.ok(Msg.DELETE_MESSAGE,delete);
        return JaxResponse.ok("Could not delete config",delete);
    }
}
