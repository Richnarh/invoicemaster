/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.khoders.invoicemaster.entites;

import com.khoders.resource.enums.Currency;
import com.khoders.resource.enums.UnitOfMeasurement;
import com.khoders.resource.jpa.BaseModel;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 *
 * @author pascal
 */
@Entity
@Table(name = "user_account")
public class UserAccount extends BaseModel implements Serializable
{
    @Column(name = "username")
    private String username;
    
    @Column(name = "phone_number")
    private String phoneNumber;
    
    @Column(name = "address")
    private String address;
    
    @Column(name = "company_branch_name")
    private String companyBranchName;
    
    @Column(name = "currency")
    @Enumerated(EnumType.STRING)
    private Currency currency = Currency.GHS;
    
    @Column(name = "website")
    private String website;
    
    @Column(name = "email")
    private String email;
    
    @Column(name = "frame")
    @Enumerated(EnumType.STRING)
    private UnitOfMeasurement frame = UnitOfMeasurement.INCHES;
    
    @Column(name = "width")
    @Enumerated(EnumType.STRING)
    private UnitOfMeasurement width = UnitOfMeasurement.MILLIMETERS;
    
    @Column(name = "height")
    @Enumerated(EnumType.STRING)
    private UnitOfMeasurement height = UnitOfMeasurement.MILLIMETERS;
    
    @Column(name = "password")
    private String password;
    
    
    @Transient
    private String password2;

    public Currency getCurrency()
    {
        return currency;
    }

    public void setCurrency(Currency currency)
    {
        this.currency = currency;
    }

    public String getUsername()
    {
        return username;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

    public String getPhoneNumber()
    {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber)
    {
        this.phoneNumber = phoneNumber;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }
    
    public String getPassword2()
    {
        return password2;
    }

    public void setPassword2(String password2)
    {
        this.password2 = password2;
    }

    public String getWebsite()
    {
        return website;
    }

    public void setWebsite(String website)
    {
        this.website = website;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public UnitOfMeasurement getFrame()
    {
        return frame;
    }

    public void setFrame(UnitOfMeasurement frame)
    {
        this.frame = frame;
    }
    
    public UnitOfMeasurement getWidth()
    {
        return width;
    }

    public void setWidth(UnitOfMeasurement width)
    {
        this.width = width;
    }

    public UnitOfMeasurement getHeight()
    {
        return height;
    }

    public void setHeight(UnitOfMeasurement height)
    {
        this.height = height;
    }

    public String getAddress()
    {
        return address;
    }

    public void setAddress(String address)
    {
        this.address = address;
    }

    public String getCompanyBranchName()
    {
        return companyBranchName;
    }

    public void setCompanyBranchName(String companyBranchName)
    {
        this.companyBranchName = companyBranchName;
    }
    
}
