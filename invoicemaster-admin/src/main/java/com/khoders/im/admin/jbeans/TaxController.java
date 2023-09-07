/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.khoders.im.admin.jbeans;

import com.khoders.im.admin.services.CompanyService;
import com.khoders.invoicemaster.entities.Tax;
import com.khoders.invoicemaster.entities.TaxGroup;
import com.khoders.resource.jpa.CrudApi;
import com.khoders.resource.utilities.CollectionList;
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
@Named(value = "taxController")
@SessionScoped
public class TaxController implements Serializable{
    @Inject CrudApi crudApi;
    @Inject CompanyService companyService;
    
    private String optionText;
    
    private Tax tax = new Tax();
    private TaxGroup taxGroup = new TaxGroup();
    private TaxGroup selectedGroup = null;
    private List<Tax> taxList = new LinkedList<>();
    private List<TaxGroup> taxGroupList = new LinkedList<>();
    
    @PostConstruct
    private void init()
    {
        clearTax();
        clearTaxGroup();
        
        taxGroupList = companyService.getTaxGroupList();
    }
    
    public void saveTax() {
        try {
            if(selectedGroup == null){
                Msg.error("Please select tax group");
                return;
            }
            tax.setTaxGroup(selectedGroup);
            if (crudApi.save(tax) != null) {
                taxList = CollectionList.washList(taxList, tax);
                Msg.info(Msg.SUCCESS_MESSAGE);
            }
            tax = new Tax();
        } catch (Exception e) {
        }
    }
    public void saveTaxGroup() {
        try {
            if (crudApi.save(taxGroup) != null) {
                taxGroupList = CollectionList.washList(taxGroupList, taxGroup);
                Msg.info(Msg.SUCCESS_MESSAGE);
            }
            clearTaxGroup();
        } catch (Exception e) {
        }
    }
   
    public void selectGroup(TaxGroup taxGroup){
        selectedGroup = taxGroup;
        optionText = "Update";
        this.taxGroup = taxGroup;
        
        taxList = companyService.getTaxList(taxGroup);
    }
    public void editTax(Tax tax) {
        optionText = "Update";
        this.tax = tax;
    }

    public void deleteTax(Tax tax) {
        try {
            if (crudApi.delete(tax)) {
                taxList.remove(tax);
                Msg.info(Msg.DELETE_MESSAGE);
            }
        } catch (Exception e) {
        }
    }

    public void deleteTaxGroup(TaxGroup taxGroup) {
        try {
            if (crudApi.delete(taxGroup)) {
                taxGroupList.remove(taxGroup);
                Msg.info(Msg.DELETE_MESSAGE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void clearTax() {
        tax = new Tax();
        selectedGroup = null;
        SystemUtils.resetJsfUI();
    }
    public void clearTaxGroup() {
        taxGroup = new TaxGroup();
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

    public List<TaxGroup> getTaxGroupList() {
        return taxGroupList;
    }

    public TaxGroup getTaxGroup() {
        return taxGroup;
    }

    public void setTaxGroup(TaxGroup taxGroup) {
        this.taxGroup = taxGroup;
    }

    public TaxGroup getSelectedGroup() {
        return selectedGroup;
    }

    public void setSelectedGroup(TaxGroup selectedGroup) {
        this.selectedGroup = selectedGroup;
    }
    
}
