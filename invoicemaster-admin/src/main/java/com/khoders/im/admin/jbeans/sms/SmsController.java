/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.khoders.im.admin.jbeans.sms;

import Zenoph.SMSLib.Enums.REQSTATUS;
import static Zenoph.SMSLib.Enums.REQSTATUS.ERR_INSUFF_CREDIT;
import Zenoph.SMSLib.ZenophSMS;
import com.khoders.im.admin.dto.SmsData;
import com.khoders.im.admin.listener.AppSession;
import com.khoders.im.admin.services.SmsService;
import com.khoders.invoicemaster.DefaultService;
import com.khoders.invoicemaster.entities.Client;
import com.khoders.invoicemaster.enums.MessagingType;
import com.khoders.invoicemaster.enums.SMSType;
import com.khoders.invoicemaster.sms.GroupContact;
import com.khoders.invoicemaster.sms.MessageTemplate;
import com.khoders.invoicemaster.sms.SMSGrup;
import com.khoders.invoicemaster.sms.SenderId;
import com.khoders.invoicemaster.sms.Sms;
import com.khoders.resource.jpa.CrudApi;
import com.khoders.resource.utilities.Msg;
import com.khoders.resource.utilities.SystemUtils;
import java.io.IOException;
import java.io.Serializable;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

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
    @Inject private DefaultService ds;
    private static OkHttpClient http;

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
        
//        getConnection();
        
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
    
    public void sendMsg(){
        if (groupContactList.isEmpty()){
           Msg.error("Please load contacts");
           return;
        }
        if (sms.getSenderId() == null){
          Msg.error("Please set sender ID");
          return;
        }
        
        List<SmsData.Destination> destinations = new LinkedList<>();
        List<String> desList = new LinkedList<>();
        
        for (GroupContact gc : groupContactList) {
            if (gc.getClient() != null && gc.getClient().getPhone() != null) {
                desList.add(gc.getClient().getPhone());
            }
        }
        
        for (String num : desList) {
            SmsData.Destination des = new SmsData.Destination();
            des.setTo(num);
            destinations.add(des);
        }
        
        SmsData data = new SmsData();
        data.setSender(sms.getSenderId().getSenderIdentity());
        data.setText(textMessage);
        data.setType(0);
        data.setDestinations(destinations);
        
        List<SmsData> messages = new LinkedList<>();
        messages.add(data);
        
        try {
            String url = " https://api.smsonlinegh.com/v4/message/sms/send";
            System.out.println("messages: "+SystemUtils.KJson().toJson(messages) +"\n");
            RequestBody body = new FormBody.Builder().add("messages", SystemUtils.KJson().toJson(messages)).build();
            Request request = new Request.Builder()
                    .url(new URL(url))
                    .addHeader("Content-Type", "application/json")
                    .addHeader("Accept", "application/json")
                    .addHeader("Host", "api.smsonlinegh.com")
                    .addHeader("Authorization", "key ")
                    .post(body)
                    .build();
            Call call = http().newCall(request);
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    System.out.println("Response: ");
                    e.printStackTrace();
                    call.cancel();
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    System.out.println("Response: "+response.body().toString());
                    response.close();
                }
            });
            
            
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private static OkHttpClient http() {
            if (http == null) {
                    http = new OkHttpClient.Builder().callTimeout(5, TimeUnit.MINUTES).readTimeout(5, TimeUnit.MINUTES).build();
            }
            return http;
    }
    public void processMessage(){
        String sendId;
        if (selectedClient == null)
        {
          Msg.error("Please select contact");
          return;
        }
        if (sms.getSenderId() == null){
            sendId = ds.getConfigValue("sms.sender.id");
            System.out.println("sendId: "+sendId);
            if(sendId != null && sendId.isEmpty() || sendId == null){
                Msg.error("Please set sender ID");
                return;
            }
        }else{
            sendId = sms.getSenderId().getSenderIdentity();
        }

        try
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
                      Msg.error("Please type a message");
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
                
                zsms.setSenderId(sendId);

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
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public void processBulkMessage()
    {
        String sendId;
        if (groupContactList.isEmpty()){
           Msg.error("Please load contacts");
            return;
        }
        
        if (sms.getSenderId() == null){
            sendId = ds.getConfigValue("sms.sender.id");
            System.out.println("sendId: "+sendId);
            if(sendId != null && sendId.isEmpty() || sendId == null){
                Msg.error("Please set sender ID");
                return;
            }
        }else{
            sendId = sms.getSenderId().getSenderIdentity();
        }

        try
        {
            clearSMS();

            ZenophSMS zsms = smsService.extractParams();
            zsms.setSenderId(sendId);
            zsms.authenticate();

            // set message parameters.
            if (selectedMessagingType == MessagingType.TEMPLATE_MESSAGING) {
                zsms.setMessage(selectedMessageTemplate.getTemplateText());

                System.out.println("TEMPLATE_MESSAGING -- " + selectedMessageTemplate.getTemplateText());
            } else {
                if (textMessage.isEmpty()) {
                    Msg.error("Please type a message");
                    return;
                }
                zsms.setMessage(textMessage);
            }

            List<String> numbers = new LinkedList<>();
            for (GroupContact groupContact : groupContactList) {
                if (groupContact.getClient() != null && groupContact.getClient().getPhone() != null) {
                    numbers.add(groupContact.getClient().getPhone());
                }
            }
            for (String number : numbers) {
                zsms.addRecipient(number);
            }
            int count = 0;
            List<String[]> response = zsms.submit();
            for (String[] destination : response) {
                REQSTATUS reqstatus = REQSTATUS.fromInt(Integer.parseInt(destination[0]));
                if (reqstatus == null) {
                    Msg.error("failed to send message");
                    break;
                } else {
                    switch (reqstatus) {
                        case SUCCESS:
                            count++;
                            break;
                        case ERR_INSUFF_CREDIT:
                            Msg.error("Insufficeint Credit");
                        default:
                            Msg.error("Failed to send message");
                            return;
                    }
                }
            }
            zsms.clearRecipients();
            if(count > 0){
                Msg.info("SMS sent to "+count+" contacts successfully!");
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
           if(crudApi.save(sms) != null){
             System.out.println("SMS sent and saved -- ");
           }
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }
   
   public void saveMessage(String smsMessage, GroupContact groupContact)
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
