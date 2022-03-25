/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.khoders.invoicemaster.commons;

import com.khoders.invoicemaster.enums.ClientType;
import com.khoders.invoicemaster.enums.DeliveryStatus;
import com.khoders.invoicemaster.enums.DoorType;
import com.khoders.invoicemaster.enums.InvoiceStatus;
import com.khoders.invoicemaster.enums.MessagingType;
import com.khoders.invoicemaster.enums.SMSType;
import com.khoders.resource.enums.Currency;
import com.khoders.resource.enums.DiscountType;
import com.khoders.resource.enums.PaymentMethod;
import com.khoders.resource.enums.PaymentStatus;
import com.khoders.resource.enums.UnitOfMeasurement;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

/**
 *
 * @author pascal
 */
@Named(value = "commonClass")
@SessionScoped
public class CommonClass implements Serializable
{
    public List<InvoiceStatus> getInvoiceStatusList()
    {
        return Arrays.asList(InvoiceStatus.values());
    }
    
    public List<PaymentStatus> getPaymentStatusList()
    {
        return Arrays.asList(PaymentStatus.values());
    }
    
    public List<PaymentMethod> getPaymentMethodList()
    {
        return Arrays.asList(PaymentMethod.values());
    }
    
    public List<Currency> getCurrencyList()
    {
        return Arrays.asList(Currency.values());
    }
    
    public List<UnitOfMeasurement> getUnitOfMeasurementList()
    {
        return Arrays.asList(UnitOfMeasurement.values());
    }
    
    public List<ClientType> getClientTypeList()
    {
        return Arrays.asList(ClientType.values());
    }
    
    public List<DoorType> getDoorTypeList()
    {
        return Arrays.asList(DoorType.values());
    }
    
    public List<MessagingType> getMessagingTypeList()
    {
        return Arrays.asList(MessagingType.values());
    }
    public List<DiscountType> getDiscountTypeList()
    {
        return Arrays.asList(DiscountType.values());
    }
    
    public List<DeliveryStatus> getDeliveryStatusList()
    {
        return Arrays.asList(DeliveryStatus.values());
    }
    
    public List<SMSType> getSmsTypeList()
    {
        return Arrays.asList(SMSType.values());
    }
    
}
