/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.khoders.invoicemaster.entites;

import com.khoders.invoicemaster.entites.enums.ClientType;
import com.khoders.resource.jpa.BaseModel;
import com.khoders.resource.utilities.SystemUtils;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Lob;
import javax.persistence.Table;

/**
 *
 * @author pascal
 */
@Entity
@Table(name = "client")
public class Client extends BaseModel implements Serializable
{

    @Column(name = "client_code")
    private String clientCode;

    @Column(name = "client_name")
    private String clientName;

    @Column(name = "business_name")
    private String businessName;

    @Column(name = "client_type")
    @Enumerated(EnumType.STRING)
    private ClientType clientType;

    @Column(name = "phone")
    private String phone;

    @Column(name = "email_address")
    private String emailAddress;

    @Column(name = "address")
    private String address;

    @Column(name = "description")
    @Lob
    private String description;

    public String getClientName()
    {
        return clientName;
    }

    public void setClientName(String clientName)
    {
        this.clientName = clientName;
    }

    public ClientType getClientType()
    {
        return clientType;
    }

    public void setClientType(ClientType clientType)
    {
        this.clientType = clientType;
    }

    public String getPhone()
    {
        return phone;
    }

    public void setPhone(String phone)
    {
        this.phone = phone;
    }

    public String getEmailAddress()
    {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress)
    {
        this.emailAddress = emailAddress;
    }

    public String getAddress()
    {
        return address;
    }

    public void setAddress(String address)
    {
        this.address = address;
    }

    public String getClientCode()
    {
        return clientCode;
    }

    public void setClientCode(String clientCode)
    {
        this.clientCode = clientCode;
    }

    public String getBusinessName()
    {
        return businessName;
    }

    public void setBusinessName(String businessName)
    {
        this.businessName = businessName;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public void genCode()
    {
        if (getClientCode() != null)
        {
            setClientCode(getClientCode());
        } else
        {
            setClientCode(SystemUtils.generateCode());
        }
    }

    @Override
    public String toString()
    {
        return clientName;
    }
    
    
}
