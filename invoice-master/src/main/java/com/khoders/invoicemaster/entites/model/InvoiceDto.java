/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.khoders.invoicemaster.entites.model;

import com.khoders.invoicemaster.entites.Client;
import com.khoders.invoicemaster.entites.Inventory;
import com.khoders.invoicemaster.entites.Invoice;
import com.khoders.invoicemaster.entites.enums.InvoiceType;
import com.khoders.resource.enums.PaymentMethod;
import com.khoders.resource.enums.PaymentStatus;
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
    private LocalDate dueDate;
    private Client client;
    private String invoiceNumber;
    private InvoiceType invoiceType;
    private String project;
    private String subject;
    private PaymentMethod paymentMethod;
    private PaymentStatus paymentStatus;
    private double totalAmount;
    private String description;

    public List<InvoiceItem> invoiceItemList = new LinkedList<>();

    public LocalDate getIssuedDate()
    {
        return issuedDate;
    }

    public void setIssuedDate(LocalDate issuedDate)
    {
        this.issuedDate = issuedDate;
    }

    public LocalDate getDueDate()
    {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate)
    {
        this.dueDate = dueDate;
    }

    public Client getClient()
    {
        return client;
    }

    public void setClient(Client client)
    {
        this.client = client;
    }

    public String getInvoiceNumber()
    {
        return invoiceNumber;
    }

    public void setInvoiceNumber(String invoiceNumber)
    {
        this.invoiceNumber = invoiceNumber;
    }

    public InvoiceType getInvoiceType()
    {
        return invoiceType;
    }

    public void setInvoiceType(InvoiceType invoiceType)
    {
        this.invoiceType = invoiceType;
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

    public PaymentMethod getPaymentMethod()
    {
        return paymentMethod;
    }

    public void setPaymentMethod(PaymentMethod paymentMethod)
    {
        this.paymentMethod = paymentMethod;
    }

    public PaymentStatus getPaymentStatus()
    {
        return paymentStatus;
    }

    public void setPaymentStatus(PaymentStatus paymentStatus)
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
