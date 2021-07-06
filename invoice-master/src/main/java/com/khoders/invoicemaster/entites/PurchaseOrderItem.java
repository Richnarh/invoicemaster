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
    
    @JoinColumn(name = "purchase_order", referencedColumnName = "id")
    @ManyToOne
    private PurchaseOrder purchaseOrder;
    
    @JoinColumn(name = "product_type", referencedColumnName = "id")
    @ManyToOne
    private ProductType productType;
    
    @Column(name = "model")
    private String model;
    
    @Column(name = "accessories")
    private String accessories;
    
    @Column(name = "description")
    private String description;
    
    @Column(name = "right_qty")
    private int rightQty;
    
    @Column(name = "left_qty")
    private int leftQty;
    
    @Column(name = "unit_price")
    private double unitPrice;
    
    @Column(name = "total_price")
    private double totalPrice;
    
    @JoinColumn(name = "product", referencedColumnName = "id")
    @ManyToOne
    private Product product;

    @Column(name = "frame_size")
    private int frameSize;

    @Column(name = "wings")
    private String wings;

    @Column(name = "width")
    private int width;

    @Column(name = "height")
    private int height;

    @Column(name = "total_qty")
    private int totaltQty;
    
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

    public int getTotaltQty()
    {
        return totaltQty;
    }

    public void setTotaltQty(int totaltQty)
    {
        this.totaltQty = totaltQty;
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

    public ProductType getProductType()
    {
        return productType;
    }

    public void setProductType(ProductType productType)
    {
        this.productType = productType;
    }

    public String getModel()
    {
        return model;
    }

    public void setModel(String model)
    {
        this.model = model;
    }

    public String getAccessories()
    {
        return accessories;
    }

    public void setAccessories(String accessories)
    {
        this.accessories = accessories;
    }

    public String getWings()
    {
        return wings;
    }

    public void setWings(String wings)
    {
        this.wings = wings;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public int getRightQty()
    {
        return rightQty;
    }

    public void setRightQty(int rightQty)
    {
        this.rightQty = rightQty;
    }

    public int getLeftQty()
    {
        return leftQty;
    }

    public void setLeftQty(int leftQty)
    {
        this.leftQty = leftQty;
    }

    public double getTotalPrice()
    {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice)
    {
        this.totalPrice = totalPrice;
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
