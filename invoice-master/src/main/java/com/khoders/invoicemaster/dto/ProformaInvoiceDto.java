/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.khoders.invoicemaster.dto;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author richa
 */
public class ProformaInvoiceDto
{

    private LocalDate issuedDate;
    private LocalDate expiryDate;
    private String clientName;
    private String clientCode;
    private String emailAddress;
    private String address;
    private String boxAddress;
    private String telephoneNo;
    private String gpsAddress;
    private String quotationNumber;
    private String description;
    private String logo;
    private String branchName;
    private String website;
    private String tinNo;
    
    private double subTotalAmount;
    private double totalAmount;
    private double installationFee;
    private double extraDiscount;
    private double totalDiscount;
    private double totalPayable;

    public List<ProformaInvoiceItemDto> invoiceItemList = new LinkedList<>();
    public List<SalesTaxDto> taxList = new LinkedList<>();

    public LocalDate getIssuedDate()
    {
        return issuedDate;
    }

    public void setIssuedDate(LocalDate issuedDate)
    {
        this.issuedDate = issuedDate;
    }

    public String getBranchName()
    {
        return branchName;
    }

    public void setBranchName(String branchName)
    {
        this.branchName = branchName;
    }

    public String getLogo()
    {
        return logo;
    }

    public void setLogo(String logo)
    {
        this.logo = logo;
    }

    public String getWebsite()
    {
        return website;
    }

    public void setWebsite(String website)
    {
        this.website = website;
    }

    public String getClientName()
    {
        return clientName;
    }

    public void setClientName(String clientName)
    {
        this.clientName = clientName;
    }

    public double getTotalAmount()
    {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount)
    {
        this.totalAmount = totalAmount;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public List<ProformaInvoiceItemDto> getInvoiceItemList()
    {
        return invoiceItemList;
    }

    public void setInvoiceItemList(List<ProformaInvoiceItemDto> invoiceItemList)
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

    public LocalDate getExpiryDate()
    {
        return expiryDate;
    }

    public void setExpiryDate(LocalDate expiryDate)
    {
        this.expiryDate = expiryDate;
    }

    public String getQuotationNumber()
    {
        return quotationNumber;
    }

    public void setQuotationNumber(String quotationNumber)
    {
        this.quotationNumber = quotationNumber;
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

    public String getBoxAddress()
    {
        return boxAddress;
    }

    public void setBoxAddress(String boxAddress)
    {
        this.boxAddress = boxAddress;
    }

    public String getTelephoneNo()
    {
        return telephoneNo;
    }

    public void setTelephoneNo(String telephoneNo)
    {
        this.telephoneNo = telephoneNo;
    }

    public String getGpsAddress()
    {
        return gpsAddress;
    }

    public void setGpsAddress(String gpsAddress)
    {
        this.gpsAddress = gpsAddress;
    }

    public void setClientCode(String clientCode)
    {
        this.clientCode = clientCode;
    }

    public String getTinNo()
    {
        return tinNo;
    }

    public void setTinNo(String tinNo)
    {
        this.tinNo = tinNo;
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

    public double getSubTotalAmount()
    {
        return subTotalAmount;
    }

    public void setSubTotalAmount(double subTotalAmount)
    {
        this.subTotalAmount = subTotalAmount;
    }

    public double getExtraDiscount() {
        return extraDiscount;
    }

    public void setExtraDiscount(double extraDiscount) {
        this.extraDiscount = extraDiscount;
    }
    
}
