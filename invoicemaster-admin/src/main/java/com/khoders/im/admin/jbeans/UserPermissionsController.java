/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.khoders.im.admin.jbeans;

import com.khoders.im.admin.services.UserAccountService;
import com.khoders.invoicemaster.entities.UserAccount;
import com.khoders.invoicemaster.entities.system.UserPermissions;
import com.khoders.resource.jpa.CrudApi;
import com.khoders.resource.utilities.CollectionList;
import com.khoders.resource.utilities.Msg;
import com.khoders.resource.utilities.SystemUtils;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author richa
 */
@Named(value = "userPermissionsController")
@SessionScoped
public class UserPermissionsController implements Serializable
{
    @Inject private CrudApi crudApi;
    @Inject private UserAccountService userAccountService;
    
    private UserPermissions userPermissions = new UserPermissions();
    private List<UserPermissions> userPermissionsList = new LinkedList<>();
    
    private UserAccount selectedAccount;
    private List<UserAccount> userAccountList = new LinkedList<>();
    
    private String optionText;
    
    @PostConstruct
    private void init()
    {
        optionText = "Save Changes";
       userAccountList =  userAccountService.getAccountList();
    }
    
    public void selectedAccountActn(UserAccount userAccount)
    {
//        userPermissionsList = userAccountService.getUserPermissionsList(userAccount);
        selectedAccount = userAccount;
    }
    
    public void checkAll()
    {
       userPermissions.setPermSave(true);
       userPermissions.setPermUpdate(true);
       userPermissions.setPermDelete(true);
       userPermissions.setPermPrint(true);
    }
    
    public void savePermissions()
    {
        if(selectedAccount == null)
        {
            FacesContext.getCurrentInstance().addMessage(null, 
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, Msg.setMsg("Please select a user"), null)); 
            return;
        }
        
        try
        {
            userPermissions.setUserAccount(selectedAccount);
           if(crudApi.save(userPermissions)!= null)
           {
               userPermissionsList = CollectionList.washList(userPermissionsList, userPermissions);
               FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_INFO, Msg.setMsg("User permissions saved!"), null));
           }
           clearPermissions();
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    
    public void editPermissions(UserPermissions userPermissions)
    {
        this.userPermissions = userPermissions;
        optionText = "Update";
    }
    
    public void deletePermissions(UserPermissions userPermissions)
    {
        try
        {
            if(crudApi.delete(userPermissions))
            {
                userPermissionsList.remove(userPermissions);
            }
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    
    public void clearPermissions()
    {
        userPermissions = new UserPermissions();
        selectedAccount = null;
        optionText = "Save Changes";
        SystemUtils.resetJsfUI();
    }

    public UserPermissions getUserPermissions()
    {
        return userPermissions;
    }

    public void setUserPermissions(UserPermissions userPermissions)
    {
        this.userPermissions = userPermissions;
    }

    public UserAccount getSelectedAccount()
    {
        return selectedAccount;
    }

    public void setSelectedAccount(UserAccount selectedAccount)
    {
        this.selectedAccount = selectedAccount;
    }

    public String getOptionText()
    {
        return optionText;
    }

    public void setOptionText(String optionText)
    {
        this.optionText = optionText;
    }

    public List<UserPermissions> getUserPermissionsList()
    {
        return userPermissionsList;
    }

    public List<UserAccount> getUserAccountList()
    {
        return userAccountList;
    }
    
    
}
