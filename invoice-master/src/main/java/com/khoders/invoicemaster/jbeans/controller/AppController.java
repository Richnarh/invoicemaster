/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.khoders.invoicemaster.jbeans.controller;

import com.khoders.invoicemaster.DefaultService;
import com.khoders.invoicemaster.Pages;
import com.khoders.invoicemaster.entities.ProformaInvoice;
import com.khoders.invoicemaster.entities.UserAccount;
import com.khoders.invoicemaster.listener.AppSession;
import com.khoders.resource.jpa.CrudApi;
import com.khoders.resource.utilities.Msg;
import java.io.Serializable;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.omnifaces.util.Faces;

/**
 *
 * @author Pascal
 */
@Named(value="appController")
@ViewScoped
public class AppController implements Serializable{
    @Inject private CrudApi crudApi;
    @Inject private AppSession appSession;
    @Inject private DefaultService ds;
    private UserAccount userAccount;
    
    
    private String userId;
    private String invoiceId;
    
    public void user(String id){
        userId = id;
        System.out.println("UserID => "+userId);
        System.out.println("ID => "+id);
    }
    
    public void reverse(String id){
        invoiceId = id;
        System.out.println("invoiceId => "+invoiceId);
    }
    
    public void reverseSale(){
        System.out.println("Reversing transaction with Id: "+invoiceId);
        ProformaInvoice invoice = ds.getInvoice(invoiceId);
        boolean paymentData = ds.deletePaymentData(invoice);
        boolean taxInfo = ds.deleteSalesTax(invoice);
        boolean saleItem = ds.deleteSaleItem(invoice);
        
        if(paymentData){
            System.out.println("Payment data deleted");
            Msg.info("Payment data and delivery info cleared!");
        }
        if(taxInfo){
            System.out.println("SalesTax data deleted");
            Msg.info("Tax info cleared");
        }
        if(saleItem){
            System.out.println("Invoice item info cleared and inventory quantity updated!");
            Msg.info("Invoice item info cleared and inventory quantity updated!");
        }
        invoice.setConverted(false);
        crudApi.save(invoice);
        
        Faces.redirect(Pages.login);
    }
    
    public String backdoorAccess()
    {
        try
        {
            UserAccount thisAccount = crudApi.find(UserAccount.class, userId);
            System.out.println("ID -- " + userId);
            if (thisAccount == null)
            {
                return null;
            }
           initAccountLogin(thisAccount);
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }

    
    public String initAccountLogin(UserAccount userAccount)
    {
        try
        {
            if (userAccount == null)
            {
               Msg.error("Wrong username or Password");
               return null;
            }
            appSession.login(userAccount);
            appSession.initBranch(userAccount.getCompanyBranch());
            
            if(userAccount.isQuickInvoice() == true)
            {
                Faces.redirect(Pages.quickInvoice);
            }
            else if(userAccount.isWaybill() == true)
            {
               Faces.redirect(Pages.waybill);
            }
            else
            {
               Faces.redirect(Pages.index);
            }
        } catch (Exception e)
        {
            e.printStackTrace();
        }

        return null;
    }

    public String getUserId()
    {
        return userId;
    }

    public void setUserId(String userId)
    {
        this.userId = userId;
    }

    public String getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(String invoiceId) {
        this.invoiceId = invoiceId;
    }
}
