/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.khoders.im.restapi.util;

import java.util.LinkedHashMap;
import java.util.Map;
import javax.ws.rs.core.Response;
import org.springframework.http.HttpStatus;

/**
 *
 * @author richa
 */
public class JaxResponse
{
    public static <T> Response ok(String message, Object responseObj){
        Map<String, Object> map = new LinkedHashMap<>();
        map.put("message", message);
        map.put("success", true);
        map.put("statusCode", HttpStatus.OK.value());
        map.put("data", responseObj);
        
        return Response.ok(map).build();
    }
    public static <T> Response created(String message, Object responseObj){
        Map<String, Object> map = new LinkedHashMap<>();
        map.put("message", message);
        map.put("success", true);
        map.put("statusCode", HttpStatus.CREATED.value());
        map.put("data", responseObj);
        
        return Response.ok(map).build();
    }
}
