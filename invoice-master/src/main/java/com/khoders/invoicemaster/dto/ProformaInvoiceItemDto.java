/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.khoders.invoicemaster.dto;

import java.io.InputStream;

/**
 *
 * @author richa
 */
public class ProformaInvoiceItemDto
{

    private String productCode;
    private String productName;
    private InputStream productImage;
    private String frameUnit;
    private String widthHeightUnits;
    private int frameSize;
    private int width;
    private int height;
    private int quantity;
    private double unitPrice;
    private double totalAmount;
    private String description;

    public String getProductName()
    {
        return productName;
    }

    public void setProductName(String productName)
    {
        this.productName = productName;
    }

    public int getQuantity()
    {
        return quantity;
    }

    public void setQuantity(int quantity)
    {
        this.quantity = quantity;
    }

    public double getUnitPrice()
    {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice)
    {
        this.unitPrice = unitPrice;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public double getTotalAmount()
    {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount)
    {
        this.totalAmount = totalAmount;
    }

    public String getProductCode()
    {
        return productCode;
    }

    public void setProductCode(String productCode)
    {
        this.productCode = productCode;
    }

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

    public String getFrameUnit()
    {
        return frameUnit;
    }

    public void setFrameUnit(String frameUnit)
    {
        this.frameUnit = frameUnit;
    }

    public String getWidthHeightUnits()
    {
        return widthHeightUnits;
    }

    public void setWidthHeightUnits(String widthHeightUnits)
    {
        this.widthHeightUnits = widthHeightUnits;
    }

    public InputStream getProductImage()
    {
        return productImage;
    }

    public void setProductImage(InputStream productImage)
    {
        this.productImage = productImage;
    }

}
