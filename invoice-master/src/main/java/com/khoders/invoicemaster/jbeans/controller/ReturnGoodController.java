/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.khoders.invoicemaster.jbeans.controller;

import com.khoders.invoicemaster.entities.Inventory;
import com.khoders.invoicemaster.entities.ProformaInvoice;
import com.khoders.invoicemaster.entities.ReturnGood;
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
@Named(value = "returnGoodController")
@SessionScoped
public class ReturnGoodController implements Serializable
{
    @Inject private CrudApi crudApi;
    @Inject private AppSession appSession;
    @Inject private InventoryService inventoryService;

    private ReturnGood returnGood = new ReturnGood();
    private List<ReturnGood> returnGoodList = new LinkedList<>();

    private FormView pageView = FormView.listForm();
    private String optionText;

    @PostConstruct
    private void init()
    {
        clearReturnGood();
        returnGoodList = inventoryService.getReturnGoodList();
    }

    public void initCLient()
    {
        clearReturnGood();
        pageView.restToCreateView();
    }
    
    public void updateInventory(ReturnGood returnGood)
    {
        Inventory inventory = crudApi.find(Inventory.class, returnGood.getInventory().getId());
        inventory.setQuantity(inventory.getQuantity()+returnGood.getQuantity());
        
        if(crudApi.save(inventory) != null)
        {
           returnGood.setUpdatedInventory(true);
           crudApi.save(returnGood);
           
           FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, Msg.SUCCESS_MESSAGE, null));
 
        }
        else
        {
         FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, Msg.setMsg("Oops! could not update inventory!, please try again!"), null));   
        }
    }

    public void saveReturnGood()
    {

        ProformaInvoice invoice = inventoryService.invoiceExist(returnGood.getRefNo(), returnGood.getClient());

        System.out.println("invoice => " + invoice);

        if (invoice == null)
        {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, Msg.setMsg("Please enter correct invoice no for the selected client: "+returnGood.getClient()), null));

            return;
        }

        try
        {
            returnGood.setUserAccount(appSession.getCurrentUser());
            returnGood.setCompanyBranch(appSession.getCompanyBranch());
            if (crudApi.save(returnGood) != null)
            {
                returnGoodList = CollectionList.washList(returnGoodList, returnGood);

                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_INFO, Msg.setMsg("Return good saved!"), null));
            } else
            {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, Msg.FAILED_MESSAGE, null));
            }
            clearReturnGood();
            closePage();
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public void deleteReturnGood(ReturnGood returnGood)
    {
        try
        {
            if (crudApi.delete(returnGood))
            {
                returnGoodList.remove(returnGood);

                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_INFO, Msg.SUCCESS_MESSAGE, null));
            } else
            {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, Msg.FAILED_MESSAGE, null));
            }
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public void editReturnGood(ReturnGood returnGood)
    {
        pageView.restToCreateView();
        this.returnGood = returnGood;
        optionText = "Update";
    }

    public void clearReturnGood()
    {
        returnGood = new ReturnGood();
        returnGood.setUserAccount(appSession.getCurrentUser());
        returnGood.setCompanyBranch(appSession.getCompanyBranch());
        optionText = "Save Changes";
        SystemUtils.resetJsfUI();
    }

    public void closePage()
    {
        returnGood = new ReturnGood();
        optionText = "Save Changes";
        pageView.restToListView();
    }

    public List<ReturnGood> getReturnGoodList()
    {
        return returnGoodList;
    }

    public ReturnGood getReturnGood()
    {
        return returnGood;
    }

    public void setReturnGood(ReturnGood bird)
    {
        this.returnGood = bird;
    }

    public String getOptionText()
    {
        return optionText;
    }

    public void setOptionText(String optionText)
    {
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
