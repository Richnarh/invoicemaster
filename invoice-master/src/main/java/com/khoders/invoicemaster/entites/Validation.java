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
@Table(name = "validation")
public class Validation extends BaseModel implements Serializable
{
    @Column(name = "validation_code")
    private String validationCode;
    
    @Column(name = "validation")
    private String validation;
    
    public String getValidation()
    {
        return validation;
    }

    public void setValidation(String validation)
    {
        this.validation = validation;
    }

    public String getValidationCode()
    {
        return validationCode;
    }

    public void setValidationCode(String validationCode)
    {
        this.validationCode = validationCode;
    }

   public void genCode()
    {
        if (getValidationCode() != null)
        {
            setValidationCode(getValidationCode());
        } else
        {
            setValidationCode(SystemUtils.generateCode());
        }
    }
    @Override
    public String toString()
    {
        return validation ;
    }

    

}
