/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.khoders.invoicemaster.service;

import com.khoders.invoicemaster.entities.PaymentData;
import com.khoders.invoicemaster.enums.DeliveryStatus;
import com.khoders.invoicemaster.listener.AppSession;
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
    @Inject private AppSession appSession;
    
    public List<PaymentData> getPaymentDataList()
    {
        try
        {
            String qryString = "SELECT e FROM PaymentData e WHERE e.companyBranch=?1";
            TypedQuery<PaymentData> typedQuery = crudApi.getEm().createQuery(qryString, PaymentData.class)
                                    .setParameter(1, appSession.getCompanyBranch());
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
            String qryString = "SELECT e FROM PaymentData e WHERE e.companyBranch=?1 AND e.paymentStatus=?2";
            TypedQuery<PaymentData> typedQuery = crudApi.getEm().createQuery(qryString, PaymentData.class)
                                    .setParameter(1, appSession.getCompanyBranch())
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
            String qryString = "SELECT e FROM PaymentData e WHERE e.companyBranch=?1 AND e.deliveryStatus=?2";
            TypedQuery<PaymentData> typedQuery = crudApi.getEm().createQuery(qryString, PaymentData.class)
                                    .setParameter(1, appSession.getCompanyBranch())
                                    .setParameter(2, deliveryStatus);
                                    return typedQuery.getResultList();
            
        } catch (Exception e)
        {
            e.printStackTrace();
        }

        return Collections.emptyList();
    }
}
