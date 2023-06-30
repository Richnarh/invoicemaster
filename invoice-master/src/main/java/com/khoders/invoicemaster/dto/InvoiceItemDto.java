/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.khoders.invoicemaster.dto;

/**
 *
 * @author Pascal
 */
public class InvoiceItemDto extends UserProp{
    private String itemCode;
    private String inventory;
    private String inventoryId;
    private int quantity = 1;
    private double unitPrice;
    private double subTotal;
    private String description;
    private String proformaInvoice;
    private String proformaInvoiceId;

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public String getInventory() {
        return inventory;
    }

    public void setInventory(String inventory) {
        this.inventory = inventory;
    }

    public String getInventoryId() {
        return inventoryId;
    }

    public void setInventoryId(String inventoryId) {
        this.inventoryId = inventoryId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public double getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(double subTotal) {
        this.subTotal = subTotal;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getProformaInvoice() {
        return proformaInvoice;
    }

    public void setProformaInvoice(String proformaInvoice) {
        this.proformaInvoice = proformaInvoice;
    }

    public String getProformaInvoiceId() {
        return proformaInvoiceId;
    }

    public void setProformaInvoiceId(String proformaInvoiceId) {
        this.proformaInvoiceId = proformaInvoiceId;
    }
    
}
