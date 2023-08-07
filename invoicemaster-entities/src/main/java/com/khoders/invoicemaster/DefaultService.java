/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.khoders.invoicemaster;

import com.khoders.invoicemaster.entities.AppConfig;
import com.khoders.invoicemaster.entities.Inventory;
import com.khoders.invoicemaster.entities.PaymentData;
import com.khoders.invoicemaster.entities.ProformaInvoice;
import com.khoders.invoicemaster.entities.ProformaInvoiceItem;
import com.khoders.invoicemaster.entities.SalesTax;
import com.khoders.invoicemaster.sms.SenderId;
import com.khoders.resource.jpa.CrudApi;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author Pascal
 */
@Stateless
public class DefaultService {
     @Inject private CrudApi crudApi;
     
    public String getConfigValue(String configName) {
        AppConfig config = crudApi.getEm().createQuery("SELECT e FROM AppConfig e WHERE e.configName = :configName", AppConfig.class)
                .setParameter(AppConfig._configName, configName)
                .getResultStream().findFirst().orElse(null);
        return config != null ? config.getConfigValue() : null;
    }
    
    public AppConfig getAppConfig(String configName) {
        return crudApi.getEm().createQuery("SELECT e FROM AppConfig e WHERE e.configName = :configName", AppConfig.class)
                .setParameter(AppConfig._configName, configName)
                .getResultStream().findFirst().orElse(null);
    }
    
    public String getSenderId(){
        SenderId id = crudApi.getEm().createQuery("SELECT e FROM SenderId e", SenderId.class)
                .setMaxResults(1)
                .getResultStream().findFirst().orElse(null);
        
        return id != null ? id.getSenderIdentity() : null;
    }
    
    public ProformaInvoice getInvoice(String quotationNumber){
        return crudApi.getEm().createQuery("SELECT e FROM ProformaInvoice e WHERE e.quotationNumber = :quotationNumber", ProformaInvoice.class)
                .setParameter(ProformaInvoice._quotationNumber, quotationNumber)
                .getResultStream().findFirst().orElse(null);
    }
    public ProformaInvoice getInvoiceById(String invoiceId){
        return crudApi.getEm().createQuery("SELECT e FROM ProformaInvoice e WHERE e.id = :id", ProformaInvoice.class)
                .setParameter(ProformaInvoice._id, invoiceId)
                .getResultStream().findFirst().orElse(null);
    }
       public boolean deletePaymentData(ProformaInvoice proformaInvoice) {
        int q = crudApi.getEm().createQuery("DELETE FROM PaymentData d WHERE d.proformaInvoice =:proformaInvoice")
                .setParameter(PaymentData._proformaInvoice, proformaInvoice)
                .executeUpdate();
        return q > 0;
    }
    
    public boolean deleteSalesTax(ProformaInvoice proformaInvoice) {
        int q = crudApi.getEm().createQuery("DELETE FROM SalesTax s WHERE s.proformaInvoice =:proformaInvoice")
                .setParameter(SalesTax._proformaInvoice, proformaInvoice)
                .executeUpdate();
        return q > 0;
    }
    
    public boolean deleteSaleItem(ProformaInvoice proformaInvoice) {
        List<ProformaInvoiceItem> salesList = getSaleItemList(proformaInvoice);
        for (ProformaInvoiceItem invoiceItem : salesList) {
            if(invoiceItem.getInventory() == null) continue;
            Inventory inventory = getInventory(invoiceItem.getInventory().getId());
            System.out.println("Inventory found: "+inventory.getInventoryCode());
            int currentQty = inventory.getQuantity();
            int updatedQty = invoiceItem.getQuantity() + currentQty;
            
            inventory.setQuantity(updatedQty);
            crudApi.save(inventory);
        }        
        
        int q = crudApi.getEm().createQuery("DELETE FROM ProformaInvoiceItem p WHERE p.proformaInvoice =:proformaInvoice")
                .setParameter(ProformaInvoiceItem._proformaInvoice, proformaInvoice)
                .executeUpdate();
        return q > 0;
    }

    public List<ProformaInvoiceItem> getSaleItemList(ProformaInvoice proformaInvoice){
        return crudApi.getEm().createQuery("SELECT e FROM ProformaInvoiceItem e WHERE e.proformaInvoice = :proformaInvoice", ProformaInvoiceItem.class)
                .setParameter(ProformaInvoiceItem._proformaInvoice, proformaInvoice)
                .getResultList();
    }

    public Inventory getInventory(String inventoryId) {
        return crudApi.getEm().createQuery("SELECT e FROM Inventory e WHERE e.id = :id", Inventory.class)
                .setParameter(Inventory._id, inventoryId)
                .getResultStream().findFirst().orElse(null);
    }
}
