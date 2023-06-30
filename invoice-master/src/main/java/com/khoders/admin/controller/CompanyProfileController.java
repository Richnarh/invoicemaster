/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.khoders.admin.controller;

import com.khoders.admin.services.BranchService;
import com.khoders.invoicemaster.ApiEndpoint;
import com.khoders.invoicemaster.dto.CompanyProfileDto;
import com.khoders.resource.jaxrs.JaxResponse;
import com.khoders.resource.utilities.Msg;
import javax.inject.Inject;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

/**
 *
 * @author Pascal
 */
@Path(ApiEndpoint.COMPANY_PROFILE_ENDPOINT)
public class CompanyProfileController {
    @Inject private BranchService branchService;
    
    @POST
    public Response create(CompanyProfileDto dto){ 
        CompanyProfileDto profileDto = branchService.saveProfile(dto);
        return JaxResponse.created(Msg.CREATED, profileDto);
    }
    
    @PUT
    public Response update(CompanyProfileDto dto){ 
        CompanyProfileDto branchDto = branchService.saveProfile(dto);
        return JaxResponse.ok(Msg.UPDATED, branchDto);
    }
    
    @GET
    @Path("/{profileId}")
    public Response findById(@PathParam("profileId") String profileId){
        CompanyProfileDto dto = branchService.findProfileById(profileId);
        return JaxResponse.ok(Msg.RECORD_FOUND,dto);
    }
    
    @GET
    @Path("/list")
    public Response findAll(){
        return JaxResponse.ok(branchService.fetchAllProfiles());
    }
    
    @DELETE
    @Path("/{profileId}")
    public Response delete(@PathParam("profileId") String profileId){
        boolean delete = branchService.deleteProfile(profileId);
        if(delete)
            return JaxResponse.ok(Msg.DELETE_MESSAGE,delete);
        return JaxResponse.ok("Could not delete company profile",delete);
    }
}
