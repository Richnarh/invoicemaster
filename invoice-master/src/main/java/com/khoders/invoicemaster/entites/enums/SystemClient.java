/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.khoders.invoicemaster.entites.enums;

/**
 *
 * @author richa
 */
public enum SystemClient
{
    WALK_IN_CUSTOMER("WALK_IN_CUSTOMER", "Walk-in-customer"),
    BACK_DOOR_CUSTOMER("BACK_DOOR_CUSTOMER", "Back-door-customer");
    
    private final String label;
    private final String code;
    
    private SystemClient(String label, String code)
    {
        this.label = label;
        this.code = code;
    }

    @Override
    public String toString()
    {
        return label;
    }   
}
