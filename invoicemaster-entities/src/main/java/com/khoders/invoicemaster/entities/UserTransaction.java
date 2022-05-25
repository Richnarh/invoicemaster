/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.khoders.invoicemaster.entities;

import com.khoders.invoicemaster.enums.DeliveryStatus;
import com.khoders.resource.enums.PaymentStatus;
import com.khoders.resource.jpa.BaseModel;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author richa
 */
@Entity
@Table(name = "user_transaction")
public class UserTransaction extends BaseModel
{
    public static final String _register="register";
    @JoinColumn(name = "register", referencedColumnName = "id")
    @ManyToOne
    private Register register;
    
    @Column(name = "payment_status")
    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;
    
    @Column(name = "delivert_status")
    @Enumerated(EnumType.STRING)
    private DeliveryStatus deliveryStatus;

    public Register getRegister()
    {
        return register;
    }

    public void setRegister(Register register)
    {
        this.register = register;
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
    
    
}
