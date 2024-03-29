/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.khoders.invoicemaster.jbeans.controller;

import com.khoders.invoicemaster.entities.Client;
import com.khoders.invoicemaster.listener.AppSession;
import com.khoders.invoicemaster.service.InventoryService;
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
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author khoders
 */
@Named(value = "clientController")
@SessionScoped
public class ClientController implements Serializable
{

    @Inject
    private CrudApi crudApi;
    @Inject
    private AppSession appSession;
    @Inject
    private InventoryService inventoryService;

    private Client client = new Client();
    private List<Client> clientList = new LinkedList<>();

    private FormView pageView = FormView.listForm();
    private String optionText;

    @PostConstruct
    private void init()
    {
        clearClient();
        clientList = inventoryService.getClientList();
    }

    public void initCLient()
    {
        clearClient();
        pageView.restToCreateView();
    }

    public void saveClient()
    {
        if (!optionText.equals("Update"))
        {
            if (client != null)
            {
                Client object = inventoryService.clientExist(client.getPhone());

                System.out.println("Object => " + object);

                if (object != null)
                {
                    FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_ERROR, Msg.setMsg("The client with phone number: " + client.getPhone() + " already exist"), null));

                    return;
                }
            }
        }
        try
        {
            client.genCode();
            client.setUserAccount(appSession.getCurrentUser());
            client.setCompanyBranch(appSession.getCompanyBranch());
            if (crudApi.save(client) != null)
            {
                clientList = CollectionList.washList(clientList, client);

                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_INFO, Msg.SUCCESS_MESSAGE, null));
            } else
            {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, Msg.FAILED_MESSAGE, null));
            }
            clearClient();
            closePage();
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public void deleteClient(Client client)
    {
        try
        {
            if (crudApi.delete(client))
            {
                clientList.remove(client);

                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_INFO, Msg.SUCCESS_MESSAGE, null));
            } else
            {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, Msg.FAILED_MESSAGE, null));
            }
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public void editClient(Client client)
    {
        pageView.restToCreateView();
        this.client = client;
        optionText = "Update";
    }

    public void clearClient()
    {
        client = new Client();
        client.setUserAccount(appSession.getCurrentUser());
        client.setCompanyBranch(appSession.getCompanyBranch());
        optionText = "Save Changes";
        SystemUtils.resetJsfUI();
    }

    public void closePage()
    {
        client = new Client();
        optionText = "Save Changes";
        pageView.restToListView();
    }

    public List<Client> getClientList()
    {
        return clientList;
    }

    public Client getClient()
    {
        return client;
    }

    public void setClient(Client bird)
    {
        this.client = bird;
    }

    public String getOptionText()
    {
        return optionText;
    }

    public void setOptionText(String optionText)
    {
        this.optionText = optionText;
    }

    public FormView getPageView()
    {
        return pageView;
    }

    public void setPageView(FormView pageView)
    {
        this.pageView = pageView;
    }

}
