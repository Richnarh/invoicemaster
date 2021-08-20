/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.khoders.invoicemaster.entities;

import com.khoders.resource.utilities.SystemUtils;
import java.time.LocalDate;
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
@Table(name = "purchase_order")
public class PurchaseOrder extends UserAccountRecord
{
    @Column(name = "purchase_order_code")
    private String purchaseOrderCode = SystemUtils.generateCode();
    
    @Column(name = "total_amount")
    private double totalAmount;
    
    @JoinColumn(name = "client", referencedColumnName = "id")
    @ManyToOne
    private Client client;
    
    @Column(name = "received_date")
    private LocalDate receivedDate = LocalDate.now();

    @Column(name = "posted_to_inventory")
    private boolean postedToInventory;
    
    @Column(name = "description")
    private String description;

    public String getPurchaseOrderCode()
    {
        return purchaseOrderCode;
    }

    public void setPurchaseOrderCode(String purchaseOrderCode)
    {
        this.purchaseOrderCode = purchaseOrderCode;
    }
    
    public double getTotalAmount()
    {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount)
    {
        this.totalAmount = totalAmount;
    }

    public Client getClient()
    {
        return client;
    }

    public void setClient(Client client)
    {
        this.client = client;
    }

    public LocalDate getReceivedDate()
    {
        return receivedDate;
    }

    public void setReceivedDate(LocalDate receivedDate)
    {
        this.receivedDate = receivedDate;
    }

    public boolean isPostedToInventory()
    {
        return postedToInventory;
    }

    public void setPostedToInventory(boolean postedToInventory)
    {
        this.postedToInventory = postedToInventory;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }
    
}
