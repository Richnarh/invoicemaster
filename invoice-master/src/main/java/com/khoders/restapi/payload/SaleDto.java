/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.khoders.restapi.payload;

import com.khoders.invoicemaster.dto.SalesTaxDto;
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
    
    private String clientName;
    private double subTotalAmount;
    private double totalAmount;
    private double installationFee;
    private double totalDiscount;
    private double totalPayable;

    private List<SaleItemDto> invoiceItemList = new LinkedList<>();
    public List<SalesTaxDto> taxList = new LinkedList<>();

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

    public double getSubTotalAmount()
    {
        return subTotalAmount;
    }

    public void setSubTotalAmount(double subTotalAmount)
    {
        this.subTotalAmount = subTotalAmount;
    }

    public double getTotalAmount()
    {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount)
    {
        this.totalAmount = totalAmount;
    }

    public double getInstallationFee()
    {
        return installationFee;
    }

    public void setInstallationFee(double installationFee)
    {
        this.installationFee = installationFee;
    }

    public double getTotalDiscount()
    {
        return totalDiscount;
    }

    public void setTotalDiscount(double totalDiscount)
    {
        this.totalDiscount = totalDiscount;
    }

    public double getTotalPayable()
    {
        return totalPayable;
    }

    public void setTotalPayable(double totalPayable)
    {
        this.totalPayable = totalPayable;
    }

    public List<SaleItemDto> getInvoiceItemList()
    {
        return invoiceItemList;
    }

    public void setInvoiceItemList(List<SaleItemDto> invoiceItemList)
    {
        this.invoiceItemList = invoiceItemList;
    }

    public List<SalesTaxDto> getTaxList()
    {
        return taxList;
    }

    public void setTaxList(List<SalesTaxDto> taxList)
    {
        this.taxList = taxList;
    }

    public String getClientName()
    {
        return clientName;
    }

    public void setClientName(String clientName)
    {
        this.clientName = clientName;
    }
    
}
