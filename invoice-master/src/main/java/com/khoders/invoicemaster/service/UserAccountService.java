/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.khoders.invoicemaster.service;

import com.khoders.invoicemaster.entites.Client;
import com.khoders.invoicemaster.entites.UserAccount;
import com.khoders.invoicemaster.jbeans.UserModel;
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
            String qryString = "SELECT e FROM UserAccount e WHERE e.username=?1 AND e.password=?2";
            TypedQuery<UserAccount> typedQuery = crudApi.getEm().createQuery(qryString, UserAccount.class)
                    .setParameter(1, userModel.getUsername())
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
    
    public boolean isTaken(String username)
    {
        String qryString = "SELECT e FROM UserAccount e WHERE e.username=?1";
        try {
            UserAccount account = crudApi.getEm().createQuery(qryString, UserAccount.class)
                    .setParameter(1, username)
                    .getSingleResult();
            
            return account != null;
        } 
        catch (Exception e) 
        {
            e.printStackTrace();
        }
        return false;
    }
    
    
    public List<Client> getClientList()
    {
        try
        {
            String qryString = "SELECT e FROM Client e";
            TypedQuery<Client> typedQuery = crudApi.getEm().createQuery(qryString, Client.class);
                            return typedQuery.getResultList();
            
        } catch (Exception e)
        {
            e.printStackTrace();
        }

        return Collections.emptyList();
    }
}
