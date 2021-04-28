/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.khoders.invoicemaster.entites.model;

import com.khoders.invoicemaster.entites.Inventory;
import com.khoders.invoicemaster.entites.Invoice;
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
    private String companyAddress;
    private String phone;
    private String quotationNumber;
    private String project;
    private String subject;
    private double totalAmount;
    private String description;
    private String logo;
    

    public List<InvoiceItem> invoiceItemList = new LinkedList<>();
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

    public String getLogo()
    {
        return logo;
    }

    public void setLogo(String logo)
    {
        this.logo = logo;
    }

    public String getProject()
    {
        return project;
    }

    public void setProject(String project)
    {
        this.project = project;
    }

    public String getSubject()
    {
        return subject;
    }

    public void setSubject(String subject)
    {
        this.subject = subject;
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

    public List<InvoiceItem> getInvoiceItemList()
    {
        return invoiceItemList;
    }

    public void setInvoiceItemList(List<InvoiceItem> invoiceItemList)
    {
        this.invoiceItemList = invoiceItemList;
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

    public String getCompanyAddress()
    {
        return companyAddress;
    }

    public void setCompanyAddress(String companyAddress)
    {
        this.companyAddress = companyAddress;
    }

    public String getPhone()
    {
        return phone;
    }

    public void setPhone(String phone)
    {
        this.phone = phone;
    }

    public void setClientCode(String clientCode)
    {
        this.clientCode = clientCode;
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
    
    public static class InvoiceItem
    {
        private String itemCode;
        private Inventory inventoryProduct;
        private int quantity;
        private double unitPrice;
        private double charges;
        private double totalAmount;
        private String description;
        private Invoice invoice;

        public String getItemCode()
        {
            return itemCode;
        }

        public void setItemCode(String itemCode)
        {
            this.itemCode = itemCode;
        }

        public Inventory getInventoryProduct()
        {
            return inventoryProduct;
        }

        public void setInventoryProduct(Inventory inventoryProduct)
        {
            this.inventoryProduct = inventoryProduct;
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

        public double getCharges()
        {
            return charges;
        }

        public void setCharges(double charges)
        {
            this.charges = charges;
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

        public Invoice getInvoice()
        {
            return invoice;
        }

        public void setInvoice(Invoice invoice)
        {
            this.invoice = invoice;
        }
        
        
    }
}
