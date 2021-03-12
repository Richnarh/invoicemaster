/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.khoders.invoicemaster.entites;

import com.khoders.invoicemaster.entites.enums.InvoiceType;
import com.khoders.resource.enums.PaymentMethod;
import com.khoders.resource.jpa.BaseModel;
import com.khoders.resource.utilities.SystemUtils;
import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author pascal
 */
@Entity
@Table(name = "invoice")
public class Invoice extends BaseModel implements Serializable{
   @Column(name = "invoice_code")
   private String invoiceCode;
   
   @Column(name = "invoice_date")
   private LocalDate invoiceDate;
   
   @JoinColumn(name = "invoice_item", referencedColumnName = "id")
   @ManyToOne
   private InvoiceItem invoiceItem;
   
   @JoinColumn(name = "client", referencedColumnName = "id")
   @ManyToOne
   private Client client;
   
   @Column(name = "invoice_number")
   private  String invoiceNumber;
   
   @JoinColumn(name = "delivery_term", referencedColumnName = "id")
   @ManyToOne
   private DeliveryTerm deliveryTerm;
   
   @Column(name = "invoice_type")
   @Enumerated(EnumType.STRING)
   private InvoiceType invoiceType;
   
   @Column(name = "project")
   private String project;
   
   @Column(name = "quotation_number")
   private String quotationNumber;
   
   @Column(name = "subject")
   private String subject;
   
   @Column(name = "payment_method")
   @Enumerated(EnumType.STRING)
   private PaymentMethod paymentMethod;

    public String getInvoiceCode()
    {
        return invoiceCode;
    }

    public void setInvoiceCode(String invoiceCode)
    {
        this.invoiceCode = invoiceCode;
    }

    public LocalDate getInvoiceDate()
    {
        return invoiceDate;
    }

    public void setInvoiceDate(LocalDate invoiceDate)
    {
        this.invoiceDate = invoiceDate;
    }

    public InvoiceItem getInvoiceItem()
    {
        return invoiceItem;
    }

    public void setInvoiceItem(InvoiceItem invoiceItem)
    {
        this.invoiceItem = invoiceItem;
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

    public DeliveryTerm getDeliveryTerm()
    {
        return deliveryTerm;
    }

    public void setDeliveryTerm(DeliveryTerm deliveryTerm)
    {
        this.deliveryTerm = deliveryTerm;
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

    public String getQuotationNumber()
    {
        return quotationNumber;
    }

    public void setQuotationNumber(String quotationNumber)
    {
        this.quotationNumber = quotationNumber;
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
   
   
    public void genInvoiceCode()
    {
        if (getInvoiceCode()!= null)
        {
            setInvoiceCode(getInvoiceCode());
        } else
        {
            setInvoiceCode(SystemUtils.generateCode());
        }
    }
    public void genQuotationNumber()
    {
        if (getQuotationNumber()!= null)
        {
            setQuotationNumber(getQuotationNumber());
        } else
        {
            setQuotationNumber(SystemUtils.generateCode());
        }
    }
}
