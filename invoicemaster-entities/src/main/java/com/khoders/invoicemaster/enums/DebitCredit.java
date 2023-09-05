/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.khoders.invoicemaster.enums;

import com.khoders.resource.utilities.MsgResolver;

/**
 *
 * @author Pascal
 */
public enum DebitCredit implements MsgResolver {
    DEBIT("DEBIT", "Debit"),
    CREDIT("CREDIT", "Credit");

    private final String code;
    private final String label;

    private DebitCredit(String code, String label) {
        this.code = code;
        this.label = label;
    }

    @Override
    public String toString() {
        return label;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getLabel() {
        return label;
    }
}
