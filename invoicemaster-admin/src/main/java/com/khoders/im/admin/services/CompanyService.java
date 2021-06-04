/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.khoders.im.admin.services;

import com.khoders.invoicemaster.entities.master.CompanyBranch;
import com.khoders.invoicemaster.entities.master.CompanyProfile;
import com.khoders.resource.jpa.CrudApi;
import java.util.Collections;
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
        try
        {
            return crudApi.getEm().createQuery("SELECT e FROM CompanyProfile e", CompanyProfile.class).getResultList();
        } catch (Exception e)
        {
            e.printStackTrace();
        }

        return Collections.emptyList();
    }

    public List<CompanyBranch> getCompanyBranchList()
    {
        try
        {
            return crudApi.getEm().createQuery("SELECT e FROM CompanyBranch e ORDER BY e.branchName ASC", CompanyBranch.class).getResultList();
        } catch (Exception e)
        {
            e.printStackTrace();
        }

        return Collections.emptyList();
    }
}
