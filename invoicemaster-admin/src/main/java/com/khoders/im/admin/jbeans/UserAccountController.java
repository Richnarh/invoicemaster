package com.khoders.im.admin.jbeans;

import com.khoders.invoicemaster.entities.master.UserAccount;
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
 * @author 
 */
@Named(value = "userAccountController")
@SessionScoped
public class UserAccountController implements Serializable{
    @Inject private CrudApi crudApi;
    private UserAccount userAccount = new UserAccount();
    private List<UserAccount> userAccountList = new LinkedList<>();
    
    @PostConstruct
    private void init()
    {
        
    }
    
    public void saveUserAccount()
    {
        try
        {
            if (crudApi.save(userAccount) != null)
            {
                userAccountList = CollectionList.washList(userAccountList, userAccount);
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_INFO, Msg.setMsg("User saved!"), null));
            }
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    
    public void editUserAccount(UserAccount userAccount)
    {
        this.userAccount = userAccount;
    }
    
    public void deleteUserAccount(UserAccount userAccount)
    {
        try
        {
            if(crudApi.delete(userAccount))
            {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_INFO, Msg.setMsg("User deleted!"), null));
            }
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    
    public void clear()
    {
        userAccount = new UserAccount();
        SystemUtils.resetJsfUI();
    }

    public UserAccount getUserAccount()
    {
        return userAccount;
    }

    public void setUserAccount(UserAccount userAccount)
    {
        this.userAccount = userAccount;
    }

    public List<UserAccount> getUserAccountList()
    {
        return userAccountList;
    }
    
    
}
