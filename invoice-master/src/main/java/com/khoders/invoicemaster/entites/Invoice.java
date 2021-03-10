/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.khoders.invoicemaster.entites;

import com.khoders.resource.jpa.BaseModel;
import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
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
   
   @Column(name = "project")
   private String project;
   
   @Column(name = "quotation_number")
   private String quotationNumber;
   
   @Column(name = "subject")
   private String subject;
   
}
