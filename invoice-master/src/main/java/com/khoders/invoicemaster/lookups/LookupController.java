/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.khoders.invoicemaster.lookups;

import com.khoders.invoicemaster.ApiEndpoint;
import com.khoders.resource.enums.UnitOfMeasurement;
import com.khoders.resource.jaxrs.JaxResponse;
import com.khoders.resource.utilities.Msg;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author Pascal
 */
@Path(ApiEndpoint.LOOKUP_ENDPOINT)
public class LookupController {
    @Inject private LookupService lookupService;
    
    @GET
    @Path("/units")
    @Produces(MediaType.APPLICATION_JSON)
    public Response units(){
        return JaxResponse.ok(Msg.RECORD_FOUND, LookupSetup.PrepareEnum(UnitOfMeasurement.values()));
    }
    
    // Entities
    @GET
    @Path("/inventory")
    @Produces(MediaType.APPLICATION_JSON)
    public Response stock(){
        return JaxResponse.ok(Msg.RECORD_FOUND, lookupService.inventory());
    }
    @GET
    @Path("/company-profile")
    @Produces(MediaType.APPLICATION_JSON)
    public Response companyProfile(){
        return JaxResponse.ok(Msg.RECORD_FOUND, lookupService.companyProfile());
    }
    @GET
    @Path("/company-company")
    @Produces(MediaType.APPLICATION_JSON)
    public Response companyBranch(){
        return JaxResponse.ok(Msg.RECORD_FOUND, lookupService.companyBranch());
    }
}
