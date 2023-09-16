/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.khoders.invoicemaster.dto;

/**
 *
 * @author Richard Narh
 */
public class TaxDto extends BaseDto{
  private String taxId;
  private String taxGroupName;
  private String taxGroupId;
  private String taxName;
  private double taxRate;
  private int reOrder;

    public String getTaxId() {
        return taxId;
    }

    public void setTaxId(String taxId) {
        this.taxId = taxId;
    }

    public String getTaxGroupName() {
        return taxGroupName;
    }

    public void setTaxGroupName(String taxGroupName) {
        this.taxGroupName = taxGroupName;
    }

    public String getTaxGroupId() {
        return taxGroupId;
    }

    public void setTaxGroupId(String taxGroupId) {
        this.taxGroupId = taxGroupId;
    }

    public String getTaxName() {
        return taxName;
    }

    public void setTaxName(String taxName) {
        this.taxName = taxName;
    }

    public double getTaxRate() {
        return taxRate;
    }

    public void setTaxRate(double taxRate) {
        this.taxRate = taxRate;
    }

    public int getReOrder() {
        return reOrder;
    }

    public void setReOrder(int reOrder) {
        this.reOrder = reOrder;
    }
  
}
