/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.khoders.invoicemaster.jbeans.controller;

import com.khoders.invoicemaster.entites.PosPrinter;
import com.khoders.resource.jpa.CrudApi;
import com.khoders.resource.utilities.CollectionList;
import com.khoders.resource.utilities.Msg;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author richa
 */
@Named(value = "posPrinterController")
@SessionScoped
public class PosPrinterController implements Serializable
{
    @Inject private CrudApi crudApi;
    private PosPrinter posPrinter = new PosPrinter();
    private List<PosPrinter> posPrinterList = new LinkedList<>();
    
    @PostConstruct
    private void init()
    {
        posPrinterList = crudApi.getEm().createQuery("SELECT e FROM PosPrinter e WHERE e.userAccount = ?1", PosPrinter.class).getResultList();
    }
    
    public void savePosPrinter()
    {
        try
        {
            if(crudApi.save(posPrinter)!= null)
            {
                posPrinterList = CollectionList.washList(posPrinterList, posPrinter);
            FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_INFO, Msg.SUCCESS_MESSAGE, null));

                clearPrinter();
            } else

            {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, Msg.FAILED_MESSAGE, null));
            }
        } catch (Exception e)
        {
        }
    }
    public void deletePosPrinter(PosPrinter posPrinter)
    {
        try
        {
            if(crudApi.delete(posPrinter))
            {
                posPrinterList.remove(posPrinter);
            FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_INFO, Msg.SUCCESS_MESSAGE, null));

            } else

            {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, Msg.FAILED_MESSAGE, null));
            }
        } catch (Exception e)
        {
        }
    }

    public void editPosPrinter(PosPrinter posPrinter)
    {
        this.posPrinter = posPrinter;
    }

    public void clearPrinter()
    {
      this.posPrinter = new PosPrinter();
    }

    public PosPrinter getPosPrinter()
    {
        return posPrinter;
    }

    public void setPosPrinter(PosPrinter posPrinter)
    {
        this.posPrinter = posPrinter;
    }

    public List<PosPrinter> getPosPrinterList()
    {
        return posPrinterList;
    }
    
    
}
