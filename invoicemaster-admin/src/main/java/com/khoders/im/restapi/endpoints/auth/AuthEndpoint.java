/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.khoders.im.restapi.endpoints.auth;

import com.khoders.im.restapi.util.JaxResponse;
import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author richa
 */
@Path("v1/auth")
public class AuthEndpoint
{
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    public Response getUser(){
        List<Integer> i = new ArrayList<>();
        i.add(1);
        i.add(2);
        i.add(3);
        return JaxResponse.ok("this is a test of rest api", i);
    }
}
