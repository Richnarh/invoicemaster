/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.khoders.invoicemaster.lookups;

import com.khoders.invoicemaster.entities.Inventory;
import com.khoders.invoicemaster.entities.system.CompanyBranch;
import com.khoders.invoicemaster.entities.system.CompanyProfile;
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
            item.setItemName(data.getProduct().getProductName());
            itemList.add(item);
        });
        return itemList;
    } 
}
