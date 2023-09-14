/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.khoders.invoicemaster.controller;

import com.khoders.invoicemaster.ApiEndpoint;
import com.khoders.invoicemaster.reportData.TaxDto;
import com.khoders.invoicemaster.service.TaxService;
import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

/**
 *
 * @author Pascal
 */
@Path(ApiEndpoint.TAX_ENDPOINT)
public class TaxController {
    @Inject private TaxService taxService;
    
    @POST
    public Response create(TaxDto taxDto){
       return null;
    }
}
