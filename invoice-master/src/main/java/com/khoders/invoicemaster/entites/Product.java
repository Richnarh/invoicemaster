/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.khoders.invoicemaster.entites;

import com.khoders.invoicemaster.entites.enums.DoorType;
import com.khoders.resource.utilities.SystemUtils;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
        
    @JoinColumn(name = "product_type")
    @ManyToOne
    private ProductType productType;

    @Column(name = "product_image")
    private byte[] productImage;
    
    @Column(name = "image_format")
    private String imageFormat;

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

    public ProductType getProductType()
    {
        return productType;
    }

    public void setProductType(ProductType productType)
    {
        this.productType = productType;
    }

    public byte[] getProductImage()
    {
        return productImage;
    }

    public void setProductImage(byte[] productImage)
    {
        this.productImage = productImage;
    }

        
    public String getImageFormat()
    {
        return imageFormat;
    }

    public void setImageFormat(String imageFormat)
    {
        this.imageFormat = imageFormat;
    }

    public void genCode()
    {
        if (getProductCode() != null)
        {
            setProductCode(getProductCode());
        } else
        {
            setProductCode(SystemUtils.generateShortCode());
        }
    }
    @Override
    public String toString()
    {
        return productCode +" - "+ productName;
    }
}
