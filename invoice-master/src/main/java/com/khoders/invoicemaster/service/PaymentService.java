/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.khoders.invoicemaster.service;

import Zenoph.SMSLib.Enums.MSGTYPE;
import Zenoph.SMSLib.ZenophSMS;
import com.khoders.invoicemaster.entities.Inventory;
import com.khoders.invoicemaster.entities.PaymentData;
import com.khoders.invoicemaster.entities.ProformaInvoice;
import com.khoders.invoicemaster.entities.ProformaInvoiceItem;
import com.khoders.invoicemaster.enums.DeliveryStatus;
import com.khoders.resource.enums.PaymentStatus;
import com.khoders.resource.jpa.CrudApi;
import com.khoders.resource.utilities.DateRangeUtil;
import java.util.Collections;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.TypedQuery;

/**
 *
 * @author richa
 */
@Stateless
public class PaymentService
{
    @Inject private CrudApi crudApi;
    
    public List<PaymentData> getPaymentDataList()
    {
        try
        {
            String qryString = "SELECT e FROM PaymentData e";
            TypedQuery<PaymentData> typedQuery = crudApi.getEm().createQuery(qryString, PaymentData.class);
                                    return typedQuery.getResultList();
            
        } catch (Exception e)
        {
            e.printStackTrace();
        }

        return Collections.emptyList();
    }
    
    public List<PaymentData> getInvoiceByPaymentStatus(PaymentStatus paymentStatus)
    {
        try
        {
            String qryString = "SELECT e FROM PaymentData e WHERE e.paymentStatus=?2";
            TypedQuery<PaymentData> typedQuery = crudApi.getEm().createQuery(qryString, PaymentData.class)
                                    .setParameter(2, paymentStatus);
                                    return typedQuery.getResultList();
            
        } catch (Exception e)
        {
            e.printStackTrace();
        }

        return Collections.emptyList();
    }
    public List<PaymentData> getInvoiceByDeliveryStatus(DeliveryStatus deliveryStatus)
    {
        try
        {
            String qryString = "SELECT e FROM PaymentData e WHERE e.deliveryStatus=?2 ORDER BY e.createdDate DESC";
            TypedQuery<PaymentData> typedQuery = crudApi.getEm().createQuery(qryString, PaymentData.class)
                                    .setParameter(2, deliveryStatus);
                                    return typedQuery.getResultList();
            
        } catch (Exception e)
        {
            e.printStackTrace();
        }

        return Collections.emptyList();
    }
    
        public List<ProformaInvoiceItem> getProformaInvoiceItem(ProformaInvoice proformaInvoice)
    {
        try
        {
            String qryString = "SELECT e FROM ProformaInvoiceItem e WHERE e.proformaInvoice=?1";
            TypedQuery<ProformaInvoiceItem> typedQuery = crudApi.getEm().createQuery(qryString, ProformaInvoiceItem.class);
            typedQuery.setParameter(1, proformaInvoice);
            return typedQuery.getResultList();
            
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        
        return Collections.emptyList();
    }
        
    public List<PaymentData> getInvoiceTransaction(PaymentStatus paymentStatus, DateRangeUtil dateRange) {
        try {
            if (dateRange.getFromDate() == null && dateRange.getToDate() == null && paymentStatus == null){
                return crudApi.getEm().createQuery("SELECT e FROM PaymentData e ORDER BY e.valueDate DESC", PaymentData.class).getResultList();
            } else if ((dateRange.getFromDate() != null || dateRange.getToDate() != null) && paymentStatus == null) {
                String qryString = "SELECT e FROM PaymentData e WHERE e.valueDate BETWEEN :valueDate AND :valueDate ORDER BY e.valueDate DESC";
                return crudApi.getEm().createQuery(qryString, PaymentData.class)
                        .setParameter(PaymentData._valueDate, dateRange.getFromDate())
                        .setParameter(PaymentData._valueDate, dateRange.getToDate()).getResultList();
            }else if (dateRange.getFromDate() == null && dateRange.getToDate() == null && paymentStatus != null) {
                return crudApi.getEm().createQuery("SELECT e FROM PaymentData e WHERE e.paymentStatus=:paymentStatus ORDER BY e.valueDate DESC", PaymentData.class)
                        .setParameter(PaymentData._paymentStatus, paymentStatus)
                        .getResultList();
            } else {
                String qryString = "SELECT e FROM PaymentData e WHERE e.valueDate BETWEEN :valueDate AND :valueDate AND e.paymentStatus=:paymentStatus ORDER BY e.valueDate DESC";
                TypedQuery<PaymentData> typedQuery = crudApi.getEm().createQuery(qryString, PaymentData.class)
                        .setParameter(PaymentData._valueDate, dateRange.getFromDate())
                        .setParameter(PaymentData._valueDate, dateRange.getToDate())
                        .setParameter(PaymentData._paymentStatus, paymentStatus);
                return typedQuery.getResultList();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }

    public ZenophSMS extractParams()
    {
        ZenophSMS zsms = new ZenophSMS();
        try
        {
           com.khoders.invoicemaster.sms.SmsAccess smsAccess = crudApi.getEm().createQuery("SELECT e FROM SmsAccess e", com.khoders.invoicemaster.sms.SmsAccess.class)
                    .getSingleResult();
            if(smsAccess != null){
                
            System.out.println("Username --- "+smsAccess.getUsername());
            System.out.println("Password --- "+smsAccess.getPassword());
            
            zsms.setUser(smsAccess.getUsername());
            zsms.setPassword(smsAccess.getPassword());
            zsms.authenticate();
            zsms.setMessageType(MSGTYPE.TEXT);
          }
        } catch (Exception e)
        {
            e.printStackTrace();
        }

        return zsms;
    }

    public PaymentData getPaymentData(String paymentDataId) {
        return crudApi.find(PaymentData.class, paymentDataId);
    }
}
