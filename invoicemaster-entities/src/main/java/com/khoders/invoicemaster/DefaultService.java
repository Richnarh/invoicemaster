/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.khoders.invoicemaster;

import com.khoders.invoicemaster.entities.AppConfig;
import com.khoders.invoicemaster.entities.ProformaInvoice;
import com.khoders.invoicemaster.sms.SenderId;
import com.khoders.resource.jpa.CrudApi;
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
    
    public String getSenderId(){
        SenderId id = crudApi.getEm().createQuery("SELECT e FROM SenderId e", SenderId.class)
                .getResultStream().findFirst().orElse(null);
        
        return id != null ? id.getSenderIdentity() : null;
    }
    
    public ProformaInvoice getInvoice(String invoiceId){
        return crudApi.getEm().createQuery("SELECT e FROM ProformaInvoice e WHERE e.quotationNumber = :quotationNumber", ProformaInvoice.class)
                .setParameter(ProformaInvoice._quotationNumber, invoiceId)
                .getResultStream().findFirst().orElse(null);
    }
}
