/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.khoders.invoicemaster.entites;

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
@Table(name = "purchase_order_item")
public class PurchaseOrderItem extends UserAccountRecord
{
    @Column(name = "order_item_code")
    private String orderItemCode;
    
    @JoinColumn(name = "purchase_order")
    @ManyToOne
    private PurchaseOrder purchaseOrder;
    
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
    
    @Column(name = "unit_price")
    private double unitPrice;
    
    @Column(name = "selling_price")
    private double sellingPrice;
    
    public String getOrderItemCode()
    {
        return orderItemCode;
    }

    public void setOrderItemCode(String orderItemCode)
    {
        this.orderItemCode = orderItemCode;
    }

    public PurchaseOrder getPurchaseOrder()
    {
        return purchaseOrder;
    }

    public void setPurchaseOrder(PurchaseOrder purchaseOrder)
    {
        this.purchaseOrder = purchaseOrder;
    }

    public Product getProduct()
    {
        return product;
    }

    public void setProduct(Product product)
    {
        this.product = product;
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

    public double getSellingPrice()
    {
        return sellingPrice;
    }

    public void setSellingPrice(double sellingPrice)
    {
        this.sellingPrice = sellingPrice;
    }

    public void genCode()
    {
        if (getOrderItemCode() != null)
        {
            setOrderItemCode(getOrderItemCode());
        } else
        {
            setOrderItemCode(SystemUtils.generateRefNo());
        }
    }
}
