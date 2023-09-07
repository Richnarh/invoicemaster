/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.khoders.invoicemaster.service;

import com.khoders.invoicemaster.dto.AuthRequest;
import com.khoders.invoicemaster.entities.AppConfig;
import com.khoders.invoicemaster.entities.SaleLead;
import com.khoders.invoicemaster.entities.UserAccount;
import com.khoders.invoicemaster.entities.system.CompanyBranch;
import com.khoders.invoicemaster.entities.system.CompanyProfile;
import com.khoders.resource.jpa.CrudApi;
import static com.khoders.resource.utilities.SecurityUtil.hashText;
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
    
    public UserAccount login(AuthRequest authRequest) {
        return crudApi.getEm().createQuery("SELECT e FROM UserAccount e WHERE e.email= :email AND e.password=:password", UserAccount.class)
                    .setParameter(UserAccount._email, authRequest.getEmailAddress())
                    .setParameter(UserAccount._password, hashText(authRequest.getPassword()))
                    .getResultStream().findFirst().orElse(null);
    }
}
