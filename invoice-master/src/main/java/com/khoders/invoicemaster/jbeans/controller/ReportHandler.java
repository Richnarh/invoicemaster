/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.khoders.invoicemaster.jbeans.controller;

import java.io.InputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

/**
 *
 * @author richa
 */
@Named
@RequestScoped
public class ReportHandler implements Serializable
{
    Map<String, Object> reportParams = new HashMap<>();
    private InputStream inputStream;
    
    
    public InputStream reportStream(String reportFile)
    {
        try
        {
          this.inputStream = getClass().getResourceAsStream(reportFile);   
          return this.inputStream;
          
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }
    
    public void addParams(String key, Object value)
    {
        reportParams.put(key, value);
    }
   
}
