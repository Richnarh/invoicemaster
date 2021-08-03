/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.khoders.invoicemaster.entities.master;

import com.khoders.resource.jpa.BaseModel;
import java.io.Serializable;
import javax.persistence.Column;

/**
 *
 * @author richa
 */

public class Currency extends BaseModel implements Serializable
{
    @Column(name = "currency_code")
    private String currencyCode;
    
    @Column(name = "current_rate")
    private double currencyRate;

    public String getCurrencyCode()
    {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode)
    {
        this.currencyCode = currencyCode;
    }

    public double getCurrencyRate()
    {
        return currencyRate;
    }

    public void setCurrencyRate(double currencyRate)
    {
        this.currencyRate = currencyRate;
    }
    
    
}
