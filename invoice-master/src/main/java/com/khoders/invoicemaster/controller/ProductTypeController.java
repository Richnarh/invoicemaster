/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.khoders.invoicemaster.controller;

import com.khoders.invoicemaster.mapper.AppParam;
import com.khoders.invoicemaster.service.ProductService;
import com.khoders.invoicemaster.ApiEndpoint;
import com.khoders.invoicemaster.dto.ProductTypeDto;
import com.khoders.resource.jaxrs.JaxResponse;
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
@Path(ApiEndpoint.PRODUCT_TYPE_ENDPOINT)
public class ProductTypeController {
    @Inject private ProductService productService;
 
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response create(ProductTypeDto typeDto, @BeanParam AppParam param){
        ProductTypeDto dto = productService.save(typeDto, param);
        return JaxResponse.created(Msg.CREATED, dto);
    }
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public Response update(ProductTypeDto typeDto, @BeanParam AppParam param){
        ProductTypeDto dto = productService.save(typeDto, param);
        return JaxResponse.ok(Msg.UPDATED, dto);
    }
    @GET
    @Path("/{productTypeId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response findById(@PathParam("productTypeId") String productTypeId){
        return JaxResponse.ok(Msg.RECORD_FOUND, productService.findByTypeId(productTypeId));
    }
    
    @GET
    @Path("/list")
    @Produces(MediaType.APPLICATION_JSON)
    public Response findAll(){
        return JaxResponse.ok(Msg.RECORD_FOUND, productService.findAllProductTypes());
    }
    @DELETE
    @Path("/{productTypeId}")
    public Response delete(@PathParam("productTypeId") String productTypeId){
        boolean delete = productService.deleteType(productTypeId);
        if(delete)
            return JaxResponse.ok(Msg.DELETE_MESSAGE,delete);
        return JaxResponse.ok("Could not delete product type",delete);
    }
}
