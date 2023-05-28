/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.khoders.im.admin.jbeans.sms;

import com.khoders.im.admin.listener.AppSession;
import com.khoders.im.admin.services.InventoryService;
import com.khoders.im.admin.services.SmsService;
import com.khoders.invoicemaster.entities.Client;
import com.khoders.invoicemaster.sms.GroupContact;
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
 * @author khoders
 */
@Named(value = "contactGroupController")
@SessionScoped
public class ContactGroupController implements Serializable{
    @Inject private CrudApi crudApi;
    @Inject private AppSession appSession;
    @Inject private SmsService smsService;
    @Inject private InventoryService inventoryService;
    
    private String optionText;
    
    private GroupContact contactGroup = new GroupContact();
    private List<GroupContact> contactGroupList = new LinkedList<>();
    private List<Client> clientList = new LinkedList<>();
    
    @PostConstruct
    private void init()
    {
        clearContactGroup();
        contactGroupList = smsService.getGroupContactList();
        clientList = inventoryService.getClientList();
    }
    
   public void saveContactGroup()
    {
        try 
        {
           if(contactGroup.getSmsGrup().getGroupName().equals("All"))
           {
               clientList.forEach(client -> {
                   GroupContact contact = new GroupContact();
                    
                    contact.genCode();
                    contact.setSmsGrup(contactGroup.getSmsGrup());
                    contact.setClient(client);
                    contact.setUserAccount(appSession.getCurrentUser());
                  
                    if(crudApi.save(contact) != null)
                    {
                        contactGroupList = CollectionList.washList(contactGroupList, contact);
                    }
               });
             
             return;
           }
             
          contactGroup.genCode();
          if(crudApi.save(contactGroup) != null)
          {
              contactGroupList = CollectionList.washList(contactGroupList, contactGroup);
              Msg.info(Msg.SUCCESS_MESSAGE);
          }
          else
          {
             Msg.error("Oops! failed to create contactGroup");
          }
           clearContactGroup();
        } catch (Exception e) 
        {
            e.printStackTrace();
        }
    }
   
    public void editContactGroup(GroupContact contactGroup)
    {
       optionText = "Update";
       this.contactGroup=contactGroup;
    }
    
    public void deleteContactGroup(GroupContact contactGroup)
    {
        try
        {
          if(crudApi.delete(contactGroup))
          {
              contactGroupList.remove(contactGroup);
              
              FacesContext.getCurrentInstance().addMessage(null, 
                        new FacesMessage(FacesMessage.SEVERITY_INFO, Msg.setMsg("Contact Group deleted successfully!"), null)); 
          }
          else
          {
              FacesContext.getCurrentInstance().addMessage(null, 
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, Msg.FAILED_MESSAGE, null));
          }
        } catch (Exception e) 
        {
            e.printStackTrace();
        }
    }
    
    public void clearContactGroup() {
        contactGroup = new GroupContact();
        contactGroup.setUserAccount(appSession.getCurrentUser());
        optionText = "Save Changes";
        SystemUtils.resetJsfUI();
    }

    public GroupContact getContactGroup() {
        return contactGroup;
    }

    public void setContactGroup(GroupContact contactGroup) {
        this.contactGroup = contactGroup;
    }

    public List<GroupContact> getContactGroupList() {
        return contactGroupList;
    }

    public String getOptionText() {
        return optionText;
    }

    public void setOptionText(String optionText) {
        this.optionText = optionText;
    }

    public List<Client> getClientList() {
        return clientList;
    }
    
}
