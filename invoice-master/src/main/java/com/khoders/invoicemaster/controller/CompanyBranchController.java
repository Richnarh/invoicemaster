/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.khoders.invoicemaster.controller;

import com.khoders.invoicemaster.dto.CompanyBranchDto;
import com.khoders.invoicemaster.service.BranchService;
import com.khoders.invoicemaster.ApiEndpoint;
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
@Path(ApiEndpoint.COMPANY_BRANCH_ENDPOINT)
public class CompanyBranchController {
    @Inject private BranchService branchService;
    
    @POST
    public Response create(CompanyBranchDto dto){ 
        CompanyBranchDto branchDto = branchService.save(dto);
        return JaxResponse.created(Msg.CREATED, branchDto);
    }
    
    @PUT
    public Response update(CompanyBranchDto dto){ 
        CompanyBranchDto branchDto = branchService.save(dto);
        return JaxResponse.ok(Msg.UPDATED, branchDto);
    }
    
    @GET
    @Path("/{companyBranchId}")
    public Response findById(@PathParam("companyBranchId") String companyBranchId){
        CompanyBranchDto dto = branchService.findById(companyBranchId);
        return JaxResponse.ok(Msg.RECORD_FOUND,dto);
    }
    
    @GET
    @Path("/list")
    public Response findAll(){
        return JaxResponse.ok(branchService.fetchAllBranches());
    }
    
    @DELETE
    @Path("/{companyBranchId}")
    public Response delete(@PathParam("companyBranchId") String companyBranchId){
        boolean delete = branchService.delete(companyBranchId);
        if(delete)
            return JaxResponse.ok(Msg.DELETE_MESSAGE,delete);
        return JaxResponse.ok("Could not delete company branch",delete);
    }
}
