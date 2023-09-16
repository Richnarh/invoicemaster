/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.khoders.invoicemaster.controller;

import com.khoders.invoicemaster.mapper.AppParam;
import com.khoders.invoicemaster.service.ProductService;
import com.khoders.invoicemaster.ApiEndpoint;
import com.khoders.invoicemaster.dto.ProductDto;
import com.khoders.resource.jaxrs.JaxResponse;
import com.khoders.resource.utilities.Msg;
import java.io.File;
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
@Path(ApiEndpoint.PRODUCT_ENDPOINT)
public class ProductController {
    @Inject private ProductService productService;
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response create(ProductDto productDto, @BeanParam AppParam param){
        ProductDto dto = productService.save(productDto, param);
        return JaxResponse.created(Msg.CREATED, dto);
    }
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public Response update(ProductDto productDto, @BeanParam AppParam param){
        ProductDto dto = productService.save(productDto, param);
        return JaxResponse.ok(Msg.UPDATED, dto);
    }
    @GET
    @Path("/{productId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response findById(@PathParam("productId") String productId){
        return JaxResponse.ok(Msg.RECORD_FOUND, productService.findProductById(productId));
    }
    
    @GET
    @Path("/list")
    @Produces(MediaType.APPLICATION_JSON)
    public Response findAll(){
        return JaxResponse.ok(Msg.RECORD_FOUND, productService.findAllProducts());
    }
    @DELETE
    @Path("/{productId}")
    public Response delete(@PathParam("productId") String productId){
        boolean delete = productService.delete(productId);
        if(delete)
            return JaxResponse.ok(Msg.DELETE_MESSAGE,delete);
        return JaxResponse.ok("Could not delete products",delete);
    }
    
    private File temp;

    public File getTemp() {
        return temp;
    }

    public void setTemp(File temp) {
        this.temp = temp;
    }
    
}
