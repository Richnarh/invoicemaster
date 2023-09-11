/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.khoders.invoicemaster.service;

import Zenoph.SMSLib.Enums.REQSTATUS;
import static Zenoph.SMSLib.Enums.REQSTATUS.SUCCESS;
import Zenoph.SMSLib.ZenophSMS;
import com.khoders.invoicemaster.DefaultService;
import com.khoders.invoicemaster.dto.sms.GroupContactDto;
import com.khoders.invoicemaster.dto.sms.MessageTemplateDto;
import com.khoders.invoicemaster.dto.sms.SMSGrupDto;
import com.khoders.invoicemaster.dto.sms.SmsMessage;
import com.khoders.invoicemaster.entities.Client;
import com.khoders.invoicemaster.enums.MessagingType;
import com.khoders.invoicemaster.mapper.SmsMapper;
import com.khoders.invoicemaster.sms.GroupContact;
import com.khoders.invoicemaster.sms.MessageTemplate;
import com.khoders.invoicemaster.sms.SMSGrup;
import com.khoders.resource.exception.DataNotFoundException;
import com.khoders.resource.jpa.CrudApi;
import com.khoders.resource.utilities.Msg;
import java.util.LinkedList;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Pascal
 */
@Stateless
public class MsgService {
    private static final Logger log = LoggerFactory.getLogger(MsgService.class);
    @Inject private DefaultService ds;
    @Inject private CrudApi crudApi;
    @Inject private SmsService smsService;
    @Inject private InventoryService inventoryService;
    @Inject private SmsMapper mapper;

    public List<GroupContactDto> createGroupContact(GroupContactDto contactDto) {
        if(contactDto.getSmsGrupId() == null){
            throw new DataNotFoundException("Please select a group");
        }
        String groupName = null;
        SMSGrup grup = crudApi.find(SMSGrup.class, contactDto.getSmsGrupId());
        if(grup != null && grup.getGroupName().equals("All")){
            groupName = grup.getGroupName();
        }
        List<GroupContactDto> groupContactList = new LinkedList<>();
        if(groupName.equals("All")){
            List<Client> clientList = inventoryService.getClientList();
            clientList.forEach(client -> {
                GroupContact contact = new GroupContact();
                contact.genCode();
                contact.setSmsGrup(grup);
                contact.setClient(client);
                if (crudApi.save(contact) != null) {
                    groupContactList.add(mapper.toDto(contact));
                }
            });
        return groupContactList;
        }
        GroupContact contact = mapper.toEntity(contactDto);
        if(crudApi.save(contact) != null){
            groupContactList.add(mapper.toDto(contact));
        }
        return groupContactList;
    }
    
    public List<GroupContactDto> findAllContactgroups(){
        List<GroupContact> groupContactList = smsService.getContactGroupList();
        List<GroupContactDto> dtoList = new LinkedList<>();
        groupContactList.forEach(item -> {
            dtoList.add(mapper.toDto(item));
        });
        return dtoList;
    }
    
    public List<GroupContactDto> findContactByGroup(String groupId){
        SMSGrup smsGrup = crudApi.find(SMSGrup.class, groupId);
        List<GroupContact> groupContactList = smsService.getContactGroupList(smsGrup);
        List<GroupContactDto> dtoList = new LinkedList<>();
        groupContactList.forEach(item -> {
            dtoList.add(mapper.toDto(item));
        });
        return dtoList;
    }

    // SMS Group
    public SMSGrupDto saveGroup(SMSGrupDto grupDto) {
        SMSGrup mSGrup = mapper.toEntity(grupDto);
        SMSGrupDto dto = null;
        if(crudApi.save(mSGrup) != null){
            dto = mapper.toDto(mSGrup);
        }
        return dto;
    }

    public List<SMSGrupDto> findAll(){
        List<SMSGrup> smsGroupList = smsService.getGroupList();
        List<SMSGrupDto> dtoList = new LinkedList<>();
        smsGroupList.forEach(item -> {
            dtoList.add(mapper.toDto(item));
        });
        return dtoList;
    }
    
    public SMSGrupDto findGrupById(String smsgroupId){
        SMSGrup grup = crudApi.find(SMSGrup.class, smsgroupId);
        return mapper.toDto(grup);
    }
    
    public boolean delete(String smsgroupId){
        SMSGrup grup = crudApi.find(SMSGrup.class, smsgroupId);
        return grup != null ? crudApi.delete(grup) : false;
    }

    // Message Template
    public MessageTemplateDto saveTemplate(MessageTemplateDto grupDto) {
        MessageTemplate messageTemplate = mapper.toEntity(grupDto);
        MessageTemplateDto dto = null;
        if(crudApi.save(messageTemplate) != null){
            dto = mapper.toDto(messageTemplate);
        }
        return dto;
    }

