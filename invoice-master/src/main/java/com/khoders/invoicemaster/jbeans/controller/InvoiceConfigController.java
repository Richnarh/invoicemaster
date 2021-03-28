/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.khoders.invoicemaster.jbeans.controller;

import com.khoders.invoicemaster.entites.Colours;
import com.khoders.invoicemaster.entites.DeliveryTerm;
import com.khoders.invoicemaster.entites.ReceivedDocument;
import com.khoders.invoicemaster.entites.Validation;
import com.khoders.invoicemaster.service.InvoiceService;
import com.khoders.resource.jpa.CrudApi;
import com.khoders.resource.utilities.CollectionList;
import com.khoders.resource.utilities.Msg;
import com.khoders.resource.utilities.SystemUtils;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.component.tabview.TabView;
import org.primefaces.event.TabChangeEvent;

/**
 *
 * @author pascal
 */
@Named(value = "invoiceConfigController")
@SessionScoped
public class InvoiceConfigController implements Serializable
{
    @Inject private CrudApi crudApi;
    @Inject private InvoiceService invoiceService;
    
    private DeliveryTerm deliveryTerm = new DeliveryTerm();
    private List<DeliveryTerm> deliveryTermList = new LinkedList<>();
    
    private Validation validation = new Validation();
    private List<Validation> validationList = new LinkedList<>();
    
    private Colours colours = new Colours();
    private List<Colours> coloursList = new LinkedList<>();
    
    private ReceivedDocument receivedDocument = new ReceivedDocument();
    private List<ReceivedDocument> receivedDocumentList = new LinkedList<>();
    
    private int selectedTabIndex;
    private String optionText;

    @PostConstruct
    private void init()
    {
        deliveryTermList = invoiceService.getDeliveryTermList();
        clearDeliveryTerm();
        
        validationList = invoiceService.getValidationList();
        clearValidation();
        
        coloursList = invoiceService.getColoursList();
        clearColours();
        
        receivedDocumentList = invoiceService.getReceivedDocumentList();
        clearReceivedDocument();
    }

