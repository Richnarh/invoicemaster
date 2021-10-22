/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.khoders.invoicemaster.dto;

import java.time.LocalDateTime;

/**
 *
 * @author richa
 */
public class Receipt
{
    private String receiptNumber;
    private LocalDateTime date;
    private String branchName;
    private String website;
    private String modeOfPayment;
    
    private double totalAmount;
    private double totalTax;
    private double installationFee;
    private double totalPayable;

    public String getReceiptNumber()
    {
        return receiptNumber;
    }

    public void setReceiptNumber(String receiptNumber)
    {
        this.receiptNumber = receiptNumber;
    }

    public LocalDateTime getDate()
    {
        return date;
    }

    public void setDate(LocalDateTime date)
    {
        this.date = date;
    }


    public String getBranchName()
    {
        return branchName;
    }

    public void setBranchName(String branchName)
    {
        this.branchName = branchName;
    }

    public String getWebsite()
    {
        return website;
    }

    public void setWebsite(String website)
    {
        this.website = website;
    }

    public String getModeOfPayment()
    {
        return modeOfPayment;
    }

    public void setModeOfPayment(String modeOfPayment)
    {
        this.modeOfPayment = modeOfPayment;
    }
    
    public double getTotalAmount()
    {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount)
    {
        this.totalAmount = totalAmount;
    }

    public double getTotalTax()
    {
        return totalTax;
    }

    public void setTotalTax(double totalTax)
    {
        this.totalTax = totalTax;
    }

    public double getInstallationFee()
    {
        return installationFee;
    }

    public void setInstallationFee(double installationFee)
    {
        this.installationFee = installationFee;
    }

    public double getTotalPayable()
    {
        return totalPayable;
    }

    public void setTotalPayable(double totalPayable)
    {
        this.totalPayable = totalPayable;
    }
    
    
}
