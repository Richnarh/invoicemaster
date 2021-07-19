/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.khoders.im.admin.jbeans;

import com.khoders.invoicemaster.entities.master.VatScheme;
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
@Named(value = "vatSchemeController")
@SessionScoped
public class VatSchemeController implements Serializable{
    @Inject CrudApi crudApi;
    
    private String optionText;
    
    private VatScheme vatScheme = new VatScheme();
    private List<VatScheme> vatSchemeList = new LinkedList<>();
    
    @PostConstruct
    private void init()
    {
        clearVatScheme();
        
//        vatSchemeList = smsService.getVatSchemeList();
    }
    
   public void saveVatScheme()
    {
        try 
        {
          vatScheme.genCode();
          
          if(crudApi.save(vatScheme) != null)
          {
              vatSchemeList = CollectionList.washList(vatSchemeList, vatScheme);
              
              FacesContext.getCurrentInstance().addMessage(null, 
                        new FacesMessage(FacesMessage.SEVERITY_INFO, Msg.SUCCESS_MESSAGE, null)); 
          }
          else
          {
              FacesContext.getCurrentInstance().addMessage(null, 
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, Msg.setMsg("Oops! failed to create sender Id"), null));
          }
           clearVatScheme();
        } catch (Exception e) 
        {
            e.printStackTrace();
        }
    }
   
    public void editVatScheme(VatScheme vatScheme)
    {
       optionText = "Update";
       this.vatScheme=vatScheme;
    }
    
    public void deleteVatScheme(VatScheme vatScheme)
    {
        try
        {
          if(crudApi.delete(vatScheme))
          {
              vatSchemeList.remove(vatScheme);
              
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
    
    public void clearVatScheme() {
        vatScheme = new VatScheme();
        optionText = "Save Changes";
        SystemUtils.resetJsfUI();
    }

    public String getOptionText() {
        return optionText;
    }

    public void setOptionText(String optionText) {
        this.optionText = optionText;
    }

    public VatScheme getVatScheme()
    {
        return vatScheme;
    }

    public void setVatScheme(VatScheme vatScheme)
    {
        this.vatScheme = vatScheme;
    }

    public List<VatScheme> getVatSchemeList()
    {
        return vatSchemeList;
    }

}
