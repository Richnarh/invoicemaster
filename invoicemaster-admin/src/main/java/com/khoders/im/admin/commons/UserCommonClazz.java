/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.khoders.im.admin.commons;

import com.khoders.im.admin.services.CompanyService;
import com.khoders.im.admin.services.InventoryService;
import com.khoders.im.admin.services.SmsService;
import com.khoders.invoicemaster.entities.Client;
import com.khoders.invoicemaster.entities.Inventory;
import com.khoders.invoicemaster.entities.Product;
import com.khoders.invoicemaster.entities.ProductType;
import com.khoders.invoicemaster.entities.UserAccount;
import com.khoders.invoicemaster.entities.system.CompanyBranch;
import com.khoders.invoicemaster.entities.system.CompanyProfile;
import com.khoders.invoicemaster.sms.MessageTemplate;
import com.khoders.invoicemaster.sms.SMSGrup;
import com.khoders.invoicemaster.sms.SenderId;
import com.khoders.resource.jpa.CrudApi;
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
@Named(value = "userCommonClazz")
@SessionScoped
public class UserCommonClazz implements Serializable
{
   @Inject private CrudApi crudApi;
   @Inject private CompanyService companyService;
   @Inject private InventoryService inventoryService;
   @Inject private SmsService smsService;
   
   private List<CompanyBranch> companyBranchList = new LinkedList<>();
   private List<CompanyProfile> companyProfileList = new LinkedList<>();
   private List<UserAccount> userAccountList = new LinkedList<>();
    private List<Inventory> inventoryList = new LinkedList<>();
    private List<MessageTemplate> messageTemplateList = new LinkedList<>();
    private List<SenderId> senderIdList = new LinkedList<>();
    private List<Product> productList = new LinkedList<>();
    private List<SMSGrup> smsGroupList = new LinkedList<>();
    private List<ProductType> productTypeList = new LinkedList<>();
    private List<Client> clientList = new LinkedList<>();
   
   @PostConstruct
   public void init()
   {
       companyBranchList = companyService.getCompanyBranchList();
       companyProfileList = companyService.getCompanyProfileList();
       userAccountList = companyService.getUserAccountList();
        clientList = inventoryService.getClientList();
        inventoryList = inventoryService.getInventoryList();
        productList = inventoryService.getProductList();
        productTypeList = inventoryService.getProductTypeList();
        messageTemplateList = smsService.getMessageTemplateList();
        senderIdList = smsService.getSenderIdList();
        smsGroupList = smsService.getGroupList();
   }

    public List<CompanyBranch> getCompanyBranchList()
    {
        return companyBranchList;
    }

    public List<CompanyProfile> getCompanyProfileList()
    {
        return companyProfileList;
    }

    public List<UserAccount> getUserAccountList()
    {
        return userAccountList;
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

    public List<Client> getClientList()
    {
        return clientList;
    }

}
