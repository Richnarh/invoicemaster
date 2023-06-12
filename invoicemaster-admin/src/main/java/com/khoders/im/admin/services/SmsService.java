/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.khoders.im.admin.services;

import Zenoph.SMSLib.Enums.MSGTYPE;
import Zenoph.SMSLib.ZenophSMS;
import com.khoders.im.admin.listener.AppSession;
import com.khoders.invoicemaster.entities.Client;
import com.khoders.invoicemaster.enums.SMSType;
import com.khoders.invoicemaster.sms.GroupContact;
import com.khoders.invoicemaster.sms.MessageTemplate;
import com.khoders.invoicemaster.sms.SMSGrup;
import com.khoders.invoicemaster.sms.SenderId;
import com.khoders.invoicemaster.sms.Sms;
import com.khoders.invoicemaster.sms.SmsAccess;
import com.khoders.resource.jpa.CrudApi;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.UnknownHostException;
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
    
    public List<Client> getContactList()
    {
        try
        {
            String qryString = "SELECT e FROM Client e ORDER BY e.clientName ASC";
            TypedQuery<Client> typedQuery = crudApi.getEm().createQuery(qryString, Client.class);
                         return typedQuery.getResultList();
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }
    
    public List<GroupContact> getGroupContactList()
    {
        try
        {
            String qryString = "SELECT e FROM GroupContact e";
            TypedQuery<GroupContact> typedQuery = crudApi.getEm().createQuery(qryString, GroupContact.class);
                             return typedQuery.getResultList();
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }
    
    public List<SMSGrup> getGroupList()
    {
        try
        {
            String qryString = "SELECT e FROM SMSGrup e ";
            TypedQuery<SMSGrup> typedQuery = crudApi.getEm().createQuery(qryString, SMSGrup.class);
                             return typedQuery.getResultList();
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }
    
    public List<GroupContact> getContactGroupList()
    {
        try
        {
            String qryString = "SELECT e FROM GroupContact e";
            TypedQuery<GroupContact> typedQuery = crudApi.getEm().createQuery(qryString, GroupContact.class);
                             return typedQuery.getResultList();
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }
    
    public List<GroupContact> getContactGroupList(SMSGrup smsGrup)
    {
        try
        {
            String qryString = "SELECT e FROM GroupContact e WHERE e.smsGrup=?1";
            TypedQuery<GroupContact> typedQuery = crudApi.getEm().createQuery(qryString, GroupContact.class);
                                typedQuery.setParameter(1, smsGrup);
                             return typedQuery.getResultList();
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return Collections.emptyList();
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
    public boolean isInternetAccessVailable()
    {
        try
        {
            URL url = new URL("http://www.google.com");
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            Object object = httpURLConnection.getContent();
            System.out.println("SUCCESSFUL INTERNET CONNECTION");
            System.out.println(object);
            return true;

        } catch (UnknownHostException e)
        {
            System.out.println("CONNECTION FAILED");
            e.printStackTrace();
            return false;
        } catch (IOException e)
        {
            System.out.println("CONNECTION FAILED");
            e.printStackTrace();
        }
        return false;
    }
    
    public List<MessageTemplate> getMessageTemplateList()
    {
        try
        {
            String qryString = "SELECT e FROM MessageTemplate e ";
            TypedQuery<MessageTemplate> typedQuery = crudApi.getEm().createQuery(qryString, MessageTemplate.class);
                             return typedQuery.getResultList();
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }
    
    public List<SmsAccess> smsAccessList(){
        return crudApi.getEm().createQuery("SELECT e FROM SmsAccess e", SmsAccess.class).getResultList();
    }
    
    public ZenophSMS extractParams()
    {
        ZenophSMS zsms = new ZenophSMS();
        try
        {
           SmsAccess smsAccess = crudApi.getEm().createQuery("SELECT e FROM SmsAccess e", SmsAccess.class)
                    .getSingleResult();
            if(smsAccess != null){
                
            System.out.println("Username --- "+smsAccess.getUsername());
            System.out.println("Password --- "+smsAccess.getPassword());
            
            zsms.setUser(smsAccess.getUsername());
            zsms.setPassword(smsAccess.getPassword());
            zsms.authenticate();
            zsms.setMessageType(MSGTYPE.TEXT);
          }
        } catch (Exception e)
        {
            e.printStackTrace();
        }

        return zsms;
    }
}
