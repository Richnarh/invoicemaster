/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.khoders.invoicemaster.entites;

import com.khoders.resource.utilities.SystemUtils;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;

/**
 *
 * @author pascal
 */
@Entity
@Table(name = "delivery_term")
public class DeliveryTerm extends UserAccountRecord implements Serializable
{
    @Column(name = "term_code")
    private String termCode;
    
    @Column(name = "term_name")
    @Lob
    private String deliveryTerm;
    
    public String getTermCode()
    {
        return termCode;
    }

    public void setTermCode(String termCode)
    {
        this.termCode = termCode;
    }

    public String getDeliveryTerm()
    {
        return deliveryTerm;
    }

    public void setDeliveryTerm(String deliveryTerm)
    {
        this.deliveryTerm = deliveryTerm;
    }

    public void genCode()
    {
        if (getTermCode() != null)
        {
            setTermCode(getTermCode());
        } else
        {
            setTermCode(SystemUtils.generateCode());
        }
    }

    @Override
    public String toString()
    {
        return deliveryTerm ;
    }

    

}
