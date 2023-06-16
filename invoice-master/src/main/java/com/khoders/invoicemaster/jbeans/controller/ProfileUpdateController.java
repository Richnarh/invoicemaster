/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.khoders.invoicemaster.jbeans.controller;

import com.khoders.invoicemaster.Pages;
import com.khoders.invoicemaster.entities.UserAccount;
import com.khoders.invoicemaster.listener.AppSession;
import com.khoders.resource.jpa.CrudApi;
import com.khoders.resource.utilities.Msg;
import com.khoders.resource.utilities.SecurityUtil;
import java.io.Serializable;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import org.omnifaces.util.Faces;

/**
 *
 * @author khoders
 */
@Named(value = "profileUpdateController")
@SessionScoped
public class ProfileUpdateController implements Serializable{
    @Inject private CrudApi crudApi;
    @Inject private AppSession appSession;
    
    private String password;
    private String confirmPassword;
    
    public void updateAccount()
    {
        try 
        {
            if(appSession.getCurrentUser() != null)
            {
                crudApi.save(appSession.getCurrentUser());
                Msg.info(Msg.SUCCESS_MESSAGE);
            }
            else
            {
              Msg.error(Msg.DELETE_MESSAGE);
            }
        } 
        catch (Exception e) 
        {
            e.printStackTrace();
        }
    }
    
    public void activateVersion(){
        UserAccount userAccount = appSession.getCurrentUser();
        System.out.println("Version: "+appSession.getCurrentUser().getAppVersion());
        userAccount.setAppVersion(appSession.getCurrentUser().getAppVersion());
        crudApi.save(userAccount);
        
        appSession.login(userAccount);
        appSession.initBranch(userAccount.getCompanyBranch());
        
        initLogin(userAccount);
    }
       
    public void updatePassword()
    {
        if(!password.equals(confirmPassword))
        {
            Msg.error("Password do not match");
            return;
        }
        
        String hashedPassword = SecurityUtil.hashText(password);
        
        if(hashedPassword.equalsIgnoreCase(appSession.getCurrentUser().getPassword()))
        {
            Msg.error("This password is same as the old one, please use a new password");
            return;
        }
        
        
        appSession.getCurrentUser().setPassword(hashedPassword);
        
        if(crudApi.save(appSession.getCurrentUser()) != null)
        {
           Msg.info("Password Reset successful!");
        }
    }

    public void initLogin(UserAccount userAccount)
    {
        try
        {
            if(userAccount == null){
              Msg.error("Wrong username or Password");
              return;
            }
            appSession.login(userAccount);
            appSession.initBranch(userAccount.getCompanyBranch());
            
            if(userAccount.isQuickInvoice() == true)
            {
                Faces.redirect(Pages.quickInvoice);
            }
            else if(userAccount.isWaybill() == true)
            {
               Faces.redirect(Pages.waybill);
            }
            else
            {
               Faces.redirect(Pages.index);
            }
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }
        
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
    
    
}
