/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.khoders.invoicemaster.entities.master;

import com.khoders.resource.jpa.BaseModel;
import com.khoders.resource.utilities.SystemUtils;
import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 *
 * @author richa
 */
@Entity
@Table(name = "vat_scheme")
public class VatScheme extends BaseModel implements Serializable
{
    @Column(name = "scheme_id")
    private String schemeId;
    
    @Column(name = "vat")
    private double vat;
    
    @Column(name = "nhil")
    private double nhil;
    
    @Column(name = "get_fund")
    private double getFund;
    
    @Column(name = "covid_19_levy")
    private double covid19Levy;
    
    @Column(name = "scheme_year")
    private int schemeYear = LocalDate.now().getYear();

    public double getVat()
    {
        return vat;
    }

    public void setVat(double vat)
    {
        this.vat = vat;
    }

    public double getNhil()
    {
        return nhil;
    }

    public void setNhil(double nhil)
    {
        this.nhil = nhil;
    }

    public double getGetFund()
    {
        return getFund;
    }

    public void setGetFund(double getFund)
    {
        this.getFund = getFund;
    }

    public double getCovid19Levy()
    {
        return covid19Levy;
    }

    public void setCovid19Levy(double covid19Levy)
    {
        this.covid19Levy = covid19Levy;
    }

    public String getSchemeId()
    {
        return schemeId;
    }

    public void setSchemeId(String schemeId)
    {
        this.schemeId = schemeId;
    }

    public int getSchemeYear()
    {
        return schemeYear;
    }

    public void setSchemeYear(int schemeYear)
    {
        this.schemeYear = schemeYear;
    }

    public void genCode()
    {
        if (getSchemeId() != null)
        {
            setSchemeId(getSchemeId());
        } else
        {
            setSchemeId(SystemUtils.generateCode());
        }
    }
}
