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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author richa
 */
@Entity
@Table(name = "tax_scheme")
public class TaxScheme extends BaseModel implements Serializable
{
    @Column(name = "tax_id")
    private String taxId;
    
    @JoinColumn(name = "tax", referencedColumnName = "id")
    @ManyToOne
    private Tax tax;
    
    @Column(name = "tax_year")
    private int taxYear = LocalDate.now().getYear();

    public String getTaxId()
    {
        return taxId;
    }

    public void setTaxId(String taxId)
    {
        this.taxId = taxId;
    }
    
    public Tax getTax()
    {
        return tax;
    }

    public void setTax(Tax tax)
    {
        this.tax = tax;
    }

    public int getTaxYear()
    {
        return taxYear;
    }

    public void setTaxYear(int taxYear)
    {
        this.taxYear = taxYear;
    }
    
    public void genCode()
    {
        if (getTaxId() != null)
        {
            setTaxId(getTaxId());
        } else
        {
            setTaxId(SystemUtils.generateCode());
        }
    }
}
