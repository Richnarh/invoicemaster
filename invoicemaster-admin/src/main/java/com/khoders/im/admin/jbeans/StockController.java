/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.khoders.im.admin.jbeans;

import com.khoders.im.admin.services.InvoiceService;
import com.khoders.invoicemaster.entities.Inventory;
import com.khoders.invoicemaster.entities.system.CompanyBranch;
import com.khoders.resource.jpa.CrudApi;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author richa
 */
@Named(value = "stockController")
@SessionScoped
public class StockController implements Serializable
{
    @Inject private CrudApi crudApi;
    @Inject private InvoiceService invoiceService;
    
    private Inventory inventory = new Inventory();
    private List<Inventory> inventoryList = new LinkedList<>();
    private List<Inventory> inventoryShortageStockList = new LinkedList<>();
    
    private CompanyBranch selectedBranch = new CompanyBranch();
    
    public void stockQtyPerBranch()
    {
        inventoryList = invoiceService.getStockList(selectedBranch);
        inventoryShortageStockList = invoiceService.getStockShortageList(selectedBranch);
    }
    
    public void clearPage()
    {
       selectedBranch = new CompanyBranch();
       inventoryList = new LinkedList<>();
       inventoryShortageStockList = new LinkedList<>();
    }

    public List<Inventory> getInventoryList()
    {
        return inventoryList;
    }

    public List<Inventory> getInventoryShortageStockList()
    {
        return inventoryShortageStockList;
    }

    public CompanyBranch getSelectedBranch()
    {
        return selectedBranch;
    }

    public void setSelectedBranch(CompanyBranch selectedBranch)
    {
        this.selectedBranch = selectedBranch;
    }
    
}
