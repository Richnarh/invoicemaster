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
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author pascal
 */
@Entity
@Table(name = "inventory")
public class Inventory extends UserAccountRecord implements Serializable
{
    @Column(name = "inventory_code")
    private String inventoryCode;
    
    @JoinColumn(name = "product", referencedColumnName = "id")
    @ManyToOne
    private Product product;

    @Column(name = "frame_size")
    private int frameSize;

    @Column(name = "width")
    private int width;

    @Column(name = "height")
    private int height;

    @Column(name = "quantity")
    private int quantity;

    @Column(name = "selling_price")
    private double sellingPrice;

    @Column(name = "unit_price")
    private double unitPrice;

    @Column(name = "description")
    @Lob
    private String description;
    
    public int getFrameSize()
    {
        return frameSize;
    }

    public void setFrameSize(int frameSize)
    {
        this.frameSize = frameSize;
    }

    public int getWidth()
    {
        return width;
    }

    public void setWidth(int width)
    {
        this.width = width;
    }

    public int getHeight()
    {
        return height;
    }

    public void setHeight(int height)
    {
        this.height = height;
    }

    public int getQuantity()
    {
        return quantity;
    }

    public void setQuantity(int quantity)
    {
        this.quantity = quantity;
    }

    public double getSellingPrice()
    {
        return sellingPrice;
    }

    public void setSellingPrice(double sellingPrice)
    {
        this.sellingPrice = sellingPrice;
    }

    public double getUnitPrice()
    {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice)
    {
        this.unitPrice = unitPrice;
    }

    public String getInventoryCode()
    {
        return inventoryCode;
    }

    public void setInventoryCode(String inventoryCode)
    {
        this.inventoryCode = inventoryCode;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public Product getProduct()
    {
        return product;
    }

    public void setProduct(Product product)
    {
        this.product = product;
    }
    
    @Override
    public String toString()
    {
        return product+"";
    }
    
    public void genCode()
    {
        if (getInventoryCode() != null)
        {
            setInventoryCode(getInventoryCode());
        } else
        {
            setInventoryCode(SystemUtils.generateCode());
        }
    }
}
