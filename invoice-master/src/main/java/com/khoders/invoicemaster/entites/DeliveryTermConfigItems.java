/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.khoders.invoicemaster.entites;

import com.khoders.resource.jpa.BaseModel;
import java.io.Serializable;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author pascal
 */
@Entity
@Table(name = "deliveryTerm_config_items")
public class DeliveryTermConfigItems extends UserAccountRecord implements Serializable
{
    @JoinColumn(name = "proforma_invoice", referencedColumnName = "id")
    @ManyToOne
    private ProformaInvoice proformaInvoice;
    
    @JoinColumn(name = "delivery_term", referencedColumnName = "id")
    @ManyToOne()
    private DeliveryTerm deliveryTerm;
    
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
}
