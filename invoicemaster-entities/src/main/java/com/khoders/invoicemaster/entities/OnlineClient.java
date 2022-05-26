/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.khoders.invoicemaster.entities;

import com.khoders.invoicemaster.enums.ClientStatus;
import com.khoders.resource.jpa.BaseModel;
import java.util.LinkedList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 *
 * @author richa
 */
@Entity
@Table(name = "online_client")
public class OnlineClient extends BaseModel
{
    @Column(name = "firstname")
    private String firstname;

    @Column(name = "lastname")
    private String lastname;

    @Column(name = "country")
    private String country;

    @Column(name = "region")
    private String region;

    @Column(name = "city")
    private String city;
    
    @Column(name = "client_status")
    @Enumerated(EnumType.STRING)
    private ClientStatus clientStatus;

    @Column(name = "street_address")
    private String streetAddress;

    @Column(name = "postal_code")
    private String postalCode;

    @Column(name = "receipt_no")
    private String receiptNo;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "email_address")
    private String emailAddress;

    @Column(name = "order_note")
    @Lob
    private String orderNote;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;
    
    @Transient
    private List<SaleItem> saleItemList = new LinkedList<>();

    public String getFirstname()
    {
        return firstname;
    }

    public void setFirstname(String firstname)
    {
        this.firstname = firstname;
    }

    public String getLastname()
    {
        return lastname;
    }

    public void setLastname(String lastname)
    {
        this.lastname = lastname;
    }

    public String getCountry()
    {
        return country;
    }

    public void setCountry(String country)
    {
        this.country = country;
    }

    public String getRegion()
    {
        return region;
    }

    public void setRegion(String region)
    {
        this.region = region;
    }

    public String getCity()
    {
        return city;
    }

    public void setCity(String city)
    {
        this.city = city;
    }

    public String getStreetAddress()
    {
        return streetAddress;
    }

    public void setStreetAddress(String streetAddress)
    {
        this.streetAddress = streetAddress;
    }

    public String getPostalCode()
    {
        return postalCode;
    }

    public void setPostalCode(String postalCode)
    {
        this.postalCode = postalCode;
    }

    public String getPhoneNumber()
    {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber)
    {
        this.phoneNumber = phoneNumber;
    }

    public String getEmailAddress()
    {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress)
    {
        this.emailAddress = emailAddress;
    }

    public String getOrderNote()
    {
        return orderNote;
    }

    public void setOrderNote(String orderNote)
    {
        this.orderNote = orderNote;
    }

    public String getUsername()
    {
        return username;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public String getReceiptNo()
    {
        return receiptNo;
    }

    public void setReceiptNo(String receiptNo)
    {
        this.receiptNo = receiptNo;
    }
    
    public List<SaleItem> getSaleItemList()
    {
        return saleItemList;
    }

    public void setSaleItemList(List<SaleItem> saleItemList)
    {
        this.saleItemList = saleItemList;
    }

    public ClientStatus getClientStatus()
    {
        return clientStatus;
    }

    public void setClientStatus(ClientStatus clientStatus)
    {
        this.clientStatus = clientStatus;
    }

    @Override
    public String toString()
    {
        return firstname +" "+lastname + " - "+phoneNumber;
    }
}
