/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.khoders.invoicemaster.entites.model;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author richa
 */
public class InvoiceDto
{
    private LocalDate issuedDate;
    private String clientName;
    private String clientCode;
    private String emailAddress;
    private String address;
    private String boxAddress;
    private String telephoneNo;
    private String phone;
    private String gpsAddress;
    private String branchName;
    private String website;
    
    private String invoiceNumber;
    private String paymentMethod;
    private String paymentStatus;
    private double totalAmount;
    private String description;
    private String logo;

    public List<InvoiceItem> invoiceItemList = new LinkedList<>();

    public LocalDate getIssuedDate()
    {
        return issuedDate;
    }

    public void setIssuedDate(LocalDate issuedDate)
    {
        this.issuedDate = issuedDate;
    }
    
    public String getInvoiceNumber()
    {
        return invoiceNumber;
    }

    public void setInvoiceNumber(String invoiceNumber)
    {
        this.invoiceNumber = invoiceNumber;
    }

    public String getLogo()
    {
        return logo;
    }

    public void setLogo(String logo)
    {
        this.logo = logo;
    }

    public String getClientName()
    {
        return clientName;
    }

    public void setClientName(String clientName)
    {
        this.clientName = clientName;
    }
    
    public String getPaymentMethod()
    {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod)
    {
        this.paymentMethod = paymentMethod;
    }

    public String getPaymentStatus()
    {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus)
    {
        this.paymentStatus = paymentStatus;
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

    public List<InvoiceItem> getInvoiceItemList()
    {
        return invoiceItemList;
    }

    public void setInvoiceItemList(List<InvoiceItem> invoiceItemList)
    {
        this.invoiceItemList = invoiceItemList;
    }

    public String getClientCode()
    {
        return clientCode;
    }

    public void setClientCode(String clientCode)
    {
        this.clientCode = clientCode;
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

    public String getPhone()
    {
        return phone;
    }

    public void setPhone(String phone)
    {
        this.phone = phone;
    }

    public String getGpsAddress()
    {
        return gpsAddress;
    }

    public void setGpsAddress(String gpsAddress)
    {
        this.gpsAddress = gpsAddress;
    }

    public String getBranchName()
    {
        return branchName;
    }

    public void setBranchName(String branchName)
    {
        this.branchName = branchName;
    }

    public String getWebsite()
    {
        return website;
    }

    public void setWebsite(String website)
    {
        this.website = website;
    }
    
    public static class InvoiceItem
    {
        private String productCode;
        private String productName;
        private String frameUnit;
        private String widthHeightUnits;
        private int frameSize;
        private int width;
        private int height;
        private int quantity;
        private double unitPrice;
        private double totalAmount;

        public double getTotalAmount()
        {
            return totalAmount;
        }

        public void setTotalAmount(double totalAmount)
        {
            this.totalAmount = totalAmount;
        }

        public String getProductCode()
        {
            return productCode;
        }

        public void setProductCode(String productCode)
        {
            this.productCode = productCode;
        }

        public String getProductName()
        {
            return productName;
        }

        public void setProductName(String productName)
        {
            this.productName = productName;
        }

        public String getFrameUnit()
        {
            return frameUnit;
        }

        public void setFrameUnit(String frameUnit)
        {
            this.frameUnit = frameUnit;
        }

        public String getWidthHeightUnits()
        {
            return widthHeightUnits;
        }

        public void setWidthHeightUnits(String widthHeightUnits)
        {
            this.widthHeightUnits = widthHeightUnits;
        }

        public int getFrameSize()
        {
            return frameSize;
        }

        public void setFrameSize(int frameSize)
        {
            this.frameSize = frameSize;
        }

        public int getWidth()
        {
            return width;
        }

        public void setWidth(int width)
        {
            this.width = width;
        }

        public int getHeight()
        {
            return height;
        }

        public void setHeight(int height)
        {
            this.height = height;
        }

        public int getQuantity()
        {
            return quantity;
        }

        public void setQuantity(int quantity)
        {
            this.quantity = quantity;
        }

        public double getUnitPrice()
        {
            return unitPrice;
        }

        public void setUnitPrice(double unitPrice)
        {
            this.unitPrice = unitPrice;
        }
       
        
    }
}