    public List<MessageTemplateDto> findAllTemplates(){
        List<MessageTemplate> templateList = smsService.getMessageTemplateList();
        List<MessageTemplateDto> dtoList = new LinkedList<>();
        templateList.forEach(item -> {
            dtoList.add(mapper.toDto(item));
        });
        return dtoList;
    }
    
    public MessageTemplateDto findTemplateById(String templateId){
        MessageTemplate messageTemplate = crudApi.find(MessageTemplate.class, templateId);
        return mapper.toDto(messageTemplate);
    }
    
    public boolean deleteTemplate(String templateId){
        MessageTemplate grup = crudApi.find(MessageTemplate.class, templateId);
        return grup != null ? crudApi.delete(grup) : false;
    }

    public void initMsg(SmsMessage smsMessage){
        
    }
    
    public String singleMessage(SmsMessage smsMessage) {
        log.info("Starting...");
        String msg = null;
        String sendId = ds.getConfigValue("sms.sender.id");
        MessageTemplate messageTemplate = ds.getMessageTemplate(smsMessage.getMessageTemplateId());
        if (sendId == null) {
            return Msg.setMsg("Please set sender ID");
        }
        if (messageTemplate != null && messageTemplate.getTemplateText() == null) {
            return Msg.setMsg("The selected template has not text");
        }
        try {
            ZenophSMS zsms = smsService.extractParams();
            if (smsMessage.getMessagingType() == MessagingType.TEMPLATE_MESSAGING) {
                zsms.setMessage(messageTemplate.getTemplateText());
            } else {
                if (smsMessage.getTextMessage() == null || smsMessage.getTextMessage().isEmpty()) {
                    return Msg.setMsg("Please type a message");
                }
                zsms.setMessage(smsMessage.getTextMessage());
            }
            if (smsMessage.getClientId() == null) {
                return Msg.setMsg("Please select a client");
            }
            Client client = crudApi.find(Client.class, smsMessage.getClientId());
            if(client == null){
                return Msg.setMsg("Could not send message, Client cannot be found in database!");
            }
            log.info("Initializing number...");
            String phoneNumber = client.getPhone();
            log.info("phoneNumber... {} ", phoneNumber);
            List<String> numbers = zsms.extractPhoneNumbers(phoneNumber);

            log.info("numbers##... {} ", numbers);
            for (String number : numbers){
                zsms.addRecipient(number);
            }
            log.info("Done adding... ");
            zsms.setSenderId(sendId);
            System.out.println("Sending...");
            List<String[]> response = zsms.submit();
            for (String[] destination : response) {
                REQSTATUS reqstatus = REQSTATUS.fromInt(Integer.parseInt(destination[0]));
                if (reqstatus == null) {
                    Msg.error("failed to send message");
                    break;
                } else {
                    switch (reqstatus) {
                        case SUCCESS:
                            log.info(" <<--- SMS delivered -->>>");
                           msg =  Msg.setMsg("SMS sent to " + client.getClientName());
                            break;
                        case ERR_INSUFF_CREDIT:
                            msg = Msg.setMsg("Insufficeint Credit");
                        default:
                            msg = Msg.setMsg("Failed to send message");
                            break;
                    }
                }
            }
        } catch (Exception e) {
        }
        return msg;
    }
    
    public String bulkMessage(SmsMessage smsMessage) {
        String msg = null;
        String sendId = ds.getConfigValue("sms.sender.id");
        MessageTemplate messageTemplate = ds.getMessageTemplate(smsMessage.getMessageTemplateId());
        SMSGrup smsGrup = crudApi.find(SMSGrup.class, smsMessage.getSmsGroupId());
        if (sendId == null) {
            return Msg.setMsg("Please set sender ID");
        }
        if (messageTemplate != null && messageTemplate.getTemplateText() == null) {
            return Msg.setMsg("The selected template has not text");
        }
        try {
            ZenophSMS zsms = smsService.extractParams();
            if (smsMessage.getMessagingType() == MessagingType.TEMPLATE_MESSAGING) {
                zsms.setMessage(messageTemplate.getTemplateText());
            } else {
                if (smsMessage.getTextMessage() == null || smsMessage.getTextMessage().isEmpty()) {
                    return Msg.setMsg("Please type a message");
                }
                zsms.setMessage(smsMessage.getTextMessage());
            }
            if (smsMessage.getClientId() == null) {
                return Msg.setMsg("Please select a client");
            }
            Client client = crudApi.find(Client.class, smsMessage.getClientId());
            if(client == null){
                return Msg.setMsg("Could not send message, Client cannot be found in database!");
            }
            
            zsms.setSenderId(sendId);
            List<GroupContact> groupContactList = smsService.getContactGroupList(smsGrup);
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
                            msg = Msg.setMsg("Insufficeint Credit");
                        default:
                            msg = Msg.setMsg("Failed to send message");
                            break;
                    }
                }
            }
            zsms.clearRecipients();
            if(count > 0){
             return Msg.setMsg("SMS sent to "+count+" contacts successfully!");
            }
        } catch (Exception e) {
        }
        return msg;
    }
}
