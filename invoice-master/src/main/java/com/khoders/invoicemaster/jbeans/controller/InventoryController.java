/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.khoders.invoicemaster.jbeans.controller;

import com.khoders.invoicemaster.entities.Inventory;
import com.khoders.invoicemaster.listener.AppSession;
import com.khoders.invoicemaster.service.InventoryService;
import com.khoders.resource.jpa.CrudApi;
import com.khoders.resource.utilities.CollectionList;
import com.khoders.resource.utilities.FormView;
import com.khoders.resource.utilities.Msg;
import com.khoders.resource.utilities.SystemUtils;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author khoders
 */
@Named(value = "inventoryController")
@SessionScoped
public class InventoryController implements Serializable{
    @Inject CrudApi crudApi;
    @Inject AppSession appSession;
    @Inject InventoryService inventoryService;
    
    private Inventory inventory = new Inventory();
    private List<Inventory> inventoryList =  new LinkedList<>();
    
    private FormView pageView = FormView.listForm();
    private String optionText;
    
    @PostConstruct
    private void init()
    {
        inventoryList = inventoryService.getInventoryList();
        
        clearInventory();
    }
    
    public void initInventory()
    {
        clearInventory();
        pageView.restToCreateView();
    }
    
    public void saveInventory()
    {
        try 
        {
           inventory.genCode();
          if(crudApi.save(inventory) != null)
          {
              inventoryList = CollectionList.washList(inventoryList, inventory);
              
              FacesContext.getCurrentInstance().addMessage(null, 
                        new FacesMessage(FacesMessage.SEVERITY_INFO, Msg.SUCCESS_MESSAGE, null)); 
          }
          else
          {
              FacesContext.getCurrentInstance().addMessage(null, 
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, Msg.FAILED_MESSAGE, null));
          }
          closePage();
        } catch (Exception e) 
        {
            e.printStackTrace();
        }
    }
    
    public void deleteInventory(Inventory inventory)
    {
        try 
        {
          if(crudApi.delete(inventory))
          {
              inventoryList.remove(inventory);
              
              FacesContext.getCurrentInstance().addMessage(null, 
                        new FacesMessage(FacesMessage.SEVERITY_INFO, Msg.SUCCESS_MESSAGE, null)); 
          }
          else
          {
              FacesContext.getCurrentInstance().addMessage(null, 
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, Msg.FAILED_MESSAGE, null));
          }
        } catch (Exception e) 
        {
            e.printStackTrace();
        }
    }
    
    public void editInventory(Inventory inventory)
    {
       pageView.restToCreateView();
       this.inventory=inventory;
       optionText = "Update";
    }
    
    public void clearInventory() 
    {
        inventory = new Inventory();
        inventory.setUserAccount(appSession.getCurrentUser());
        inventory.setCompanyBranch(appSession.getCompanyBranch());
        optionText = "Save Changes";
        SystemUtils.resetJsfUI();
    }
    
    public void closePage()
    {
       inventory = new Inventory();
       optionText = "Save Changes";
       pageView.restToListView();
    }
    public List<Inventory> getInventoryList() {
        return inventoryList;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public void setInventory(Inventory bird) {
        this.inventory = bird;
    }

    public String getOptionText() {
        return optionText;
    }

    public void setOptionText(String optionText) {
        this.optionText = optionText;
    }

    public FormView getPageView()
    {
        return pageView;
    }

    public void setPageView(FormView pageView)
    {
        this.pageView = pageView;
    }

}
