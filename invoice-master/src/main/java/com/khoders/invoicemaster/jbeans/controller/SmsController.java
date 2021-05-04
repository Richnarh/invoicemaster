/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.khoders.invoicemaster.jbeans.controller;

import Zenoph.SMSLib.Enums.MSGTYPE;
import Zenoph.SMSLib.Enums.REQSTATUS;
import static Zenoph.SMSLib.Enums.REQSTATUS.ERR_INSUFF_CREDIT;
import Zenoph.SMSLib.ZenophSMS;
import com.khoders.invoicemaster.entites.Client;
import com.khoders.invoicemaster.entites.enums.MessagingType;
import com.khoders.invoicemaster.entites.sms.MessageTemplate;
import com.khoders.invoicemaster.entites.sms.SenderId;
import com.khoders.invoicemaster.entites.sms.Sms;
import com.khoders.invoicemaster.jbeans.SmsAccess;
import com.khoders.invoicemaster.service.SmsService;
import com.khoders.resource.jpa.CrudApi;
import com.khoders.resource.utilities.Msg;
import com.khoders.resource.utilities.SystemUtils;
import java.io.Serializable;
import java.time.LocalDateTime;
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
@Named(value = "smsController")
@SessionScoped
public class SmsController implements Serializable
{
    @Inject CrudApi crudApi;
    @Inject SmsService smsService;

    private Sms sms = new Sms();
    private Client selectedClient;

    private List<Client> clientList = new LinkedList<>();
    private List<Sms> smsList = new LinkedList<>();

    private SenderId senderId = new SenderId();
    private MessageTemplate selectedMessageTemplate;
    
    private MessagingType selectedMessagingType = MessagingType.TEXT_MESSAGING;
    private String connectionStatus;
    private String textMessage;
    
    private boolean flag = false;

    @PostConstruct
    private void init()
    {
        clientList = smsService.getContactList();
        
        getConnection();
        
    }
    
    private void getConnection()
    {
        if(smsService.isInternetAccessVailable() == true)
        {
            connectionStatus = "Internet Access";
        }
        else
        {
            connectionStatus = "No Internet Access";
        }
    }
    
    public void selectMessagingType()
    {
        flag = selectedMessagingType == MessagingType.TEMPLATE_MESSAGING;
    }
    
    public void activateSenderId()
    {
        sms.setSenderId(senderId);
    }

    public void processMessage()
    {
        if (selectedClient == null)
        {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, Msg.setMsg("Please select contact"), null));
            return;
        }
        if (sms.getSenderId() == null)
        {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, Msg.setMsg("Please set sender ID"), null));
            return;
        }

        try
        {
            if (smsService.isInternetAccessVailable() == true)
            {
                clearSMS();
                
                ZenophSMS zsms = new ZenophSMS();
                zsms.setUser(SmsAccess.USERNAME);
                zsms.setPassword(SmsAccess.PASSWORD);
                zsms.authenticate();

                // set message parameters.
                if (selectedMessagingType == MessagingType.TEMPLATE_MESSAGING)
                {
                    zsms.setMessage(selectedMessageTemplate.getTemplateText());

                    System.out.println("TEMPLATE_MESSAGING -- " + selectedMessageTemplate.getTemplateText());
                } else
                {
                    zsms.setMessage(textMessage);
                }
                String phoneNumber = selectedClient.getPhone();
                List<String> numbers = zsms.extractPhoneNumbers(phoneNumber);

                for (String number : numbers)
                {
                    zsms.addRecipient(number);
                }
                
                zsms.setSenderId(sms.getSenderId().getSenderId());
                zsms.setMessageType(MSGTYPE.TEXT);

                List<String[]> response = zsms.submit();
                for (String[] destination : response)
                {
                    REQSTATUS reqstatus = REQSTATUS.fromInt(Integer.parseInt(destination[0]));
                    if (reqstatus == null)
                    {
                        FacesContext.getCurrentInstance().addMessage(null,
                                new FacesMessage(FacesMessage.SEVERITY_ERROR, Msg.setMsg("failed to send message"), null));
                        break;
                    } else
                    {
                        switch (reqstatus)
                        {
                            case SUCCESS:
                                saveMessage();
                                break;
                            case ERR_INSUFF_CREDIT:
                                FacesContext.getCurrentInstance().addMessage(null,
                                        new FacesMessage(FacesMessage.SEVERITY_ERROR, Msg.setMsg("Insufficeint Credit"), null));
                            default:
                                FacesContext.getCurrentInstance().addMessage(null,
                                        new FacesMessage(FacesMessage.SEVERITY_ERROR, Msg.setMsg("Failed to send message"), null));
                                return;
                        }
                    }
                }

            } else
            {
                System.out.println("---------Connection not Available ----");
            }
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    
    private void saveMessage()
    {
        try
        {
            sms.setSmsTime(LocalDateTime.now());
            sms.setMessage(textMessage);
            sms.setClient(selectedClient);
           if(crudApi.save(sms) != null)
           {
               System.out.println("SMS sent and saved -- ");
           }
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }
       
    public void loadSmslog()
    {
        smsList =smsService.loadSmslogList();
    }
    
    public void deleteSms(Sms sms)
    {
        try
        {
            if(crudApi.delete(sms))
            {
                smsList.remove(sms);
                FacesContext.getCurrentInstance().addMessage(null, 
                        new FacesMessage(FacesMessage.SEVERITY_INFO, Msg.SUCCESS_MESSAGE, null)); 
            }
            else
            {
                FacesContext.getCurrentInstance().addMessage(null, 
                        new FacesMessage(FacesMessage.SEVERITY_INFO, Msg.FAILED_MESSAGE, null)); 
            }
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public void clearSMS()
    {
        sms = new Sms();
        sms.setSenderId(senderId);
        SystemUtils.resetJsfUI();
    }

    public void manage(Client client)
    {
        this.selectedClient = client;
    }
   
    public Sms getSms()
    {
        return sms;
    }

    public void setSms(Sms sms)
    {
        this.sms = sms;
    }

    public List<Sms> getSmsList()
    {
        return smsList;
    }

    public String getConnectionStatus()
    {
        return connectionStatus;
    }
    
    public SenderId getSenderId()
    {
        return senderId;
    }

    public void setSenderId(SenderId senderId)
    {
        this.senderId = senderId;
    }

    public Client getSelectedClient()
    {
        return selectedClient;
    }

    public void setSelectedClient(Client selectedClient)
    {
        this.selectedClient = selectedClient;
    }

    public List<Client> getClientList()
    {
        return clientList;
    }

    public MessageTemplate getSelectedMessageTemplate()
    {
        return selectedMessageTemplate;
    }

    public void setSelectedMessageTemplate(MessageTemplate selectedMessageTemplate)
    {
        this.selectedMessageTemplate = selectedMessageTemplate;
    }

    public MessagingType getSelectedMessagingType()
    {
        return selectedMessagingType;
    }

    public void setSelectedMessagingType(MessagingType selectedMessagingType)
    {
        this.selectedMessagingType = selectedMessagingType;
    }

    public boolean isFlag()
    {
        return flag;
    }

    public void setFlag(boolean flag)
    {
        this.flag = flag;
    }

    public String getTextMessage()
    {
        return textMessage;
    }

    public void setTextMessage(String textMessage)
    {
        this.textMessage = textMessage;
    }
 
}
