/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.khoders.invoicemaster.service;

import com.khoders.invoicemaster.entites.Client;
import com.khoders.invoicemaster.entites.sms.GroupContact;
import com.khoders.invoicemaster.entites.sms.MessageTemplate;
import com.khoders.invoicemaster.entites.sms.SMSGrup;
import com.khoders.invoicemaster.entites.sms.SenderId;
import com.khoders.invoicemaster.entites.sms.Sms;
import com.khoders.invoicemaster.listener.AppSession;
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
    @Inject private AppSession appSession;
    @Inject private CrudApi crudApi;
    
    public List<Client> getContactList()
    {
        try
        {
            String qryString = "SELECT e FROM Client e WHERE e.userAccount=?1 ORDER BY e.clientName ASC";
            TypedQuery<Client> typedQuery = crudApi.getEm().createQuery(qryString, Client.class);
                               typedQuery.setParameter(1, appSession.getCurrentUser());
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
            String qryString = "SELECT e FROM GroupContact e WHERE e.userAccount=?1";
            TypedQuery<GroupContact> typedQuery = crudApi.getEm().createQuery(qryString, GroupContact.class);
                                typedQuery.setParameter(1, appSession.getCurrentUser());
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
            String qryString = "SELECT e FROM SMSGrup e WHERE e.userAccount=?1 ";
            TypedQuery<SMSGrup> typedQuery = crudApi.getEm().createQuery(qryString, SMSGrup.class);
                                typedQuery.setParameter(1, appSession.getCurrentUser());
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
            String qryString = "SELECT e FROM GroupContact e WHERE e.userAccount=?1 AND e.smsGrup=?2";
            TypedQuery<GroupContact> typedQuery = crudApi.getEm().createQuery(qryString, GroupContact.class);
                                typedQuery.setParameter(1, appSession.getCurrentUser());
                                typedQuery.setParameter(2, smsGrup);
                             return typedQuery.getResultList();
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }
  
    public List<Sms> loadSmslogList()
    {
        try
        {
            String qryString = "SELECT e FROM Sms e WHERE e.userAccount=?1 ORDER BY e.smsTime DESC";
            TypedQuery<Sms> typedQuery = crudApi.getEm().createQuery(qryString, Sms.class);
                            typedQuery.setParameter(1, appSession.getCurrentUser());
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
            String qryString = "SELECT e FROM SenderId e WHERE e.userAccount=?1";
            TypedQuery<SenderId> typedQuery = crudApi.getEm().createQuery(qryString, SenderId.class);
            typedQuery.setParameter(1, appSession.getCurrentUser());
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
            String qryString = "SELECT e FROM MessageTemplate e WHERE e.userAccount=?1";
            TypedQuery<MessageTemplate> typedQuery = crudApi.getEm().createQuery(qryString, MessageTemplate.class);
            typedQuery.setParameter(1, appSession.getCurrentUser());
                             return typedQuery.getResultList();
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }
    
}