/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.khoders.invoicemaster.service;

import com.khoders.invoicemaster.entities.Inventory;
import com.khoders.invoicemaster.entities.ReturnGood;
import javax.ejb.Stateless;

/**
 *
 * @author richa
 */
@Stateless
public class XtractService
{
    public Inventory extractReturnGoodToInventory(ReturnGood returnGood)
    {
        Inventory inventory=null;
        
        inventory.setProduct(returnGood.getInventory().getProduct());
        
        return inventory;
    }
}
