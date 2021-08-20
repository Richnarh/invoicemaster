/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.khoders.invoicemaster.sms;

import com.khoders.invoicemaster.entities.Client;
import com.khoders.invoicemaster.entities.UserAccountRecord;
import com.khoders.invoicemaster.enums.SMSType;
import com.khoders.resource.utilities.SystemUtils;
import java.io.Serializable;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author pascal
 */
@Entity
@Table(name = "sms")
public class Sms extends UserAccountRecord implements Serializable
{
    @Column(name = "sms_id")
    private String smsId;
    
    @Column(name = "sender_name")
    private String senderName;
    
    @Column(name = "mobile_no")
    private String mobileNo;
    
    @Column(name = "sms_time")
    private LocalDateTime smsTime;
    
    @Column(name = "sms_type")
    @Enumerated(EnumType.STRING)
    private SMSType sMSType;
    
    @Column(name = "message")
    @Lob
    private String message;
    
    @JoinColumn(name = "client", referencedColumnName = "id")
    @ManyToOne
    private Client client;
    
    @JoinColumn(name = "sender_id", referencedColumnName = "id")
    @ManyToOne
    private SenderId senderId;
    
    public String getSmsId()
    {
        return smsId;
    }

    public void setSmsId(String smsId)
    {
        this.smsId = smsId;
    }

    public String getMessage()
    {
        return message;
    }

    public void setMessage(String message)
    {
        this.message = message;
    }

    public String getSenderName()
    {
        return senderName;
    }

    public void setSenderName(String senderName)
    {
        this.senderName = senderName;
    }

    public LocalDateTime getSmsTime()
    {
        return smsTime;
    }

    public void setSmsTime(LocalDateTime smsTime)
    {
        this.smsTime = smsTime;
    }

    public Client getClient()
    {
        return client;
    }

    public void setClient(Client client)
    {
        this.client = client;
    }

    public SenderId getSenderId()
    {
        return senderId;
    }

    public void setSenderId(SenderId senderId)
    {
        this.senderId = senderId;
    }

    public String getMobileNo()
    {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo)
    {
        this.mobileNo = mobileNo;
    }

    public SMSType getsMSType()
    {
        return sMSType;
    }

    public void setsMSType(SMSType sMSType)
    {
        this.sMSType = sMSType;
    }
        
    public void genCode()
    {
        if (getSmsId()!= null)
        {
            setSmsId(getSmsId());
        } else
        {
            setSmsId(SystemUtils.generateCode());
        }
    }
}
