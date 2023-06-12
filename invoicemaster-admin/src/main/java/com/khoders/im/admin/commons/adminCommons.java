/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.khoders.im.admin.commons;

import com.khoders.invoicemaster.enums.ActionType;
import com.khoders.invoicemaster.enums.DeliveryStatus;
import com.khoders.invoicemaster.enums.MessagingType;
import com.khoders.invoicemaster.enums.SMSType;
import com.khoders.resource.enums.AccessLevel;
import com.khoders.resource.enums.PaymentMethod;
import com.khoders.resource.enums.PaymentStatus;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

/**
 *
 * @author richa
 */
@Named(value = "adminCommons")
@SessionScoped
public class adminCommons implements Serializable
{
    public List<AccessLevel> getAccessLevelList()
    {
        return Arrays.asList(AccessLevel.values());
    }
   public List<MessagingType> getMessagingTypeList()
    {
        return Arrays.asList(MessagingType.values());
    }
    public List<PaymentStatus> getPaymentStatusList()
    {
        return Arrays.asList(PaymentStatus.values());
    }
    public List<PaymentMethod> getPaymentMethodList()
    {
        return Arrays.asList(PaymentMethod.values());
    }
    public List<DeliveryStatus> getDeliveryStatusList()
    {
        return Arrays.asList(DeliveryStatus.values());
    }
    public List<ActionType> getActionTypeList()
    {
        return Arrays.asList(ActionType.values());
    }
    public List<SMSType> getSmsTypeList()
    {
        return Arrays.asList(SMSType.values());
    }
    
}
