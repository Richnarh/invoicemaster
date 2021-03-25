/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.khoders.invoicemaster.entites;

import com.khoders.resource.jpa.BaseModel;
import com.khoders.resource.utilities.SystemUtils;
import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author pascal
 */
@Entity
@Table(name = "proforma_invoice")
public class ProformaInvoice extends BaseModel implements Serializable
{
    @Column(name = "issued_date")
    private LocalDate issuedDate = LocalDate.now();
   
    @JoinColumn(name = "client", referencedColumnName = "id")
    @ManyToOne
    private Client client;

    @Column(name = "project")
    private String project;

    @Column(name = "quotation_number")
    private String quotationNumber = SystemUtils.generateCode();

    @Column(name = "subject")
    private String subject;

    @Column(name = "total_amount")
    private double totalAmount;

    @Column(name = "description")
    @Lob
    private String description;

    public LocalDate getIssuedDate()
    {
        return issuedDate;
    }

    public void setIssuedDate(LocalDate issuedDate)
    {
        this.issuedDate = issuedDate;
    }

    public Client getClient()
    {
        return client;
    }

    public void setClient(Client client)
    {
        this.client = client;
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

    @Override
    public String toString()
    {
        return quotationNumber;
    }
}
