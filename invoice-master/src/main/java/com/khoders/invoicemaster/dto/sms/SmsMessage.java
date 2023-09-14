/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.khoders.invoicemaster.dto.sms;

import com.khoders.invoicemaster.dto.ClientDto;
import com.khoders.invoicemaster.enums.MessagingType;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Pascal
 */
public class SmsMessage {
    private String clientId;
    private String smsGroupId;
    private String messageTemplateId;
    private MessagingType messagingType;
    private String textMessage;
    private List<ClientDto> clientList = new LinkedList<>();

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getSmsGroupId() {
        return smsGroupId;
    }

    public void setSmsGroupId(String smsGroupId) {
        this.smsGroupId = smsGroupId;
    }

    public MessagingType getMessagingType() {
        return messagingType;
    }

    public void setMessagingType(MessagingType messagingType) {
        this.messagingType = messagingType;
    }

    public String getTextMessage() {
        return textMessage;
    }

    public void setTextMessage(String textMessage) {
        this.textMessage = textMessage;
    }

    public String getMessageTemplateId() {
        return messageTemplateId;
    }

    public void setMessageTemplateId(String messageTemplateId) {
        this.messageTemplateId = messageTemplateId;
    }

    public List<ClientDto> getClientList() {
        return clientList;
    }

    public void setClientList(List<ClientDto> clientList) {
        this.clientList = clientList;
    }
    
}
