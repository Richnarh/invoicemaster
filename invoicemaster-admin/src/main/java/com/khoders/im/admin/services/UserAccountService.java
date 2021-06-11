/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.khoders.im.admin.services;

import com.khoders.im.admin.jbeans.UserModel;
import com.khoders.invoicemaster.entities.master.UserAccount;
import com.khoders.invoicemaster.entities.master.UserPermissions;
import com.khoders.resource.jpa.CrudApi;
import static com.khoders.resource.utilities.SecurityUtil.hashText;
import java.util.Collections;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.TypedQuery;

/**
 *
 * @author Khoders
 */
@Stateless
public class UserAccountService
{
    @Inject private CrudApi crudApi;
    
    public UserAccount login(UserModel userModel)
    {
        
        try
        {
            String qryString = "SELECT e FROM UserAccount e WHERE e.email=?1 AND e.password=?2";
            TypedQuery<UserAccount> typedQuery = crudApi.getEm().createQuery(qryString, UserAccount.class)
                    .setParameter(1, userModel.getUserEmail())
                    .setParameter(2, hashText(userModel.getPassword()));
            
                 if(typedQuery.getSingleResult() != null)
                 {
                    return typedQuery.getSingleResult();
                 }

        } catch (Exception e)
        {
            e.printStackTrace();
        }

        return null;
    }
    
    public boolean isTaken(String email)
    {
        String qryString = "SELECT e FROM UserAccount e WHERE e.email=?1";
        try {
            UserAccount account = crudApi.getEm().createQuery(qryString, UserAccount.class)
                    .setParameter(1, email)
                    .getSingleResult();
            
            return account != null;
        } 
        catch (Exception e) 
        {
            e.printStackTrace();
        }
        return false;
    }
    
    public List<UserAccount> getAccountList()
    {
        try
        {
            return crudApi.getEm().createQuery("SELECT e FROM UserAccount e", UserAccount.class).getResultList();
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }

    public List<UserAccount> getUserPermissionsList(UserAccount userAccount)
    {
        try
        {
            String qryString = "SELECT e FROM UserAccount e WHERE e.email=?1";
            return crudApi.getEm().createQuery(qryString, UserAccount.class).setParameter(1, userAccount.getEmail()).getResultList();
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }
}
