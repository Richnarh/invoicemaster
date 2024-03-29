/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.khoders.invoicemaster.lookups;

import com.khoders.invoicemaster.entities.Client;
import com.khoders.invoicemaster.entities.Inventory;
import com.khoders.invoicemaster.entities.Product;
import com.khoders.invoicemaster.entities.ProductType;
import com.khoders.invoicemaster.entities.SaleLead;
import com.khoders.invoicemaster.entities.UserAccount;
import com.khoders.invoicemaster.entities.system.CompanyBranch;
import com.khoders.invoicemaster.entities.system.CompanyProfile;
import com.khoders.invoicemaster.sms.MessageTemplate;
import com.khoders.invoicemaster.sms.SMSGrup;
import com.khoders.resource.jpa.QueryBuilder;
import java.util.LinkedList;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author Pascal
 */
@Stateless
public class LookupService {
    @Inject private QueryBuilder builder;
    
    public List<LookupItem> companyProfile() {
        List<LookupItem> itemList = new LinkedList<>();
        builder.findAll(CompanyProfile.class).forEach(data -> {
            LookupItem item = new LookupItem();
            item.setId(data.getId());
            item.setItemName(data.getCompanyEmail()+"-"+data.getTinNo());
            itemList.add(item);
        });
        return itemList;
    } 
    
    public List<LookupItem> companyBranch() {
        List<LookupItem> itemList = new LinkedList<>();
        builder.findAll(CompanyBranch.class).forEach(data -> {
            LookupItem item = new LookupItem();
            item.setId(data.getId());
            item.setItemName(data.getBranchName());
            itemList.add(item);
        });
        return itemList;
    } 
    
    public List<LookupItem> inventory() {
        List<LookupItem> itemList = new LinkedList<>();
        builder.findAll(Inventory.class).forEach(data -> {
            LookupItem item = new LookupItem();
            item.setId(data.getId());
            item.setItemName(data.getProduct()+"");
            itemList.add(item);
        });
        return itemList;
    } 
    public List<LookupItem> products() {
        List<LookupItem> itemList = new LinkedList<>();
        builder.findAll(Product.class).forEach(data -> {
            LookupItem item = new LookupItem();
            item.setId(data.getId());
            item.setItemName(data.getProductName());
            itemList.add(item);
        });
        return itemList;
    } 
    public List<LookupItem> productTypes() {
        List<LookupItem> itemList = new LinkedList<>();
        builder.findAll(ProductType.class).forEach(data -> {
            LookupItem item = new LookupItem();
            item.setId(data.getId());
            item.setItemName(data.getProductTypeName());
            itemList.add(item);
        });
        return itemList;
    } 
    public List<LookupItem> employees() {
        List<LookupItem> itemList = new LinkedList<>();
        builder.findAll(UserAccount.class).forEach(data -> {
            LookupItem item = new LookupItem();
            item.setId(data.getId());
            item.setItemName(data.getFullname() +" - "+data.getPhoneNumber());
            itemList.add(item);
        });
        return itemList;
    } 
    public List<LookupItem> saleslead() {
        List<LookupItem> itemList = new LinkedList<>();
        builder.findAll(SaleLead.class).forEach(data -> {
            LookupItem item = new LookupItem();
            item.setId(data.getId());
            item.setItemName(data+"");
            itemList.add(item);
        });
        return itemList;
    } 
    public List<LookupItem> clients() {
        List<LookupItem> itemList = new LinkedList<>();
        builder.findAll(Client.class).forEach(data -> {
            LookupItem item = new LookupItem();
            item.setId(data.getId());
            item.setItemName(data+"");
            itemList.add(item);
        });
        return itemList;
    } 
    public List<LookupItem> smsGroup() {
        List<LookupItem> itemList = new LinkedList<>();
        builder.findAll(SMSGrup.class).forEach(data -> {
            LookupItem item = new LookupItem();
            item.setId(data.getId());
            item.setItemName(data+"");
            itemList.add(item);
        });
        return itemList;
    } 
    public List<LookupItem> messageTemplates() {
        List<LookupItem> itemList = new LinkedList<>();
        builder.findAll(MessageTemplate.class).forEach(data -> {
            LookupItem item = new LookupItem();
            item.setId(data.getId());
            item.setItemName(data+"");
            itemList.add(item);
        });
        return itemList;
    } 
}
