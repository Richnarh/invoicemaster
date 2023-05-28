/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.khoders.im.admin.jbeans;

import com.khoders.im.admin.listener.AppSession;
import com.khoders.im.admin.services.InventoryService;
import com.khoders.invoicemaster.entities.DiscountAction;
import com.khoders.resource.jpa.CrudApi;
import com.khoders.resource.utilities.CollectionList;
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
@Named(value = "discountActionController")
@SessionScoped
public class DiscountActionController implements Serializable{
    @Inject CrudApi crudApi;
    @Inject AppSession appSession;
    @Inject InventoryService inventoryService;
    
    private String optionText;
    
    private DiscountAction discountAction = new DiscountAction();
    private DiscountAction selectedAction = null;
    
    @PostConstruct
    private void init()
    {
        clearDiscountAction();
        selectedAction = inventoryService.getDiscountAction();
    }
    
   public void saveDiscountAction()
    {
        crudApi.delete(selectedAction);
        try 
        {
          if(crudApi.save(discountAction) != null)
          {
            init();
            Msg.info(Msg.SUCCESS_MESSAGE);
          }
        } catch (Exception e) 
        {
            e.printStackTrace();
        }
    }
   
    public void editDiscountAction(DiscountAction discountAction)
    {
       optionText = "Update";
       this.discountAction=discountAction;
    }
    
    public void clearDiscountAction() {
        discountAction.setUserAccount(appSession.getCurrentUser());
        optionText = "Save Changes";
        SystemUtils.resetJsfUI();
    }

    public String getOptionText() {
        return optionText;
    }

    public void setOptionText(String optionText) {
        this.optionText = optionText;
    }

    public DiscountAction getDiscountAction()
    {
        return discountAction;
    }

    public void setDiscountAction(DiscountAction discountAction)
    {
        this.discountAction = discountAction;
    }
    
    public DiscountAction getSelectedAction() {
        return selectedAction;
    }
    
}
