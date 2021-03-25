/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.khoders.invoicemaster.entites;

import com.khoders.resource.jpa.BaseModel;
import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author pascal
 */
@Entity
@Table(name = "invoice_config_items")
public class InvoiceConfigItems extends BaseModel implements Serializable
{
    @JoinColumn(name = "invoice", referencedColumnName = "id")
    @ManyToOne
    private ProformaInvoice proformaInvoice;
    
    @JoinColumn(name = "delivery_term", referencedColumnName = "id")
    @ManyToOne
    private DeliveryTerm deliveryTerm;
    
    @JoinColumn(name = "validation", referencedColumnName = "id")
    @ManyToOne
    private Validation validation;
    
    @JoinColumn(name = "colours", referencedColumnName = "id")
    @ManyToOne
    private Colours colours;

    public ProformaInvoice getProformaInvoice()
    {
        return proformaInvoice;
    }

    public void setProformaInvoice(ProformaInvoice proformaInvoice)
    {
        this.proformaInvoice = proformaInvoice;
    }
    
    public DeliveryTerm getDeliveryTerm()
    {
        return deliveryTerm;
    }

    public void setDeliveryTerm(DeliveryTerm deliveryTerm)
    {
        this.deliveryTerm = deliveryTerm;
    }

    public Validation getValidation()
    {
        return validation;
    }

    public void setValidation(Validation validation)
    {
        this.validation = validation;
    }

    public Colours getColours()
    {
        return colours;
    }

    public void setColours(Colours colours)
    {
        this.colours = colours;
    }
    
    
}
