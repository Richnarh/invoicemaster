/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.khoders.invoicemaster.commons;

import com.khoders.invoicemaster.entites.Client;
import com.khoders.invoicemaster.entites.Colours;
import com.khoders.invoicemaster.entites.DeliveryTerm;
import com.khoders.invoicemaster.entites.Inventory;
import com.khoders.invoicemaster.entites.Invoice;
import com.khoders.invoicemaster.entites.ReceivedDocument;
import com.khoders.invoicemaster.entites.Validation;
import com.khoders.invoicemaster.service.InvoiceService;
import com.khoders.invoicemaster.service.UserAccountService;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.Asynchronous;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author pascal
 */
@Named(value = "userCommonClass")
@SessionScoped
public class UserCommonClass implements Serializable{
    @Inject private InvoiceService invoiceService;
    @Inject private UserAccountService userAccountService;
    
    private List<DeliveryTerm> deliveryTermList = new LinkedList<>();
    private List<Validation> validationList = new LinkedList<>();
    private List<Colours> coloursList = new LinkedList<>();
    private List<Client> clientList = new LinkedList<>();
    private List<Inventory> inventoryList = new LinkedList<>();
    private List<Invoice> invoiceList = new LinkedList<>();
    private List<ReceivedDocument> receivedDocumentList = new LinkedList<>();
    
    @PostConstruct
    @Asynchronous
    public void init()
    {
        deliveryTermList = invoiceService.getDeliveryTermList();
        validationList = invoiceService.getValidationList();
        invoiceList = invoiceService.getInvoiceList();
        clientList = userAccountService.getClientList();
        coloursList = invoiceService.getColoursList();
        receivedDocumentList = invoiceService.getReceivedDocumentList();
        inventoryList = invoiceService.getInventoryList();
    }

    public List<DeliveryTerm> getDeliveryTermList()
    {
        return deliveryTermList;
    }
    
    public List<Validation> getValidationList()
    {
        return validationList;
    }
    
    public List<Client> getClientList()
    {
        return clientList;
    }

    public List<Colours> getColoursList()
    {
        return coloursList;
    }

    public List<Inventory> getInventoryList()
    {
        return inventoryList;
    }

    public List<ReceivedDocument> getReceivedDocumentList() {
        return receivedDocumentList;
    }

    public List<Invoice> getInvoiceList()
    {
        return invoiceList;
    }

    
}
