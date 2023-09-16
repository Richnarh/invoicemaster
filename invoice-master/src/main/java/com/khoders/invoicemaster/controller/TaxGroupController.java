/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.khoders.invoicemaster.controller;

import com.khoders.invoicemaster.ApiEndpoint;
import com.khoders.invoicemaster.dto.TaxGroupDto;
import com.khoders.invoicemaster.service.TaxService;
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
@Path(ApiEndpoint.TAX_GROUP_ENDPOINT)
public class TaxGroupController {
    @Inject private TaxService taxService;
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createGroup(TaxGroupDto groupDto){
        TaxGroupDto dto = taxService.saveTaxGroup(groupDto);
        return JaxResponse.created(Msg.CREATED, dto);
    }
    
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public Response update(TaxGroupDto groupDto){
        TaxGroupDto dto = taxService.saveTaxGroup(groupDto);
        return JaxResponse.ok(Msg.UPDATED, dto);
    }
    
    @GET
    @Path("/{taxGroupId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response findById(@PathParam("taxGroupId") String taxGroupId){
        return JaxResponse.ok(Msg.RECORD_FOUND, taxService.findTaxgroupById(taxGroupId));
    }
    
    @GET
    @Path("/list")
    @Produces(MediaType.APPLICATION_JSON)
    public Response findAll(){
        return JaxResponse.ok(Msg.RECORD_FOUND, taxService.findAllTaxGroups());
    }
    
    @DELETE
    @Path("/{taxGroupId}")
    public Response delete(@PathParam("taxGroupId") String taxGroupId){
        boolean delete = taxService.deleteTaxGroup(taxGroupId);
        if(delete)
            return JaxResponse.ok(Msg.DELETE_MESSAGE,delete);
        return JaxResponse.ok("Could not delete inventory",delete);
    }
}
