/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.khoders.invoicemaster.entites;

import com.khoders.resource.jpa.BaseModel;
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
@Table(name = "delivery_term")
public class DeliveryTerm extends BaseModel implements Serializable
{
    @Column(name = "term_name")
    private String deliveryTerm;
    
    @Column(name = "validation")
    private String validation;
    
    @JoinColumn(name = "invoice", referencedColumnName = "id")
    @ManyToOne
    private Invoice invoice;

    public String getDeliveryTerm()
    {
        return deliveryTerm;
    }

    public void setDeliveryTerm(String deliveryTerm)
    {
        this.deliveryTerm = deliveryTerm;
    }

    public String getValidation()
    {
        return validation;
    }

    public void setValidation(String validation)
    {
        this.validation = validation;
    }

    public Invoice getInvoice()
    {
        return invoice;
    }

    public void setInvoice(Invoice invoice)
    {
        this.invoice = invoice;
    }


}
