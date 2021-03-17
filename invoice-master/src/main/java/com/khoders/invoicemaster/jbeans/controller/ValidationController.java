/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.khoders.invoicemaster.jbeans.controller;

import com.khoders.invoicemaster.entites.Validation;
import com.khoders.invoicemaster.service.InvoiceService;
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
 * @author pascal
 */
@Named(value = "validationController")
@SessionScoped
public class ValidationController implements Serializable
{
    @Inject private CrudApi crudApi;
    @Inject private InvoiceService invoiceService;
    
    
    private Validation validation = new Validation();
    private List<Validation> validationList = new LinkedList<>();
    
    private String optionText;
    private int selectedTabIndex;
    
    @PostConstruct
    private void init()
    {
        validationList = invoiceService.getValidationList();
        
        clearValidation();
    }
    
    public void saveValidation()
    {
        try
        {
           validation.genCode();
            if(crudApi.save(validation) != null)
            {
                validationList = CollectionList.washList(validationList, validation);
                FacesContext.getCurrentInstance().addMessage(null, 
                        new FacesMessage(FacesMessage.SEVERITY_INFO, Msg.SUCCESS_MESSAGE, null));
                
                clearValidation();
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
    
    public void editValidation(Validation validation)
    {
        this.validation=validation;
        optionText = "Update";
    }
    
    public void deleteValidation(Validation validation)
    {
        try
        {
            if(crudApi.delete(validation))
            {
                validationList.remove(validation);
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
    
    public void clearValidation()
    {
        validation = new Validation();
        optionText = "Save Changes";
        selectedTabIndex = 2;
        SystemUtils.resetJsfUI();
    }

    public Validation getValidation()
    {
        return validation;
    }

    public void setValidation(Validation validation)
    {
        this.validation = validation;
    }

    public String getOptionText()
    {
        return optionText;
    }

    public List<Validation> getValidationList()
    {
        return validationList;
    }

    public int getSelectedTabIndex()
    {
        return selectedTabIndex;
    }

    public void setSelectedTabIndex(int selectedTabIndex)
    {
        this.selectedTabIndex = selectedTabIndex;
    }
    
    
}
