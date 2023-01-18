/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.khoders.invoicemaster.service;

import com.khoders.invoicemaster.entities.PaymentData;
import com.khoders.invoicemaster.entities.ProformaInvoice;
import com.khoders.invoicemaster.entities.ProformaInvoiceItem;
import com.khoders.invoicemaster.enums.DeliveryStatus;
import com.khoders.invoicemaster.jbeans.SmsAccess;
import com.khoders.resource.enums.PaymentStatus;
import com.khoders.resource.jpa.CrudApi;
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
        
//    public static ZenophSMS extractParams()
//    {
//        ZenophSMS zsms = new ZenophSMS();
//        try
//        {
//            zsms.setUser(SmsAccess.USERNAME);
//            zsms.setPassword(SmsAccess.PASSWORD);
//            zsms.authenticate();
//            zsms.setMessageType(MSGTYPE.TEXT);
//
//        } catch (Exception e)
//        {
//            e.printStackTrace();
//        }
//
//        return zsms;
//    }
}
