/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.khoders.invoicemaster.jbeans.controller;

import com.khoders.invoicemaster.entities.PaymentData;
import com.khoders.invoicemaster.enums.DeliveryStatus;
import com.khoders.invoicemaster.listener.AppSession;
import com.khoders.invoicemaster.service.PaymentService;
import com.khoders.resource.enums.PaymentStatus;
import com.khoders.resource.jpa.CrudApi;
import com.khoders.resource.utilities.CollectionList;
import com.khoders.resource.utilities.Msg;
import com.khoders.resource.utilities.SystemUtils;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author khoders
 */
@Named(value = "paymentDataController")
@SessionScoped
public class PaymentDataController implements Serializable{
    @Inject CrudApi crudApi;
    @Inject AppSession appSession;
    @Inject PaymentService paymentService;
    
    private String optionText;
    
    private PaymentData paymentData = new PaymentData();
    private List<PaymentData> paymentDataStatusList = new LinkedList<>();
    private List<PaymentData> paymentDataDeliveryList = new LinkedList<>();
    
    private PaymentStatus paymentStatus;
    private DeliveryStatus deliveryStatus;
    
    @PostConstruct
    private void init()
    {
        clearPaymentData();
        
//        paymentDataList = paymentService.getPaymentDataList();
    }
    
    public void fetchByPaymentStatus()
    {
        if(paymentStatus == null) return;
        paymentDataStatusList = paymentService.getInvoiceByPaymentStatus(paymentStatus);
    }
    public void fetchByDeliveryStatus()
    {
        if(paymentStatus == null) return;
        paymentDataDeliveryList = paymentService.getInvoiceByDeliveryStatus(deliveryStatus);
    }
    
    public void updatePaymentData(PaymentData paymentData)
    {
        this.paymentData = crudApi.find(PaymentData.class, paymentData.getId());
        this.paymentData.setPaymentStatus(paymentData.getPaymentStatus());  
    }
    public void updateDeliveryData(PaymentData paymentData)
    {
        this.paymentData = crudApi.find(PaymentData.class, paymentData.getId());
        this.paymentData.setDeliveryStatus(paymentData.getDeliveryStatus());  
    }
    
   public void savePaymentData()
    {
        System.out.println("payment status => "+this.paymentData.getPaymentStatus());
        System.out.println("delivery status => "+this.paymentData.getDeliveryStatus());
        
        try 
        {
          if(crudApi.save(paymentData) != null)
          {
              FacesContext.getCurrentInstance().addMessage(null, 
                        new FacesMessage(FacesMessage.SEVERITY_INFO, Msg.SUCCESS_MESSAGE, null)); 
          }
          else
          {
              FacesContext.getCurrentInstance().addMessage(null, 
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, Msg.FAILED_MESSAGE, null));
          }
           clearPaymentData();
        } catch (Exception e) 
        {
            e.printStackTrace();
        }
    }
   
    public void editPaymentData(PaymentData paymentData)
    {
       optionText = "Update";
       this.paymentData=paymentData;
    }
    
    public void deletePaymentData(PaymentData paymentData)
    {
        try
        {
          if(crudApi.delete(paymentData))
          {
              paymentDataStatusList.remove(paymentData);
              
              FacesContext.getCurrentInstance().addMessage(null, 
                        new FacesMessage(FacesMessage.SEVERITY_INFO, Msg.DELETE_MESSAGE, null)); 
          }
          else
          {
              FacesContext.getCurrentInstance().addMessage(null, 
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, Msg.FAILED_MESSAGE, null));
          }
        } catch (Exception e) 
        {
            e.printStackTrace();
        }
    }
    
    public void deleteDeliveryData(PaymentData paymentData)
    {
        try
        {
          if(crudApi.delete(paymentData))
          {
              paymentDataDeliveryList.remove(paymentData);
              
              FacesContext.getCurrentInstance().addMessage(null, 
                        new FacesMessage(FacesMessage.SEVERITY_INFO, Msg.DELETE_MESSAGE, null)); 
          }
          else
          {
              FacesContext.getCurrentInstance().addMessage(null, 
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, Msg.FAILED_MESSAGE, null));
          }
        } catch (Exception e) 
        {
            e.printStackTrace();
        }
    }
    
    public void clearPaymentData() {
        paymentData = new PaymentData();
        paymentData.setUserAccount(appSession.getCurrentUser());
        paymentData.setCompanyBranch(appSession.getCompanyBranch());
        optionText = "Save Changes";
        SystemUtils.resetJsfUI();
    }

    public String getOptionText() {
        return optionText;
    }

    public void setOptionText(String optionText) {
        this.optionText = optionText;
    }

    public PaymentData getPaymentData()
    {
        return paymentData;
    }

    public void setPaymentData(PaymentData paymentData)
    {
        this.paymentData = paymentData;
    }

    public PaymentStatus getPaymentStatus()
    {
        return paymentStatus;
    }

    public void setPaymentStatus(PaymentStatus paymentStatus)
    {
        this.paymentStatus = paymentStatus;
    }

    public DeliveryStatus getDeliveryStatus()
    {
        return deliveryStatus;
    }

    public void setDeliveryStatus(DeliveryStatus deliveryStatus)
    {
        this.deliveryStatus = deliveryStatus;
    }

    public List<PaymentData> getPaymentDataStatusList()
    {
        return paymentDataStatusList;
    }

    public List<PaymentData> getPaymentDataDeliveryList()
    {
        return paymentDataDeliveryList;
    }
    
}
