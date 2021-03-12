/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.khoders.invoicemaster.entites.enums;

import com.khoders.resource.utilities.MsgResolver;

/**
 *
 * @author pascal
 */
public enum DoorType implements MsgResolver
{
    WOODEN_DOOR("WOODEN_DOOR", "Wooden Door"),
    METAL_DOOR("METAL_DOOR", "Metal Door"),
    GLASS_DOOR("GLASS_DOOR", "Glass Door");
    
     private final String code;
    private final String label;
    
    private DoorType(String code, String label)
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
