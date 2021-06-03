/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.khoders.im.admin.commons;

import com.khoders.invoicemaster.entities.master.CompanyProfile;
import com.khoders.resource.jpa.CrudApi;
import java.io.Serializable;
import java.util.Collections;
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
   
   private List<CompanyProfile> companyProfileList = new LinkedList<>();
   
   @PostConstruct
   private void init()
   {
       getCompanyProfileList();
   }
   
   public List<CompanyProfile> getCompanyProfileList()
   {
       try
       {
           companyProfileList = crudApi.getEm().createQuery("SELECT e FROM CompanyProfile e", CompanyProfile.class).getResultList();
       } catch (Exception e)
       {
           e.printStackTrace();
           return Collections.EMPTY_LIST;
       }
       
       return Collections.emptyList();
   }
   
}