    public void saveDeliveryTerm()
    {
        try
        {
            deliveryTerm.genCode();
            if (crudApi.save(deliveryTerm) != null)
            {
                deliveryTermList = CollectionList.washList(deliveryTermList, deliveryTerm);
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_INFO, Msg.SUCCESS_MESSAGE, null));
                clearDeliveryTerm();
            } else
            {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, Msg.FAILED_MESSAGE, null));
            }
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public void editDeliveryTerm(DeliveryTerm deliveryTerm)
    {
        this.deliveryTerm = deliveryTerm;
        optionText = "Update";
    }

    public void deleteDeliveryTerm(DeliveryTerm deliveryTerm)
    {
        try
        {
            if (crudApi.delete(deliveryTerm))
            {
                deliveryTermList.remove(deliveryTerm);
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_INFO, Msg.SUCCESS_MESSAGE, null));
            } else
            {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, Msg.FAILED_MESSAGE, null));
            }
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    
/* -- Validation -- */
    public void saveValidation()
    {
        try
        {
           validation.genCode();
            if(crudApi.save(validation) != null)
            {
                validationList = CollectionList.washList(validationList, validation);
                FacesContext.getCurrentInstance().addMessage(null, 
                        new FacesMessage(FacesMessage.SEVERITY_INFO, Msg.SUCCESS_MESSAGE, null));
                
                clearValidation();
            }
            else
            {
                FacesContext.getCurrentInstance().addMessage(null, 
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, Msg.FAILED_MESSAGE, null));
            }
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    
    public void editValidation(Validation validation)
    {
        this.validation=validation;
        optionText = "Update";
    }
    
    public void deleteValidation(Validation validation)
    {
        try
        {
            if(crudApi.delete(validation))
            {
                validationList.remove(validation);
                FacesContext.getCurrentInstance().addMessage(null, 
                        new FacesMessage(FacesMessage.SEVERITY_INFO, Msg.SUCCESS_MESSAGE, null));
            }
            else
            {
                FacesContext.getCurrentInstance().addMessage(null, 
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, Msg.FAILED_MESSAGE, null));
            }
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    


/* -- Colour -- */
    public void saveColour()
    {
        try
        {
           colours.genCode();
            if(crudApi.save(colours) != null)
            {
                coloursList = CollectionList.washList(coloursList, colours);
                FacesContext.getCurrentInstance().addMessage(null, 
                        new FacesMessage(FacesMessage.SEVERITY_INFO, Msg.SUCCESS_MESSAGE, null));
                
                clearColours();
            }
            else
            {
                FacesContext.getCurrentInstance().addMessage(null, 
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, Msg.FAILED_MESSAGE, null));
            }
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    
    public void editColours(Colours colours)
    {
        this.colours=colours;
        optionText = "Update";
    }
    
    
    public void deleteColours(Colours colours)
    {
        try
        {
            if(crudApi.delete(colours))
            {
                coloursList.remove(colours);
                FacesContext.getCurrentInstance().addMessage(null, 
                        new FacesMessage(FacesMessage.SEVERITY_INFO, Msg.SUCCESS_MESSAGE, null));
            }
            else
            {
                FacesContext.getCurrentInstance().addMessage(null, 
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, Msg.FAILED_MESSAGE, null));
            }
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }
       
    
/* -- ReceivedDocument -- */
    public void saveReceivedDocument()
    {
        try
        {
           receivedDocument.genCode();
            if(crudApi.save(receivedDocument) != null)
            {
                receivedDocumentList = CollectionList.washList(receivedDocumentList, receivedDocument);
                FacesContext.getCurrentInstance().addMessage(null, 
                        new FacesMessage(FacesMessage.SEVERITY_INFO, Msg.SUCCESS_MESSAGE, null));
                
                clearReceivedDocument();
            }
            else
            {
                FacesContext.getCurrentInstance().addMessage(null, 
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, Msg.FAILED_MESSAGE, null));
            }
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    
    public void editReceivedDocument(ReceivedDocument receivedDocument)
    {
        this.receivedDocument=receivedDocument;
        optionText = "Update";
    }
    
    
    public void deleteReceivedDocument(ReceivedDocument receivedDocument)
    {
        try
        {
            if(crudApi.delete(receivedDocument))
            {
                receivedDocumentList.remove(receivedDocument);
                FacesContext.getCurrentInstance().addMessage(null, 
                        new FacesMessage(FacesMessage.SEVERITY_INFO, Msg.SUCCESS_MESSAGE, null));
            }
            else
            {
                FacesContext.getCurrentInstance().addMessage(null, 
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, Msg.FAILED_MESSAGE, null));
            }
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    
    public void clearDeliveryTerm()
    {
        deliveryTerm = new DeliveryTerm();
        optionText = "Save Changes";
         selectedTabIndex = 0;
        SystemUtils.resetJsfUI();
    }

    public void clearValidation()
    {
        validation = new Validation();
        optionText = "Save Changes";
        selectedTabIndex = 1;
        SystemUtils.resetJsfUI();
    }
    
    public void clearColours()
    {
        colours = new Colours();
        optionText = "Save Changes";
        selectedTabIndex = 2;
        SystemUtils.resetJsfUI();
    }

    public void clearReceivedDocument()
    {
        receivedDocument = new ReceivedDocument();
        optionText = "Save Changes";
        selectedTabIndex = 3;
        SystemUtils.resetJsfUI();
    }

    
    public void onTabChange(TabChangeEvent event)
    {
        try
        {
            TabView tabView = (TabView) event.getComponent();
            selectedTabIndex = tabView.getChildren().indexOf(event.getTab());

        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public DeliveryTerm getDeliveryTerm()
    {
        return deliveryTerm;
    }

    public void setDeliveryTerm(DeliveryTerm deliveryTerm)
    {
        this.deliveryTerm = deliveryTerm;
    }

    public String getOptionText()
    {
        return optionText;
    }

    public List<DeliveryTerm> getDeliveryTermList()
    {
        return deliveryTermList;
    }

    public int getSelectedTabIndex()
    {
        return selectedTabIndex;
    }

    public void setSelectedTabIndex(int selectedTabIndex)
    {
        this.selectedTabIndex = selectedTabIndex;
    }

    public Validation getValidation()
    {
        return validation;
    }

    public void setValidation(Validation validation)
    {
        this.validation = validation;
    }

    public List<Validation> getValidationList()
    {
        return validationList;
    }

    public Colours getColours()
    {
        return colours;
    }

    public void setColours(Colours colours)
    {
        this.colours = colours;
    }

    public List<Colours> getColoursList()
    {
        return coloursList;
    }

    public ReceivedDocument getReceivedDocument() {
        return receivedDocument;
    }

    public void setReceivedDocument(ReceivedDocument receivedDocument) {
        this.receivedDocument = receivedDocument;
    }

    public List<ReceivedDocument> getReceivedDocumentList() {
        return receivedDocumentList;
    }
    
}
