/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.khoders.im.admin.commons;

import com.khoders.im.admin.services.CompanyService;
import com.khoders.invoicemaster.entities.master.CompanyBranch;
import com.khoders.invoicemaster.entities.master.CompanyProfile;
import com.khoders.invoicemaster.entities.master.TaxScheme;
import com.khoders.resource.jpa.CrudApi;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author richa
 */
@Named(value = "userCommonClazz")
@SessionScoped
public class UserCommonClazz implements Serializable
{
   @Inject private CrudApi crudApi;
   @Inject private CompanyService companyService;
   
   private List<CompanyBranch> companyBranchList = new LinkedList<>();
   private List<CompanyProfile> companyProfileList = new LinkedList<>();
   private List<TaxScheme> taxSchemeList = new LinkedList<>();
   
   @PostConstruct
   public void init()
   {
       companyBranchList = companyService.getCompanyBranchList();
       companyProfileList = companyService.getCompanyProfileList();
       taxSchemeList = companyService.getTaxSchemeList();
   }

    public List<CompanyBranch> getCompanyBranchList()
    {
        return companyBranchList;
    }

    public List<CompanyProfile> getCompanyProfileList()
    {
        return companyProfileList;
    }

    public List<TaxScheme> getTaxSchemeList()
    {
        return taxSchemeList;
    }
   
}
