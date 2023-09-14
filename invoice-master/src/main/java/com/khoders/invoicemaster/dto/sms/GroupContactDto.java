/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.khoders.invoicemaster.dto.sms;

import com.khoders.invoicemaster.dto.BaseDto;

/**
 *
 * @author Pascal
 */
public class GroupContactDto extends BaseDto{
    private String contactGroupId;
    private String smsGrupId;
    private String smsGrup;
    private String clientId;
    private String client;
    private String phoneNumber;

    public String getContactGroupId() {
        return contactGroupId;
    }

    public void setContactGroupId(String contactGroupId) {
        this.contactGroupId = contactGroupId;
    }

    public String getSmsGrupId() {
        return smsGrupId;
    }

    public void setSmsGrupId(String smsGrupId) {
        this.smsGrupId = smsGrupId;
    }

    public String getSmsGrup() {
        return smsGrup;
    }

    public void setSmsGrup(String smsGrup) {
        this.smsGrup = smsGrup;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    
}
