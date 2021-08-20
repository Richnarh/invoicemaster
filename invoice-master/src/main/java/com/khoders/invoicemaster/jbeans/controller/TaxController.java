/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.khoders.invoicemaster.jbeans.controller;

import com.khoders.invoicemaster.entities.Tax;
import com.khoders.invoicemaster.service.ProformaInvoiceService;
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
@Named(value = "taxController")
@SessionScoped
public class TaxController implements Serializable{
    @Inject CrudApi crudApi;
    @Inject ProformaInvoiceService proformaInvoiceService;
    
    private String optionText;
    
    private Tax tax = new Tax();
    private List<Tax> taxList = new LinkedList<>();
    
    @PostConstruct
    private void init()
    {
        clearTax();
        
        taxList = proformaInvoiceService.getTaxList();
    }
    
   public void saveTax()
    {
        try 
        {
          if(crudApi.save(tax) != null)
          {
              taxList = CollectionList.washList(taxList, tax);
              
              FacesContext.getCurrentInstance().addMessage(null, 
                        new FacesMessage(FacesMessage.SEVERITY_INFO, Msg.SUCCESS_MESSAGE, null)); 
          }
          else
          {
              FacesContext.getCurrentInstance().addMessage(null, 
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, Msg.setMsg("Oops! failed to create sender Id"), null));
          }
           clearTax();
        } catch (Exception e) 
        {
            e.printStackTrace();
        }
    }
   
    public void editTax(Tax tax)
    {
       optionText = "Update";
       this.tax=tax;
    }
    
    public void deleteTax(Tax tax)
    {
        try
        {
          if(crudApi.delete(tax))
          {
              taxList.remove(tax);
              
              FacesContext.getCurrentInstance().addMessage(null, 
                        new FacesMessage(FacesMessage.SEVERITY_INFO, Msg.DELETE_MESSAGE, null)); 
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
    
    public void clearTax() {
        tax = new Tax();
        optionText = "Save Changes";
        SystemUtils.resetJsfUI();
    }

    public String getOptionText() {
        return optionText;
    }

    public void setOptionText(String optionText) {
        this.optionText = optionText;
    }

    public Tax getTax()
    {
        return tax;
    }

    public void setTax(Tax tax)
    {
        this.tax = tax;
    }

    public List<Tax> getTaxList()
    {
        return taxList;
    }
    
}
