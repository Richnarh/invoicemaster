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
public enum AppVersion implements MsgResolver{
    V1("V1","v1"),
    V2("V2","v2");
    
    private final String code;
    private final String label;
    
    private AppVersion(String code, String label){
        this.code = code;
        this.label = label;
    }

    @Override
    public String getCode() {
       return code;
    }

    @Override
    public String getLabel() {
       return label;
    }

    @Override
    public String toString() {
        return label;
    }
    
}
