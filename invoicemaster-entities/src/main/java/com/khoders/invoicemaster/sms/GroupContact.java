/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.khoders.invoicemaster.sms;

import com.khoders.invoicemaster.entities.Client;
import com.khoders.invoicemaster.entities.UserAccountRecord;
import com.khoders.resource.utilities.SystemUtils;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author pascal
 */
@Entity
@Table(name = "group_contact")
public class GroupContact extends UserAccountRecord implements Serializable
{

    @Column(name = "group_id")
    private String contactGroupId;

    @JoinColumn(name = "sms_group", referencedColumnName = "id")
    @ManyToOne
    private SMSGrup smsGrup;

    @JoinColumn(name = "client", referencedColumnName = "id")
    @ManyToOne
    private Client client;

    public String getContactGroupId()
    {
        return contactGroupId;
    }

    public void setContactGroupId(String contactGroupId)
    {
        this.contactGroupId = contactGroupId;
    }

    public SMSGrup getSmsGrup()
    {
        return smsGrup;
    }

    public void setSmsGrup(SMSGrup smsGrup)
    {
        this.smsGrup = smsGrup;
    }

    public Client getClient()
    {
        return client;
    }

    public void setClient(Client client)
    {
        this.client = client;
    }
    
    @Override
    public String toString()
    {
        return smsGrup+"";
    }
    
    public void genCode()
    {
        if (getContactGroupId() != null)
        {
            setContactGroupId(getContactGroupId());
        } else
        {
            setContactGroupId(SystemUtils.generateCode());
        }
    }
    
}
