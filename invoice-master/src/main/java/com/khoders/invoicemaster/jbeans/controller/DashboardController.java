/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.khoders.invoicemaster.jbeans.controller;

import com.khoders.invoicemaster.entities.Inventory;
import com.khoders.invoicemaster.service.ProformaInvoiceService;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author richa
 */
@Named(value = "dashboardController")
@SessionScoped
public class DashboardController implements Serializable
{
    @Inject private ProformaInvoiceService proformaInvoiceService;
    private List<Inventory> inventoryList = new LinkedList<>();
    private Inventory inventory = null;
    
    @PostConstruct
    private void init()
    {
        inventoryList = proformaInvoiceService.getStockShortageList();
    }

    public List<Inventory> getInventoryList()
    {
        return inventoryList;
    }
    
    public void show(){
        this.inventory = inventoryList.get(0);
    }
    
    public void hide(){
        this.inventoryList = new LinkedList<>();
    }

    public Inventory getInventory()
    {
        return inventory;
    }

    public void setInventory(Inventory inventory)
    {
        this.inventory = inventory;
    }

    
}
