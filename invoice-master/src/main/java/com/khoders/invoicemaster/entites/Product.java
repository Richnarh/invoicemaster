/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.khoders.invoicemaster.entites;

import com.khoders.invoicemaster.entites.enums.DoorType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

/**
 *
 * @author richa
 */
@Entity
@Table(name = "product")
public class Product extends UserAccountRecord
{
    @Column(name = "product_name")
    private String productName;

    @Column(name = "product_code")
    private String productCode;
    
    @Column(name = "door_type")
    @Enumerated(EnumType.STRING)
    private DoorType doorType;


    public String getProductName()
    {
        return productName;
    }

    public void setProductName(String productName)
    {
        this.productName = productName;
    }

    public String getProductCode()
    {
        return productCode;
    }

    public void setProductCode(String productCode)
    {
        this.productCode = productCode;
    }

    public DoorType getDoorType()
    {
        return doorType;
    }

    public void setDoorType(DoorType doorType)
    {
        this.doorType = doorType;
    }

    @Override
    public String toString()
    {
        return productCode +" - "+ productName;
    }
}
