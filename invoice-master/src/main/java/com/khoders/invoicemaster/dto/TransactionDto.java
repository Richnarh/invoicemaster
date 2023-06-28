/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.khoders.invoicemaster.dto;

import com.khoders.invoicemaster.reportData.SalesTaxDto;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author richa
 */
public class TransactionDto extends BaseDto
{
    private String onlineClientId;
    private String onlineClientName;
    private String streetAddress;
    private double cartTotal;
    private double cartQty;
    private String paymentStatus;
    private String deliveryStatus;
    private String paymentMethod;
    
    private String clientName;
    private double subTotalAmount;
    private double totalAmount;
    private double installationFee;
    private double totalDiscount;
    private double totalPayable;
    
    private List<SaleItemDto> invoiceItemList = new LinkedList<>();
    public List<SalesTaxDto> salesTaxList = new LinkedList<>();

    public String getOnlineClientId()
    {
        return onlineClientId;
    }

    public void setOnlineClientId(String onlineClientId)
    {
        this.onlineClientId = onlineClientId;
    }

    public String getOnlineClientName()
    {
        return onlineClientName;
    }

    public void setOnlineClientName(String onlineClientName)
    {
        this.onlineClientName = onlineClientName;
    }

    public String getStreetAddress()
    {
        return streetAddress;
    }

    public void setStreetAddress(String streetAddress)
    {
        this.streetAddress = streetAddress;
    }

    public double getCartTotal()
    {
        return cartTotal;
    }

    public void setCartTotal(double cartTotal)
    {
        this.cartTotal = cartTotal;
    }

    public double getCartQty()
    {
        return cartQty;
    }

    public void setCartQty(double cartQty)
    {
        this.cartQty = cartQty;
    }

    public String getPaymentStatus()
    {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus)
    {
        this.paymentStatus = paymentStatus;
    }

    public String getDeliveryStatus()
    {
        return deliveryStatus;
    }

    public void setDeliveryStatus(String deliveryStatus)
    {
        this.deliveryStatus = deliveryStatus;
    }
    
    public String getClientName()
    {
        return clientName;
    }

    public void setClientName(String clientName)
    {
        this.clientName = clientName;
    }

    public double getSubTotalAmount()
    {
        return subTotalAmount;
    }

    public void setSubTotalAmount(double subTotalAmount)
    {
        this.subTotalAmount = subTotalAmount;
    }

    public double getTotalAmount()
    {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount)
    {
        this.totalAmount = totalAmount;
    }

    public double getInstallationFee()
    {
        return installationFee;
    }

    public void setInstallationFee(double installationFee)
    {
        this.installationFee = installationFee;
    }

    public double getTotalDiscount()
    {
        return totalDiscount;
    }

    public void setTotalDiscount(double totalDiscount)
    {
        this.totalDiscount = totalDiscount;
    }

    public double getTotalPayable()
    {
        return totalPayable;
    }

    public void setTotalPayable(double totalPayable)
    {
        this.totalPayable = totalPayable;
    }

    public List<SaleItemDto> getInvoiceItemList()
    {
        return invoiceItemList;
    }

    public void setInvoiceItemList(List<SaleItemDto> invoiceItemList)
    {
        this.invoiceItemList = invoiceItemList;
    }

    public List<SalesTaxDto> getSalesTaxList()
    {
        return salesTaxList;
    }

    public void setSalesTaxList(List<SalesTaxDto> salesTaxList)
    {
        this.salesTaxList = salesTaxList;
    }
    
    public String getPaymentMethod()
    {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod)
    {
        this.paymentMethod = paymentMethod;
    }
    
    
}
