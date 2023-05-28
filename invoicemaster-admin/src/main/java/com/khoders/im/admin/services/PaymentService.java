/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.khoders.im.admin.services;

import com.khoders.im.admin.SmsAccess;
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

    @Inject
    private CrudApi crudApi;

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
            String qryString = "SELECT e FROM PaymentData e WHERE e.paymentStatus=?1 ORDER BY e.valueDate DESC";
            TypedQuery<PaymentData> typedQuery = crudApi.getEm().createQuery(qryString, PaymentData.class)
                    .setParameter(1, paymentStatus);
            return typedQuery.getResultList();

        } catch (Exception e)
        {
            e.printStackTrace();
        }

        return Collections.emptyList();
    }

    public List<PaymentData> getInvoiceTransaction(PaymentStatus paymentStatus, DateRangeUtil dateRange)
    {
        try
        {
            if (dateRange.getFromDate() == null && dateRange.getToDate() == null && paymentStatus == null)
            {
                String queryString = "SELECT e FROM PaymentData e ORDER BY e.valueDate DESC";
                TypedQuery<PaymentData> typedQuery = crudApi.getEm().createQuery(queryString, PaymentData.class);
                return typedQuery.getResultList();

            } else if ((dateRange.getFromDate() != null || dateRange.getToDate() != null) && paymentStatus == null)
            {
                String qryString = "SELECT e FROM PaymentData e WHERE e.valueDate BETWEEN ?1 AND ?2 ORDER BY e.valueDate DESC";
                TypedQuery<PaymentData> typedQuery = crudApi.getEm().createQuery(qryString, PaymentData.class)
                        .setParameter(1, dateRange.getFromDate())
                        .setParameter(2, dateRange.getToDate());
                return typedQuery.getResultList();
            } else
            {
                String qryString = "SELECT e FROM PaymentData e WHERE e.valueDate BETWEEN ?1 AND ?2 AND e.paymentStatus=?3 ORDER BY e.valueDate DESC";
                TypedQuery<PaymentData> typedQuery = crudApi.getEm().createQuery(qryString, PaymentData.class)
                        .setParameter(1, dateRange.getFromDate())
                        .setParameter(2, dateRange.getToDate())
                        .setParameter(3, paymentStatus);
                return typedQuery.getResultList();
            }
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
            String qryString = "SELECT e FROM PaymentData e WHERE e.deliveryStatus=?2";
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
