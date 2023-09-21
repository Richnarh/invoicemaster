/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.khoders.invoicemaster.commons;

import com.khoders.invoicemaster.entities.Client;
import com.khoders.invoicemaster.entities.Inventory;
import com.khoders.invoicemaster.entities.Product;
import com.khoders.invoicemaster.entities.ProductType;
import com.khoders.invoicemaster.entities.SaleLead;
import com.khoders.invoicemaster.handlers.ClientHandler;
import com.khoders.invoicemaster.sms.MessageTemplate;
import com.khoders.invoicemaster.sms.SMSGrup;
import com.khoders.invoicemaster.sms.SenderId;
import com.khoders.invoicemaster.service.InventoryService;
import com.khoders.invoicemaster.service.SmsService;
import com.khoders.resource.utilities.Msg;
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
 * @author pascal
 */
@Named(value = "userCommonClass")
@SessionScoped
public class UserCommonClass implements Serializable{
    @Inject private SmsService smsService;
    @Inject private InventoryService inventoryService;
    @Inject private ClientHandler clientService;
    
    private List<Client> clientList = new LinkedList<>();
    private List<Inventory> inventoryList = new LinkedList<>();
    private List<MessageTemplate> messageTemplateList = new LinkedList<>();
    private List<SenderId> senderIdList = new LinkedList<>();
    private List<Product> productList = new LinkedList<>();
    private List<SMSGrup> smsGroupList = new LinkedList<>();
    private List<ProductType> productTypeList = new LinkedList<>();
    private List<SaleLead> salesLeadList = new LinkedList<>();
    
    @PostConstruct
    public void init()
    {
        clientList = inventoryService.getClientList();
        inventoryList = inventoryService.getInventoryList();
        salesLeadList = inventoryService.getsalesLeadList();
        productList = inventoryService.getProductList();
        productTypeList = inventoryService.getProductTypeList();
        messageTemplateList = smsService.getMessageTemplateList();
        senderIdList = smsService.getSenderIdList();
        smsGroupList = smsService.getGroupList();
    }
    
    public void loadClient()
    {
        
        clientList = inventoryService.getClientList();
        FacesContext.getCurrentInstance().addMessage(null, 
                        new FacesMessage(FacesMessage.SEVERITY_INFO, Msg.setMsg("Client list updated!"), null)); 
        System.out.println("Sizz => "+clientList.size());
    }

    public List<Client> getClientList()
    {
        return clientList;
    }


    public List<Inventory> getInventoryList()
    {
        return inventoryList;
    }

    public List<MessageTemplate> getMessageTemplateList()
    {
        return messageTemplateList;
    }

    public List<SenderId> getSenderIdList()
    {
        return senderIdList;
    }

    public List<Product> getProductList()
    {
        return productList;
    }

    public List<SMSGrup> getSmsGroupList()
    {
        return smsGroupList;
    }

    public List<ProductType> getProductTypeList()
    {
        return productTypeList;
    }

    public List<SaleLead> getSalesLeadList() {
        return salesLeadList;
    }
    
}
