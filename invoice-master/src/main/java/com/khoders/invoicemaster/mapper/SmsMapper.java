/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.khoders.invoicemaster.mapper;

import com.khoders.invoicemaster.dto.sms.GroupContactDto;
import com.khoders.invoicemaster.dto.sms.MessageTemplateDto;
import com.khoders.invoicemaster.dto.sms.SMSGrupDto;
import com.khoders.invoicemaster.entities.Client;
import com.khoders.invoicemaster.sms.GroupContact;
import com.khoders.invoicemaster.sms.MessageTemplate;
import com.khoders.invoicemaster.sms.SMSGrup;
import com.khoders.resource.exception.DataNotFoundException;
import com.khoders.resource.jpa.CrudApi;
import com.khoders.resource.utilities.SystemUtils;
import javax.ejb.Stateless;
import javax.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Pascal
 */
@Stateless
public class SmsMapper {
    private static final Logger log = LoggerFactory.getLogger(SmsMapper.class);
    @Inject CrudApi crudApi;
    
    public GroupContact toEntity(GroupContactDto dto){
        log.info("mapping GroupContact dto to entity");
        GroupContact contact = new GroupContact();
        if(dto.getId() != null){
            contact.setId(dto.getId());
        }
        if(dto.getClientId() == null){
            throw new DataNotFoundException("Client Id cannot be null");
        }
        if(dto.getSmsGrupId() == null){
            throw new DataNotFoundException("SMS Group Id cannot be null");
        }
        Client client = crudApi.find(Client.class, dto.getClientId());
        if(client != null){
            contact.setClient(client);
        }
        SMSGrup grup = crudApi.find(SMSGrup.class, dto.getSmsGrupId());
        if(grup != null){
            contact.setSmsGrup(grup);
        }
        contact.setContactGroupId(dto.getContactGroupId() == null ? SystemUtils.generateId() : dto.getContactGroupId());
        return contact;
    }
    
    public GroupContactDto toDto(GroupContact contact){
        GroupContactDto dto = new GroupContactDto();
        if(contact.getId() == null)return null;
        dto.setId(contact.getId());
        dto.setContactGroupId(contact.getContactGroupId());
        if(contact.getClient() != null){
            dto.setClient(contact.getClient().getClientName());
            dto.setClientId(contact.getClient().getId());
            dto.setPhoneNumber(contact.getClient().getPhone());
        }
        if(contact.getSmsGrup() != null){
            dto.setSmsGrup(contact.getSmsGrup().getGroupName());
            dto.setSmsGrupId(contact.getSmsGrup().getId());
        }
        return dto;
    }

    // SMS Group
    public SMSGrup toEntity(SMSGrupDto dto) {
       log.info("mapping SMSGrup dto to entity");
        SMSGrup sMSGrup = new SMSGrup();
        if(dto.getId() != null){
            sMSGrup.setId(dto.getId());
        }
        sMSGrup.setGroupId(dto.getGroupId()== null ? SystemUtils.generateId() : dto.getGroupId());
        sMSGrup.setGroupName(dto.getGroupName());
        return sMSGrup;
    }

    public SMSGrupDto toDto(SMSGrup mSGrup) {
         SMSGrupDto dto = new SMSGrupDto();
        if(mSGrup.getId() == null)return null;
        dto.setId(mSGrup.getId());
        dto.setGroupId(mSGrup.getGroupId());
        dto.setGroupName(mSGrup.getGroupName());
        return dto;
    }

    // Message Template
    public MessageTemplate toEntity(MessageTemplateDto dto) {
       log.info("mapping MessageTemplate dto to entity");
        MessageTemplate messageTemplate = new MessageTemplate();
        if(dto.getId() != null){
            messageTemplate.setId(dto.getId());
        }
        messageTemplate.setTemplateId(dto.getTemplateId()== null ? SystemUtils.generateId() : dto.getTemplateId());
        messageTemplate.setTemplateName(dto.getTemplateName());
        messageTemplate.setTemplateText(dto.getTemplateText());
        return messageTemplate;
    }

    public MessageTemplateDto toDto(MessageTemplate messageTemplate) {
        MessageTemplateDto dto = new MessageTemplateDto();
        if(messageTemplate.getId() == null)return null;
        dto.setId(messageTemplate.getId());
        dto.setTemplateId(messageTemplate.getTemplateId());
        dto.setTemplateName(messageTemplate.getTemplateName());
        dto.setTemplateText(messageTemplate.getTemplateText());
        return dto;
    }
}
