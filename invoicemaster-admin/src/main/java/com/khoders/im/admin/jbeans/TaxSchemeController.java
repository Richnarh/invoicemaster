/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.khoders.im.admin.jbeans;

import com.khoders.im.admin.services.CompanyService;
import com.khoders.invoicemaster.entities.master.TaxScheme;
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
@Named(value = "taxSchemeController")
@SessionScoped
public class TaxSchemeController implements Serializable{
    @Inject CrudApi crudApi;
    @Inject CompanyService companyService;
    
    private String optionText;
    
    private TaxScheme taxScheme = new TaxScheme();
    private List<TaxScheme> taxSchemeList = new LinkedList<>();
    
    @PostConstruct
    private void init()
    {
        clearTaxScheme();
        
        taxSchemeList = companyService.getTaxSchemeList();
    }
    
   public void saveTaxScheme()
    {
        try 
        {
          taxScheme.genCode();
          
          if(crudApi.save(taxScheme) != null)
          {
              taxSchemeList = CollectionList.washList(taxSchemeList, taxScheme);
              
              FacesContext.getCurrentInstance().addMessage(null, 
                        new FacesMessage(FacesMessage.SEVERITY_INFO, Msg.SUCCESS_MESSAGE, null)); 
          }
          else
          {
              FacesContext.getCurrentInstance().addMessage(null, 
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, Msg.setMsg("Oops! failed to create sender Id"), null));
          }
           clearTaxScheme();
        } catch (Exception e) 
        {
            e.printStackTrace();
        }
    }
   
    public void editTaxScheme(TaxScheme taxScheme)
    {
       optionText = "Update";
       this.taxScheme=taxScheme;
    }
    
    public void deleteTaxScheme(TaxScheme taxScheme)
    {
        try
        {
          if(crudApi.delete(taxScheme))
          {
              taxSchemeList.remove(taxScheme);
              
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
    
    public void clearTaxScheme() {
        taxScheme = new TaxScheme();
        optionText = "Save Changes";
        SystemUtils.resetJsfUI();
    }

    public String getOptionText() {
        return optionText;
    }

    public void setOptionText(String optionText) {
        this.optionText = optionText;
    }

    public TaxScheme getTaxScheme()
    {
        return taxScheme;
    }

    public void setTaxScheme(TaxScheme taxScheme)
    {
        this.taxScheme = taxScheme;
    }

    public List<TaxScheme> getTaxSchemeList()
    {
        return taxSchemeList;
    }

}
