/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.khoders.invoicemaster.entites;

import com.khoders.resource.jpa.BaseModel;
import com.khoders.resource.utilities.SystemUtils;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author pascal
 */
@Entity
@Table(name = "colours")
public class Colours extends BaseModel implements Serializable
{

    @Column(name = "colour_code")
    private String colourCode;

    @Column(name = "colour_name")
    private String colourName;

    public String getColourCode()
    {
        return colourCode;
    }

    public void setColourCode(String colourCode)
    {
        this.colourCode = colourCode;
    }

    public String getColourName()
    {
        return colourName;
    }

    public void setColourName(String colourName)
    {
        this.colourName = colourName;
    }
    public void genCode()
    {
        if (getColourCode()!= null)
        {
            setColourCode(getColourCode());
        } else
        {
            setColourCode(SystemUtils.generateCode());
        }
    }
    
    @Override
    public String toString()
    {
        return colourName ;
    }

}
