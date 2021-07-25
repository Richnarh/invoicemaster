/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.khoders.invoicemaster.entites.model;

import java.io.InputStream;
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
    private String phone;
    private String gpsAddress;
    private String quotationNumber;
    private double totalAmount;
    private String description;
    private String logo;
    private String branchName;
    private String website;
    private String tinNo;
    private double installationFee;
    private double totalDiscount;
    private double totalPayable;

    public List<ProformaInvoiceItem> invoiceItemList = new LinkedList<>();
    public List<SalesTax> taxList = new LinkedList<>();
    public List<DeliveryTerm> deliveryTermList = new LinkedList<>();
    public List<Validation> validationList = new LinkedList<>();
    public List<Colours> coloursList = new LinkedList<>();
    public List<ReceivedDocument> receivedDocumentList = new LinkedList<>();

    public LocalDate getIssuedDate()
    {
        return issuedDate;
    }

    public void setIssuedDate(LocalDate issuedDate)
    {
        this.issuedDate = issuedDate;
    }

    public String getPhone()
    {
        return phone;
    }

    public void setPhone(String phone)
    {
        this.phone = phone;
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

    public List<ProformaInvoiceItem> getInvoiceItemList()
    {
        return invoiceItemList;
    }

    public void setInvoiceItemList(List<ProformaInvoiceItem> invoiceItemList)
    {
        this.invoiceItemList = invoiceItemList;
    }

    public List<SalesTax> getTaxList()
    {
        return taxList;
    }

    public void setTaxList(List<SalesTax> taxList)
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

    public List<DeliveryTerm> getDeliveryTermList()
    {
        return deliveryTermList;
    }

    public void setDeliveryTermList(List<DeliveryTerm> deliveryTermList)
    {
        this.deliveryTermList = deliveryTermList;
    }

    public List<Validation> getValidationList()
    {
        return validationList;
    }

    public void setValidationList(List<Validation> validationList)
    {
        this.validationList = validationList;
    }

    public List<Colours> getColoursList()
    {
        return coloursList;
    }

    public void setColoursList(List<Colours> coloursList)
    {
        this.coloursList = coloursList;
    }

    public List<ReceivedDocument> getReceivedDocumentList()
    {
        return receivedDocumentList;
    }

    public void setReceivedDocumentList(List<ReceivedDocument> receivedDocumentList)
    {
        this.receivedDocumentList = receivedDocumentList;
    }

    public String getClientCode()
    {
        return clientCode;
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

    public static class DeliveryTerm
    {

        private String deliveryTerm;

        public String getDeliveryTerm()
        {
            return deliveryTerm;
        }

        public void setDeliveryTerm(String deliveryTerm)
        {
            this.deliveryTerm = deliveryTerm;
        }

    }

    public static class Validation
    {

        private String validation;

        public String getValidation()
        {
            return validation;
        }

        public void setValidation(String validation)
        {
            this.validation = validation;
        }

    }

    public static class Colours
    {

        private String colours;

        public String getColours()
        {
            return colours;
        }

        public void setColours(String colours)
        {
            this.colours = colours;
        }
    }

    public static class SalesTax
    {

        private String taxName;
        private double taxRate;
        private double taxAmount;

        public String getTaxName()
        {
            return taxName;
        }

        public void setTaxName(String taxName)
        {
            this.taxName = taxName;
        }

        public double getTaxRate()
        {
            return taxRate;
        }

        public void setTaxRate(double taxRate)
        {
            this.taxRate = taxRate;
        }

        public double getTaxAmount()
        {
            return taxAmount;
        }

        public void setTaxAmount(double taxAmount)
        {
            this.taxAmount = taxAmount;
        }

    }

    public static class ReceivedDocument
    {

        private String receivedDocument;

        public String getReceivedDocument()
        {
            return receivedDocument;
        }

        public void setReceivedDocument(String receivedDocument)
        {
            this.receivedDocument = receivedDocument;
        }

    }

    public static class ProformaInvoiceItem
    {

        private String productCode;
        private InputStream productImage;
        private String frameUnit;
        private String widthHeightUnits;
        private int frameSize;
        private int width;
        private int height;
        private int quantity;
        private double unitPrice;
        private double totalAmount;
        private String description;

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

        public String getDescription()
        {
            return description;
        }

        public void setDescription(String description)
        {
            this.description = description;
        }

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

        public InputStream getProductImage()
        {
            return productImage;
        }

        public void setProductImage(InputStream productImage)
        {
            this.productImage = productImage;
        }

    }
}
