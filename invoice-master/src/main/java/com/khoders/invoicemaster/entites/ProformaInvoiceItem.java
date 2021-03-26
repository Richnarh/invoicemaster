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
@Table(name = "proforma_invoice_item")
public class ProformaInvoiceItem extends BaseModel implements Serializable
{

    @Column(name = "item_code")
    private String itemCode;

    @JoinColumn(name = "inventory_product", referencedColumnName = "id")
    @ManyToOne
    private Inventory inventoryProduct;

    @Column(name = "quantity")
    private int quantity;

    @Column(name = "unit_price")
    private double unitPrice;

    @Column(name = "charges")
    private double charges;

    @Column(name = "total_amount")
    private double totalAmount;

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

    public double getTotalAmount()
    {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount)
    {
        this.totalAmount = totalAmount;
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

    public Inventory getInventoryProduct()
    {
        return inventoryProduct;
    }

    public void setInventoryProduct(Inventory inventoryProduct)
    {
        this.inventoryProduct = inventoryProduct;
    }

    public double getCharges()
    {
        return charges;
    }

    public void setCharges(double charges)
    {
        this.charges = charges;
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
