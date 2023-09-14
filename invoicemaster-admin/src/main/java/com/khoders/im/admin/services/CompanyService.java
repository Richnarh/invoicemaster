/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.khoders.im.admin.services;

import com.khoders.invoicemaster.entities.AppConfig;
import com.khoders.invoicemaster.entities.SaleLead;
import com.khoders.invoicemaster.entities.Tax;
import com.khoders.invoicemaster.entities.TaxGroup;
import com.khoders.invoicemaster.entities.UserAccount;
import com.khoders.invoicemaster.entities.system.CompanyBranch;
import com.khoders.invoicemaster.entities.system.CompanyProfile;
import com.khoders.resource.jpa.CrudApi;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author richa
 */
@Stateless
public class CompanyService
{
    @Inject private CrudApi crudApi;
    
   public List<CompanyProfile> getCompanyProfileList()
    {
       return crudApi.getEm().createQuery("SELECT e FROM CompanyProfile e", CompanyProfile.class).getResultList();
    }

    public List<CompanyBranch> getCompanyBranchList()
    {
        return crudApi.getEm().createQuery("SELECT e FROM CompanyBranch e ORDER BY e.branchName ASC", CompanyBranch.class).getResultList();
    }
    
    public List<UserAccount> getUserAccountList()
    {
       return crudApi.getEm().createQuery("SELECT e FROM UserAccount e", UserAccount.class).getResultList();
    }

    public List<SaleLead> getSaleLeadList() {
        return crudApi.getEm().createQuery("SELECT e FROM SaleLead e", SaleLead.class).getResultList();
    }
    
    public List<AppConfig> getConfigList() {
        return crudApi.getEm().createQuery("SELECT e FROM AppConfig e", AppConfig.class).getResultList();
    }
    
    public List<TaxGroup> getTaxGroupList() {
        return crudApi.getEm().createQuery("SELECT e FROM TaxGroup e ORDER BY e.groupName DESC", TaxGroup.class).getResultList();
    }
    public List<Tax> getTaxList(TaxGroup taxGroup){
       return crudApi.getEm().createQuery("SELECT e FROM Tax e WHERE e.taxGroup = :taxGroup ORDER BY e.reOrder ASC", Tax.class)
               .setParameter(Tax._taxGroup, taxGroup)
               .getResultList();
    }
}
