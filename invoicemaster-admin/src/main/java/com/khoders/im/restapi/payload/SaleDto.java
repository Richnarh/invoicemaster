/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.khoders.im.restapi.payload;

import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author richa
 */
public class SaleDto extends BaseDto
{
    private String firstname;
    private String lastname;
    private String country;
    private String region;
    private String city;
    private String streetAddress;
    private String postalCode;
    private String phoneNumber;
    private String emailAddress;
    private String orderNote;
    private String username;
    private String password;

    private List<SaleItemDto> saleItemList = new LinkedList<>();

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

    public List<SaleItemDto> getSaleItemList()
    {
        return saleItemList;
    }

    public void setSaleItemList(List<SaleItemDto> saleItemList)
    {
        this.saleItemList = saleItemList;
    }
    
}
