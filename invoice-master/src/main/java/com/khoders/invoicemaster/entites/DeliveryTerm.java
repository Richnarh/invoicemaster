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
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author pascal
 */
@Entity
@Table(name = "delivery_term")
public class DeliveryTerm extends BaseModel implements Serializable
{
    @Column(name = "term_code")
    private String termCode;
    
    @Column(name = "term_name")
    @Lob
    private String deliveryTerm;
    
    @JoinColumn(name = "invoice", referencedColumnName = "id")
    @ManyToOne
    private Invoice invoice;
    
    @Column(name = "description")
    @Lob
    private String description;

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }
    
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

    public Invoice getInvoice()
    {
        return invoice;
    }

    public void setInvoice(Invoice invoice)
    {
        this.invoice = invoice;
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
