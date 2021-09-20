/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.khoders.invoicemaster.entities;

import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author richa
 */
@Entity
@Table(name = "return_good")
public class ReturnGood extends UserAccountRecord implements Serializable
{
   @Column(name = "ref_no")
   private String refNo;
   
   @Column(name = "quantity")
   private int quantity;
   
   @Column(name = "updated_inventory")
   private boolean updatedInventory;
   
   @Column(name = "return_date")
   private LocalDate returnDate;
   
   @JoinColumn(name = "inventory", referencedColumnName = "id")
   @ManyToOne
   private Inventory inventory;
   
   @JoinColumn(name = "client", referencedColumnName = "id")
   @ManyToOne
   private Client client;
   
   @Column(name = "description")
   @Lob
   private String description;

    public String getRefNo()
    {
        return refNo;
    }

    public void setRefNo(String refNo)
    {
        this.refNo = refNo;
    }

    public LocalDate getReturnDate()
    {
        return returnDate;
    }

    public void setReturnDate(LocalDate returnDate)
    {
        this.returnDate = returnDate;
    }

    public int getQuantity()
    {
        return quantity;
    }

    public void setQuantity(int quantity)
    {
        this.quantity = quantity;
    }

    public Inventory getInventory()
    {
        return inventory;
    }

    public void setInventory(Inventory inventory)
    {
        this.inventory = inventory;
    }
    
    public Client getClient()
    {
        return client;
    }

    public void setClient(Client client)
    {
        this.client = client;
    }
    
    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public boolean isUpdatedInventory()
    {
        return updatedInventory;
    }

    public void setUpdatedInventory(boolean updatedInventory)
    {
        this.updatedInventory = updatedInventory;
    }
   
   
}
