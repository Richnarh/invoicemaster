/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.khoders.invoicemaster.service;

import com.khoders.invoicemaster.entities.UserAccount;
import com.khoders.invoicemaster.entities.system.CompanyBranch;
import com.khoders.resource.exception.DataNotFoundException;
import com.khoders.resource.jpa.CrudApi;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author Pascal
 */
@Stateless
public class AppService {
    @Inject private CrudApi crudApi;
    
    public UserAccount getUser(String userAccountId){
        if(userAccountId == null){
           throw new DataNotFoundException("userAccountId is required");
        }
        UserAccount user =  crudApi.find(UserAccount.class, userAccountId);
        if(user == null){
           throw new DataNotFoundException("User with the ID: "+userAccountId +" cannot be found!");
        }
        return user;
    }
    public CompanyBranch getBranch(String branchId){
        if(branchId == null){
           throw new DataNotFoundException("companyBranchId is required");
        }
        CompanyBranch branch =  crudApi.find(CompanyBranch.class, branchId);
        if(branch == null){
           throw new DataNotFoundException("User with the ID: "+branchId +" cannot be found!");
        }
        return branch;
    }
}
