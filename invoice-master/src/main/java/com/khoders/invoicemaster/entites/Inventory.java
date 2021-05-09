/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.khoders.invoicemaster.entites;

import com.khoders.invoicemaster.entites.enums.DoorType;
import com.khoders.resource.jpa.BaseModel;
import com.khoders.resource.utilities.SystemUtils;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Lob;
import javax.persistence.Table;

/**
 *
 * @author pascal
 */
@Entity
@Table(name = "inventory")
public class Inventory extends BaseModel implements Serializable
{
    @Column(name = "inventory_code")
    private String inventoryCode;

    @Column(name = "product_name")
    private String productName;

    @Column(name = "product_code")
    private String productCode;

    @Column(name = "door_type")
    @Enumerated(EnumType.STRING)
    private DoorType doorType;

    @Column(name = "frame_size")
    private int frameSise;

    @Column(name = "width")
    private int width;

    @Column(name = "height")
    private int height;

    @Column(name = "quantity")
    private int quantity;

    @Column(name = "selling_price")
    private double sellingPrice;

    @Column(name = "cost_price")
    private double costPrice;

    @Column(name = "description")
    @Lob
    private String description;
    
    public String getProductName()
    {
        return productName;
    }

    public void setProductName(String productName)
    {
        this.productName = productName;
    }

    public DoorType getDoorType()
    {
        return doorType;
    }

    public void setDoorType(DoorType doorType)
    {
        this.doorType = doorType;
    }

    public int getFrameSise()
    {
        return frameSise;
    }

    public void setFrameSise(int frameSise)
    {
        this.frameSise = frameSise;
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

    public double getCostPrice()
    {
        return costPrice;
    }

    public void setCostPrice(double costPrice)
    {
        this.costPrice = costPrice;
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

    public String getProductCode()
    {
        return productCode;
    }

    public void setProductCode(String productCode)
    {
        this.productCode = productCode;
    }

    @Override
    public String toString()
    {
        return productName;
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
