/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.khoders.invoicemaster.jbeans.controller;


import com.khoders.invoicemaster.Pages;
import com.khoders.invoicemaster.entities.UserAccount;
import com.khoders.invoicemaster.jbeans.UserModel;
import com.khoders.invoicemaster.listener.AppSession;
import com.khoders.invoicemaster.service.UserAccountService;
import com.khoders.resource.utilities.Msg;
import java.io.Serializable;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import org.omnifaces.util.Faces;

/**
 *
 * @author khoders
 */
@Named(value="loginController")
@RequestScoped
public class LoginController implements Serializable
{
    @Inject private AppSession appSession;
    @Inject private UserAccountService userAccountService;
    
    private String userEmail;
    private String password;
    
    private UserModel userModel = new UserModel();

    public String doLogin()
    {
        try
        {
            userModel.setUserEmail(userEmail);
            userModel.setPassword(password);

            UserAccount account = userAccountService.login(userModel);
            
            if (account == null)
            {
                FacesContext.getCurrentInstance().addMessage(null, 
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, Msg.setMsg("Wrong username or Password"), null));
                return null;
            }

            initLogin(account);

        } catch (Exception e)
        {
            e.printStackTrace();
        }

        return null;
    }
        
    public void initLogin(UserAccount userAccount)
    {
        try
        {
            if (userAccount == null)
            {
                FacesContext.getCurrentInstance().addMessage(null, 
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, Msg.setMsg("Wrong username or Password"), null));
                return;
            }
            appSession.login(userAccount);
            
            Faces.redirect(Pages.index);

        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public String doLogout()
    {
        try
        {
            
            Faces.invalidateSession();
            Faces.logout();
            
            Faces.redirect(Pages.login);
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public AppSession getAppSession()
    {
        return appSession;
    }

    public void setAppSession(AppSession appSession)
    {
        this.appSession = appSession;
    }

    public UserModel getUserModel()
    {
        return userModel;
    }

    public void setUserModel(UserModel userModel)
    {
        this.userModel = userModel;
    }
    
}