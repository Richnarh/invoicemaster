/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.khoders.invoicemaster.entities;

import com.khoders.resource.enums.DiscountType;
import com.khoders.resource.utilities.SystemUtils;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author pascal
 */
@Entity
@Table(name = "proforma_invoice_item")
public class ProformaInvoiceItem extends UserAccountRecord implements Serializable
{
    @Column(name = "item_code")
    private String itemCode;

    @JoinColumn(name = "inventory", referencedColumnName = "id")
    @ManyToOne
    private Inventory inventory;

    @Column(name = "quantity")
    private int quantity = 1;

    @Column(name = "unit_price")
    private double unitPrice;

    @Column(name = "sub_total")
    private double subTotal;

    @Column(name = "description")
    @Lob
    private String description;

    @JoinColumn(name = "proforma_invoice", referencedColumnName = "id")
    @ManyToOne
    private ProformaInvoice proformaInvoice;
    
    public int getQuantity()
    {
        return quantity;
    }

    public void setQuantity(int quantity)
    {
        this.quantity = quantity;
    }

    public ProformaInvoice getProformaInvoice()
    {
        return proformaInvoice;
    }

    public void setProformaInvoice(ProformaInvoice proformaInvoice)
    {
        this.proformaInvoice = proformaInvoice;
    }
    
    public double getUnitPrice()
    {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice)
    {
        this.unitPrice = unitPrice;
    }

    public double getSubTotal()
    {
        return subTotal;
    }

    public void setSubTotal(double subTotal)
    {
        this.subTotal = subTotal;
    }
    
    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public String getItemCode()
    {
        return itemCode;
    }

    public void setItemCode(String itemCode)
    {
        this.itemCode = itemCode;
    }

    public Inventory getInventory()
    {
        return inventory;
    }

    public void setInventory(Inventory inventory)
    {
        this.inventory = inventory;
    }

    public void genCode()
    {
        if (getItemCode() != null)
        {
            setItemCode(getItemCode());
        } else
        {
            setItemCode(SystemUtils.generateCode());
        }
    }
}
