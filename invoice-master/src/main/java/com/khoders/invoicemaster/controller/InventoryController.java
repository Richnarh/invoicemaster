/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.khoders.invoicemaster.controller;

import com.khoders.invoicemaster.dto.InventoryDto;
import com.khoders.invoicemaster.mapper.AppParam;
import com.khoders.invoicemaster.service.ProductService;
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
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author Pascal
 */
@Path(ApiEndpoint.INVENTORY_ENDPOINT)
public class InventoryController {
    @Inject private CrudApi crudApi;
    @Inject private ProductService productService;
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response create(@BeanParam AppParam param, InventoryDto inventoryDto){
        InventoryDto dto = productService.save(inventoryDto, param);
        return JaxResponse.created(Msg.CREATED, dto);
    }
    
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public Response update(@BeanParam AppParam param, InventoryDto inventoryDto){
        InventoryDto dto = productService.save(inventoryDto, param);
        return JaxResponse.ok(Msg.UPDATED, dto);
    }
    
    @GET
    @Path("/{inventoryId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response findById(@PathParam("inventoryId") String inventoryId){
        return JaxResponse.ok(Msg.RECORD_FOUND, productService.findById(inventoryId));
    }
    
    @GET
    @Path("/list")
    @Produces(MediaType.APPLICATION_JSON)
    public Response findAll(){
        return JaxResponse.ok(Msg.RECORD_FOUND, productService.findAll());
    }
    
    @DELETE
    @Path("/{inventoryId}")
    public Response delete(@PathParam("inventoryId") String inventoryId){
        boolean delete = productService.delete(inventoryId);
        if(delete)
            return JaxResponse.ok(Msg.DELETE_MESSAGE,delete);
        return JaxResponse.ok("Could not delete inventory",delete);
    }
}
