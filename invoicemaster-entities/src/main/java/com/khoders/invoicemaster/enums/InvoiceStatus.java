/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.khoders.invoicemaster.enums;

import com.khoders.resource.utilities.MsgResolver;

/**
 *
 * @author pascal
 */
public enum InvoiceStatus implements MsgResolver
{
    VALID_INVOICE("VALID_INVOICE", "Valid Invoice"),
    OVERDUE_INVOICE("OVERDUE_INVOICE", "Overdue Invoice");
    
    private final String code;
    private final String label;
    
    private InvoiceStatus(String code, String label)
    {
        this.code = code;
        this.label = label;
    }

    @Override
    public String getCode()
    {
        return code;
    }

    @Override
    public String getLabel()
    {
        return label;
    }

    @Override
    public String toString()
    {
        return label;
    }
    
}
