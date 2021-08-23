/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.khoders.invoicemaster.sms;

import com.khoders.resource.jpa.BaseModel;
import com.khoders.resource.utilities.SystemUtils;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 *
 * @author pascal
 */
@Entity
@Table(name = "sender_id")
public class SenderId extends BaseModel implements Serializable
{
    @Column(name = "sender_identity")
    private String senderIdentity;

    public String getSenderIdentity()
    {
        return senderIdentity;
    }

    public void setSenderIdentity(String senderIdentity)
    {
        this.senderIdentity = senderIdentity;
    }
    
    @Override
    public String toString()
    {
        return senderIdentity;
    }
    
    public boolean checkSenderIdSize()
    {
        return senderIdentity.length() > 11;
    }

    public void genCode()
    {
        if (getSenderIdentity() != null)
        {
            setSenderIdentity(getSenderIdentity());
        } else
        {
            setSenderIdentity(SystemUtils.generateCode());
        }
    }
    
}
