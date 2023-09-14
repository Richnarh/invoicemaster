/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.khoders.invoicemaster.service;

import Zenoph.SMSLib.Enums.MSGTYPE;
import Zenoph.SMSLib.ZenophSMS;
import com.khoders.invoicemaster.DefaultService;
import com.khoders.invoicemaster.entities.Client;
import com.khoders.invoicemaster.enums.SMSType;
import com.khoders.invoicemaster.sms.GroupContact;
import com.khoders.invoicemaster.sms.MessageTemplate;
import com.khoders.invoicemaster.sms.SMSGrup;
import com.khoders.invoicemaster.sms.SenderId;
import com.khoders.invoicemaster.sms.Sms;
import com.khoders.resource.jpa.CrudApi;
import java.util.Collections;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.TypedQuery;

/**
 *
 * @author richa
 */
@Stateless
public class SmsService
{
    @Inject private CrudApi crudApi;
    @Inject private DefaultService ds;
    
    public List<Client> getContactList(){
        return crudApi.getEm().createQuery("SELECT e FROM Client e ORDER BY e.clientName ASC", Client.class).getResultList();
    }
       
    public List<SMSGrup> getGroupList(){
       return crudApi.getEm().createQuery("SELECT e FROM SMSGrup e ORDER BY e.groupName ASC", SMSGrup.class).getResultList();
    }
    
    public List<GroupContact> getContactGroupList(){
        return crudApi.getEm().createQuery("SELECT e FROM GroupContact e", GroupContact.class).getResultList();
    }
    
    public List<GroupContact> getContactGroupList(SMSGrup smsGrup){
       return crudApi.getEm().createQuery("SELECT e FROM GroupContact e WHERE e.smsGrup=:smsGrup", GroupContact.class)
               .setParameter(GroupContact._smsGrup, smsGrup)
               .getResultList();
    }
  
    public List<Sms> loadSmslogList(SMSType smsType)
    {
        try
        {
            String qryString = "SELECT e FROM Sms e WHERE e.sMSType=?1 ORDER BY e.smsTime DESC";
//            String qryString = "SELECT e FROM Sms e WHERE e.userAccount=?1 AND e.sMSType=?2 ORDER BY e.smsTime DESC";
            TypedQuery<Sms> typedQuery = crudApi.getEm().createQuery(qryString, Sms.class);
//                            typedQuery.setParameter(1, appSession.getCurrentUser());
                            typedQuery.setParameter(1, smsType);
                            return typedQuery.getResultList();
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }
    
    public List<SenderId> getSenderIdList()
    {
        try
        {
            String qryString = "SELECT e FROM SenderId e";
            TypedQuery<SenderId> typedQuery = crudApi.getEm().createQuery(qryString, SenderId.class);
            typedQuery.getResultList();

            return typedQuery.getResultList();
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }
    
    public List<MessageTemplate> getMessageTemplateList(){
     return crudApi.getEm().createQuery("SELECT e FROM MessageTemplate e", MessageTemplate.class).getResultList();
    }
   
    public ZenophSMS extractParams(){
        ZenophSMS zsms = new ZenophSMS();
        try {
            zsms.setUser(ds.getConfigValue("sms.username"));
            zsms.setPassword(ds.getConfigValue("sms.password"));
            zsms.authenticate();
            zsms.setMessageType(MSGTYPE.TEXT);
        } catch (Exception e) {
        }
        return zsms;
    }
}
