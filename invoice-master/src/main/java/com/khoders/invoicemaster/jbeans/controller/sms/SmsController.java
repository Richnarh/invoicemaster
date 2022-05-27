/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.khoders.invoicemaster.jbeans.controller.sms;

import Zenoph.SMSLib.Enums.REQSTATUS;
import static Zenoph.SMSLib.Enums.REQSTATUS.ERR_INSUFF_CREDIT;
import Zenoph.SMSLib.ZenophSMS;
import com.khoders.invoicemaster.entities.Client;
import com.khoders.invoicemaster.enums.MessagingType;
import com.khoders.invoicemaster.enums.SMSType;
import com.khoders.invoicemaster.sms.GroupContact;
import com.khoders.invoicemaster.sms.MessageTemplate;
import com.khoders.invoicemaster.sms.SMSGrup;
import com.khoders.invoicemaster.sms.SenderId;
import com.khoders.invoicemaster.sms.Sms;
import com.khoders.invoicemaster.listener.AppSession;
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
    @Inject private CrudApi crudApi;
    @Inject private AppSession appSession;
    @Inject private SmsService smsService;

    private Sms sms = new Sms();
    private Client selectedClient;
    private SMSGrup smsGrup = new SMSGrup();

    private List<Client> clientList = new LinkedList<>();
    private List<Sms> smsList = new LinkedList<>();
    
     private List<GroupContact> groupContactList = new LinkedList<>();

    private SenderId senderId = new SenderId();
    private MessageTemplate selectedMessageTemplate;
    
    private MessagingType selectedMessagingType = MessagingType.TEXT_MESSAGING;
    private String connectionStatus;
    private String textMessage;
    
    private SMSType smsType = SMSType.SINGLE_SMS;
    
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
                
                ZenophSMS zsms = smsService.extractParams();

                // set message parameters.
                if (selectedMessagingType == MessagingType.TEMPLATE_MESSAGING)
                {
                    zsms.setMessage(selectedMessageTemplate.getTemplateText());

                    System.out.println("TEMPLATE_MESSAGING -- " + selectedMessageTemplate.getTemplateText());
                } else
                {
                    if(textMessage.isEmpty())
                    {
                         FacesContext.getCurrentInstance().addMessage(null,
                                        new FacesMessage(FacesMessage.SEVERITY_ERROR, Msg.setMsg("Please type a message"), null));
                        
                        return;
                    }
                    zsms.setMessage(textMessage);
                }
                
                String phoneNumber = selectedClient.getPhone();
                List<String> numbers = zsms.extractPhoneNumbers(phoneNumber);

                for (String number : numbers)
                {
                    zsms.addRecipient(number);
                }
                
                zsms.setSenderId(sms.getSenderId().getSenderIdentity());
                

                List<String[]> response = zsms.submit();
                for (String[] destination : response)
                {
                    REQSTATUS reqstatus = REQSTATUS.fromInt(Integer.parseInt(destination[0]));
                    if (reqstatus == null)
                    {
                      Msg.error("failed to send message");
                        break;
                    } else
                    {
                        switch (reqstatus)
                        {
                            case SUCCESS:
                                saveMessage(zsms.getMessage());
                                System.out.println(" <<--- SMS delivered -->>>");
                                Msg.info("SMS sent to "+selectedClient.getClientName());
                                break;
                            case ERR_INSUFF_CREDIT:
                                Msg.error("Insufficeint Credit");
                            default:
                                Msg.error("Failed to send message");
                                return;
                        }
                    }
                }

            } else
            {
                System.out.println("--------- INTERNET CONNECTION NOT AVAILABLE ----");
            }
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    
    public void processBulkMessage()
    {
        if (groupContactList.isEmpty())
        {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, Msg.setMsg("Please load contacts"), null));
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
                
                ZenophSMS zsms = SmsService.extractParams();
                zsms.authenticate();

               // set message parameters.
                if (selectedMessagingType == MessagingType.TEMPLATE_MESSAGING)
                {
                    zsms.setMessage(selectedMessageTemplate.getTemplateText());
                    
                    System.out.println("TEMPLATE_MESSAGING -- " + selectedMessageTemplate.getTemplateText());
                } else
                {
                    if(textMessage.isEmpty())
                    {
                      Msg.error("Please type a message");
                      return;
                    }
                    zsms.setMessage(textMessage);
                }
                
                String phoneNumber = null;
                GroupContact gc=null;
                
                for (GroupContact groupContact : groupContactList)
                {
                    gc = groupContact;
                    phoneNumber = groupContact.getClient().getPhone();
                    
                    List<String> numbers = zsms.extractPhoneNumbers(phoneNumber);

                    for (String number : numbers)
                    {
                        zsms.addRecipient(number);
                    }
                }
                
                zsms.setSenderId(sms.getSenderId().getSenderIdentity());

                List<String[]> response = zsms.submit();
                for (String[] destination : response)
                {
                    REQSTATUS reqstatus = REQSTATUS.fromInt(Integer.parseInt(destination[0]));
                    if (reqstatus == null)
                    {
                        Msg.error("failed to send message");
                        break;
                    }
                    else
                    {
                        switch (reqstatus)
                        {
                            case SUCCESS:
                                saveBulkMessage(zsms.getMessage(), gc);
                                System.out.println(" <<--- Bulk SMS delivered -->>>");
                                Msg.info("Sending Bulk Message successful!");
                                break;
                            case ERR_INSUFF_CREDIT:
                                Msg.error("Insufficeint Credit");
                            default:
                                Msg.error("Failed to send message");
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

    public void saveMessage(String smsMessage)
    {
        try
        {
            sms = new Sms();
            sms.setSmsTime(LocalDateTime.now());
            sms.setMessage(smsMessage);
            sms.setClient(selectedClient);
            sms.setsMSType(SMSType.SINGLE_SMS);
            sms.setUserAccount(appSession.getCurrentUser());
           if(crudApi.save(sms) != null)
           {
             Msg.info("SMS sent to "+selectedClient.getClientName());
               
               System.out.println("SMS sent and saved -- ");
           }
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }
   
   public void saveBulkMessage(String smsMessage, GroupContact groupContact)
   {
      try
        {
            sms = new Sms();
            sms.genCode();
            sms.setSmsTime(LocalDateTime.now());
            sms.setMessage(smsMessage);
            sms.setClient(groupContact.getClient());
            sms.setsMSType(SMSType.BULK_SMS);
            sms.setUserAccount(appSession.getCurrentUser());

            if (crudApi.save(sms) != null)
            {
                System.out.println("SMS sent and saved -- ");
            }  
           
        } catch (Exception e)
        {
            e.printStackTrace();
        }  
   }

    public void loadContactGroup()
    {
        if(smsGrup.getGroupName().equals("All"))
        {
          groupContactList = smsService.getContactGroupList();
          return;
        }
        groupContactList = smsService.getContactGroupList(smsGrup);
    }
    
    public void loadSmslog()
    {
        smsList =smsService.loadSmslogList(smsType);
    }
    
    public void deleteSms(Sms sms)
    {
        try
        {
            if(crudApi.delete(sms))
            {
                smsList.remove(sms);
                Msg.info(Msg.SUCCESS_MESSAGE);
            }
            else
            {
              Msg.info(Msg.FAILED_MESSAGE);
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
        sms.setUserAccount(appSession.getCurrentUser());
        sms.setCompanyBranch(appSession.getCompanyBranch());
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

    public SMSGrup getSmsGrup()
    {
        return smsGrup;
    }

    public void setSmsGrup(SMSGrup smsGrup)
    {
        this.smsGrup = smsGrup;
    }

    public List<GroupContact> getGroupContactList()
    {
        return groupContactList;
    }

    public SMSType getSmsType() {
        return smsType;
    }

    public void setSmsType(SMSType smsType) {
        this.smsType = smsType;
    }
}
