/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.khoders.im.admin.jbeans;

import com.khoders.im.admin.listener.AppSession;
import com.khoders.im.admin.services.CompanyService;
import com.khoders.invoicemaster.entities.SaleLead;
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
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author khoders
 */
@Named(value = "saleLeadController")
@SessionScoped
public class SaleLeadController implements Serializable{
    @Inject CrudApi crudApi;
    @Inject AppSession appSession;
    @Inject CompanyService companyService;
    
    private SaleLead saleLead = new SaleLead();
    private List<SaleLead> saleLeadList =  new LinkedList<>();
    
    private FormView pageView = FormView.listForm();
    private String optionText;
    
    @PostConstruct
    private void init()
    {
        saleLeadList = companyService.getSaleLeadList();
        clearSaleLead();
    }
    
    public void initSaleLead()
    {
        clearSaleLead();
        pageView.restToCreateView();
    }
    
    public void saveSaleLead()
    {
        try 
        {
          if(crudApi.save(saleLead) != null)
          {
              saleLeadList = CollectionList.washList(saleLeadList, saleLead);
              Msg.info(Msg.SUCCESS_MESSAGE);
          }
          else
          {
            Msg.error(Msg.FAILED_MESSAGE);
          }
          closePage();
        } catch (Exception e) 
        {
            e.printStackTrace();
        }
    }
    
    public void deleteSaleLead(SaleLead saleLead)
    {
        try 
        {
          if(crudApi.delete(saleLead))
          {
              saleLeadList.remove(saleLead);
               Msg.info(Msg.SUCCESS_MESSAGE);
          }
          else
          {
            Msg.error(Msg.FAILED_MESSAGE);
          }
        } catch (Exception e) 
        {
            e.printStackTrace();
        }
    }
    
    public void editSaleLead(SaleLead saleLead)
    {
       pageView.restToCreateView();
       this.saleLead=saleLead;
       optionText = "Update";
    }
    
    public void clearSaleLead() 
    {
        saleLead = new SaleLead();
        saleLead.setUserAccount(appSession.getCurrentUser());
        optionText = "Save Changes";
        SystemUtils.resetJsfUI();
    }
    
    public void closePage()
    {
       saleLead = new SaleLead();
       optionText = "Save Changes";
       pageView.restToListView();
    }
    public List<SaleLead> getSaleLeadList() {
        return saleLeadList;
    }

    public SaleLead getSaleLead() {
        return saleLead;
    }

    public void setSaleLead(SaleLead bird) {
        this.saleLead = bird;
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
