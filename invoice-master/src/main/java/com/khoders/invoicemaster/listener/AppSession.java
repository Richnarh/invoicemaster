/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.khoders.invoicemaster.listener;

import com.khoders.invoicemaster.entities.master.UserAccount;
import java.io.Serializable;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

/**
 *
 * @author khoders
 */
@Named(value = "appSession")
@SessionScoped
public class AppSession implements Serializable{
    private UserAccount currentUser;
    
    public void login(UserAccount userAccount)
    {
        System.out.println("Currentuser Email => "+userAccount.getEmail());
        System.out.println("Currentuser => "+userAccount);
        this.currentUser = userAccount;
        
        System.out.println("this.currentUser => "+this.currentUser);
    }
    
    public void logout()
    {
        this.currentUser = null;
    }

    public UserAccount getCurrentUser()
    {
        return currentUser;
    }

    public void setCurrentUser(UserAccount currentUser)
    {
        this.currentUser = currentUser;
    }
    
}
