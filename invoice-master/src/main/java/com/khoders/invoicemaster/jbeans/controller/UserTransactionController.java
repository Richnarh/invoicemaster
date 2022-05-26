/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.khoders.invoicemaster.jbeans.controller;

import com.khoders.invoicemaster.entities.OnlineClient;
import com.khoders.invoicemaster.entities.UserTransaction;
import com.khoders.invoicemaster.service.ClientService;
import com.khoders.resource.jpa.CrudApi;
import com.khoders.resource.utilities.CollectionList;
import com.khoders.resource.utilities.FormView;
import com.khoders.resource.utilities.Msg;
import com.khoders.resource.utilities.SystemUtils;
import java.io.Serializable;
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
@Named(value = "userTransactionController")
@SessionScoped
public class UserTransactionController implements Serializable
{
    @Inject private CrudApi crudApi;
    @Inject private ClientService clientService;
    private UserTransaction userTransaction = new UserTransaction();
    private OnlineClient onlineClient = new OnlineClient();
    private OnlineClient selectedOnlineClient = null;
    private List<UserTransaction> userTransactionList = new LinkedList<>();
    private List<OnlineClient> onlineClientList = new LinkedList<>();
    
    private FormView pageView = FormView.listForm();
    
    private String optionText;
    
    @PostConstruct
    private void init()
    {
       clearOnlineClient();
       clear();
       
       onlineClientList = clientService.getOnlineClientList();
    }
    
    public void saveUserTransaction(){
        try
        {
            System.out.println("Data-- "+userTransaction.getPaymentMethod());
            if(crudApi.save(userTransaction) != null)
            {
                userTransactionList = CollectionList.washList(userTransactionList, userTransaction);
                Msg.info(Msg.SUCCESS_MESSAGE);
            }
            else
            {
                Msg.error(Msg.FAILED_MESSAGE);
            }
            clear();
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    
    public void editUserTransaction(UserTransaction userTransaction){
        this.userTransaction=userTransaction;
        optionText = "Update";
    }
    
    public void deleteUserTransaction(UserTransaction userTransaction){
        try
        {
            if(crudApi.delete(userTransaction))
            {
                userTransactionList.remove(userTransaction);
                Msg.info(Msg.SUCCESS_MESSAGE);
            }
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    
    public void manageTransaction(OnlineClient onlineClient)
    {
        this.selectedOnlineClient = onlineClient;
        pageView.restToCreateView();
        clear();
        userTransactionList = clientService.userTransactionList(onlineClient);
    }
    
    public void clear()
    {
       userTransaction = new UserTransaction();
       userTransaction.setOnlineClient(selectedOnlineClient);
       optionText = "Save Changes";
        SystemUtils.resetJsfUI();
    }
    
    public void clearOnlineClient()
    {
       selectedOnlineClient = new OnlineClient();
       onlineClientList = new LinkedList<>(); 
       optionText = "Save Changes";
       SystemUtils.resetJsfUI();
    }

    public void closePage()
    {
        clear();
        
        userTransactionList = new LinkedList<>(); 
        pageView.restToListView();
    }
    public UserTransaction getUserTransaction()
    {
        return userTransaction;
    }

    public void setUserTransaction(UserTransaction userTransaction)
    {
        this.userTransaction = userTransaction;
    }

    public String getOptionText()
    {
        return optionText;
    }

    public void setOptionText(String optionText)
    {
        this.optionText = optionText;
    }

    public List<UserTransaction> getUserTransactionList()
    {
        return userTransactionList;
    }

    public List<OnlineClient> getOnlineClientList()
    {
        return onlineClientList;
    }

    public FormView getPageView()
    {
        return pageView;
    }

    public void setPageView(FormView pageView)
    {
        this.pageView = pageView;
    }

    public OnlineClient getSelectedOnlineClient()
    {
        return selectedOnlineClient;
    }

    public void setSelectedOnlineClient(OnlineClient selectedOnlineClient)
    {
        this.selectedOnlineClient = selectedOnlineClient;
    }
    
}
