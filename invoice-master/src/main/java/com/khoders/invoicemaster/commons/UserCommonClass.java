/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.khoders.invoicemaster.commons;

import com.khoders.invoicemaster.entites.Client;
import com.khoders.invoicemaster.entites.Inventory;
import com.khoders.invoicemaster.entites.Product;
import com.khoders.invoicemaster.entites.ProductType;
import com.khoders.invoicemaster.entites.sms.MessageTemplate;
import com.khoders.invoicemaster.entites.sms.SMSGrup;
import com.khoders.invoicemaster.entites.sms.SenderId;
import com.khoders.invoicemaster.service.InventoryService;
import com.khoders.invoicemaster.service.SmsService;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.Asynchronous;
import javax.enterprise.context.SessionScoped;
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
    @Inject InventoryService inventoryService;
    
    private List<Client> clientList = new LinkedList<>();
    private List<Inventory> inventoryList = new LinkedList<>();
    private List<MessageTemplate> messageTemplateList = new LinkedList<>();
    private List<SenderId> senderIdList = new LinkedList<>();
    private List<Product> productList = new LinkedList<>();
    private List<SMSGrup> smsGroupList = new LinkedList<>();
    private List<ProductType> productTypeList = new LinkedList<>();
    
    @PostConstruct
    @Asynchronous
    public void init()
    {
        clientList = inventoryService.getClientList();
        inventoryList = inventoryService.getInventoryList();
        productList = inventoryService.getProductList();
        productTypeList = inventoryService.getProductTypeList();
        messageTemplateList = smsService.getMessageTemplateList();
        senderIdList = smsService.getSenderIdList();
        smsGroupList = smsService.getGroupList();
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
    
}
