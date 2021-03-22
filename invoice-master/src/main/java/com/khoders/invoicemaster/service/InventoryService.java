/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.khoders.invoicemaster.service;

import com.khoders.invoicemaster.entites.Inventory;
import com.khoders.resource.jpa.CrudApi;
import java.util.Collections;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.TypedQuery;

/**
 *
 * @author pascal
 */
@Stateless
public class InventoryService
{
     private @Inject CrudApi crudApi;
     
         
    public List<Inventory> getInventoryList()
    {
        try
        {
            String qryString = "SELECT e FROM Inventory e";
            TypedQuery<Inventory> typedQuery = crudApi.getEm().createQuery(qryString, Inventory.class);
                            return typedQuery.getResultList();
            
        } catch (Exception e)
        {
            e.printStackTrace();
        }

        return Collections.emptyList();
    }
}
