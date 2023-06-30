/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.khoders.invoicemaster.dto;

/**
 *
 * @author richa
 */
public class SaleItemDto extends BaseDto
{
    private String inventory;
    private String inventoryId;
    private double price;
    private int quantity;
    private double subTotal;

    public String getInventory()
    {
        return inventory;
    }

    public void setInventory(String inventory)
    {
        this.inventory = inventory;
    }

    public String getInventoryId()
    {
        return inventoryId;
    }

    public void setInventoryId(String inventoryId)
    {
        this.inventoryId = inventoryId;
    }

    public double getPrice()
    {
        return price;
    }

    public void setPrice(double price)
    {
        this.price = price;
    }

    public int getQuantity()
    {
        return quantity;
    }

    public void setQuantity(int quantity)
    {
        this.quantity = quantity;
    }

    public double getSubTotal()
    {
        return subTotal;
    }

    public void setSubTotal(double subTotal)
    {
        this.subTotal = subTotal;
    }
    
}
