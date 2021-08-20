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
    
     @Column(name = "apply_discount")
    private boolean applyDiscount;
    
    @Column(name = "discount_rate")
    private double discountRate;
    
    @Column(name = "installation_fee")
    private double installationFee;

    @Column(name = "discount_type")
    @Enumerated(EnumType.STRING)
    private DiscountType discountType;

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

    public Inventory getInventory()
    {
        return inventory;
    }

    public void setInventory(Inventory inventory)
    {
        this.inventory = inventory;
    }

    public double getCharges()
    {
        return charges;
    }

    public void setCharges(double charges)
    {
        this.charges = charges;
    }

    public boolean isApplyDiscount()
    {
        return applyDiscount;
    }

    public void setApplyDiscount(boolean applyDiscount)
    {
        this.applyDiscount = applyDiscount;
    }

    public double getDiscountRate()
    {
        return discountRate;
    }

    public void setDiscountRate(double discountRate)
    {
        this.discountRate = discountRate;
    }

    public DiscountType getDiscountType()
    {
        return discountType;
    }

    public void setDiscountType(DiscountType discountType)
    {
        this.discountType = discountType;
    }

    public double getInstallationFee()
    {
        return installationFee;
    }

    public void setInstallationFee(double installationFee)
    {
        this.installationFee = installationFee;
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
