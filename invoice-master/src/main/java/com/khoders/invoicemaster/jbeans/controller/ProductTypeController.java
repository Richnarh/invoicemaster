/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.khoders.invoicemaster.jbeans.controller;

import com.khoders.invoicemaster.entites.ProductType;
import com.khoders.invoicemaster.listener.AppSession;
import com.khoders.invoicemaster.service.InventoryService;
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
@Named(value = "productTypeController")
@SessionScoped
public class ProductTypeController implements Serializable{
    @Inject CrudApi crudApi;
    @Inject AppSession appSession;
    @Inject InventoryService inventoryService;
    
    private String optionText;
    
    private ProductType productType = new ProductType();
    private List<ProductType> productTypeList = new LinkedList<>();
    
    @PostConstruct
    private void init()
    {
        clearProductType();
        
        productTypeList = inventoryService.getProductTypeList();
    }
    
   public void saveProductType()
    {
        try 
        {
          if(crudApi.save(productType) != null)
          {
              productTypeList = CollectionList.washList(productTypeList, productType);
              
              FacesContext.getCurrentInstance().addMessage(null, 
                        new FacesMessage(FacesMessage.SEVERITY_INFO, Msg.SUCCESS_MESSAGE, null)); 
          }
          else
          {
              FacesContext.getCurrentInstance().addMessage(null, 
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, Msg.setMsg("Oops! failed to create sender Id"), null));
          }
           clearProductType();
        } catch (Exception e) 
        {
            e.printStackTrace();
        }
    }
   
    public void editProductType(ProductType productType)
    {
       optionText = "Update";
       this.productType=productType;
    }
    
    public void deleteProductType(ProductType productType)
    {
        try
        {
          if(crudApi.delete(productType))
          {
              productTypeList.remove(productType);
              
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
    
    public void clearProductType() {
        productType = new ProductType();
        productType.setUserAccount(appSession.getCurrentUser());
        optionText = "Save Changes";
        SystemUtils.resetJsfUI();
    }

    public String getOptionText() {
        return optionText;
    }

    public void setOptionText(String optionText) {
        this.optionText = optionText;
    }

    public ProductType getProductType()
    {
        return productType;
    }

    public void setProductType(ProductType productType)
    {
        this.productType = productType;
    }

    public List<ProductType> getProductTypeList()
    {
        return productTypeList;
    }
    
}
