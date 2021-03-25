/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.khoders.invoicemaster.jbeans.controller;

import com.khoders.invoicemaster.entites.InvoiceConfigItems;
import com.khoders.invoicemaster.entites.ProformaInvoice;
import com.khoders.invoicemaster.entites.ProformaInvoiceItem;
import com.khoders.invoicemaster.service.ProformaInvoiceService;
import com.khoders.resource.jpa.CrudApi;
import com.khoders.resource.utilities.CollectionList;
import com.khoders.resource.utilities.DateRangeUtil;
import com.khoders.resource.utilities.FormView;
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
@Named(value = "proformaInvoiceController")
@SessionScoped
public class ProformaInvoiceController implements Serializable
{

    @Inject
    private CrudApi crudApi;
    @Inject
    private ProformaInvoiceService proformaInvoiceService;

    private FormView pageView = FormView.listForm();
    private DateRangeUtil dateRange = new DateRangeUtil();

    private ProformaInvoice proformaInvoice = new ProformaInvoice();
    private ProformaInvoice stdProformaInvoice = new ProformaInvoice();
    private List<ProformaInvoice> proformaInvoiceList = new LinkedList<>();

    private ProformaInvoiceItem proformaInvoiceItem = new ProformaInvoiceItem();
    private List<ProformaInvoiceItem> proformaInvoiceItemList = new LinkedList<>();

    private InvoiceConfigItems invoiceConfigItems = new InvoiceConfigItems();
    private List<InvoiceConfigItems> invoiceConfigItemsList = new LinkedList<>();
    
    private int selectedTabIndex;
    private String optionText;
    private double totalAmount;
     

    @PostConstruct
    private void init()
    {
        proformaInvoiceList = proformaInvoiceService.getProformaInvoiceList();
        clearProformaInvoice();
    }

    public void inventoryProperties()
    {
        if(proformaInvoiceItem.getInventoryProduct().getSellingPrice() != 0.0)
        {
            proformaInvoiceItem.setUnitPrice(proformaInvoiceItem.getInventoryProduct().getSellingPrice());
        }
    }

    public void initProformaInvoice()
    {
        clearProformaInvoice();
        pageView.restToCreateView();
    }
    
    public void convertToSTDProformaInvoice(ProformaInvoice proformaInvoice)
    {
//        ProformaInvoice standardProformaInvoice = proformaInvoiceService.extractFromProformaInvoice(proformaInvoice);
//        proformaInvoiceList = CollectionList.washList(proformaInvoiceList, standardProformaInvoice);
//        editProformaInvoice(standardProformaInvoice);
    }
        
    public void filterProformaInvoice()
    {
        proformaInvoiceList = proformaInvoiceService.getProformaInvoice(dateRange, proformaInvoice);   
    }
    
    public void reset()
    {
        proformaInvoiceList = new LinkedList<>();
    }
    
    public void saveProformaInvoice()
    {
        try
        {
            if (crudApi.save(proformaInvoice) != null)
            {
                proformaInvoiceList = CollectionList.washList(proformaInvoiceList, proformaInvoice);
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_INFO, Msg.SUCCESS_MESSAGE, null));

                clearProformaInvoice();
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

    public void manageProformaInvoiceItem(ProformaInvoice proformaInvoice)
    {
        this.proformaInvoice = proformaInvoice;
        pageView.restToDetailView();
        
        clearProformaInvoiceItem();
        
        proformaInvoiceItemList = proformaInvoiceService.getProformaInvoiceItemList(proformaInvoice);
        
        for (ProformaInvoiceItem items : proformaInvoiceItemList) 
        {
            totalAmount += items.getTotalAmount();
            setTotalAmount(totalAmount);
        }
    }

    public void manageProformaInvoiceConfig(ProformaInvoice proformaInvoice)
    {
        this.proformaInvoice = proformaInvoice;
        clearInvoiceConfigItems();
        
        invoiceConfigItemsList = proformaInvoiceService.getInvoiceConfigItemsList(proformaInvoice);
    }

    public void addProformaInvoiceItem()
    {
        try
        {
            if (proformaInvoiceItem.getQuantity() <= 0)
            {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, Msg.setMsg("Please enter quantity"), null));
                return;
            }
            
            if(proformaInvoiceItem.getUnitPrice() <= 0)
            {
              FacesContext.getCurrentInstance().addMessage(null, 
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, Msg.setMsg("Please enter price"), null));
              return;
            }
            
             if(proformaInvoiceItem != null)
              {
                totalAmount = proformaInvoiceItem.getQuantity() * proformaInvoiceItem.getUnitPrice();
                proformaInvoiceItem.setTotalAmount(totalAmount);
                
                proformaInvoiceItemList.add(proformaInvoiceItem);
                proformaInvoiceItemList = CollectionList.washList(proformaInvoiceItemList, proformaInvoiceItem);
                
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, Msg.setMsg("ProformaInvoice item added"), null));
              }
              else
                {
                   FacesContext.getCurrentInstance().addMessage(null, 
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, Msg.setMsg("ProformaInvoice item removed!"), null));
                }
            clearProformaInvoiceItem();
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    
    public void saveAll()
    {
        try 
        {
            if(proformaInvoiceItemList != null)
            {
                for (ProformaInvoiceItem proformaInvoiceItem : proformaInvoiceItemList) 
                {
                    crudApi.save(proformaInvoiceItem);
                    
                    totalAmount += proformaInvoiceItem.getTotalAmount();
                    
                    setTotalAmount(totalAmount);
                    
                }
                
                FacesContext.getCurrentInstance().addMessage(null, 
                        new FacesMessage(FacesMessage.SEVERITY_INFO, Msg.setMsg("ProformaInvoice item list saved!"), null));
            }
            else
            {
                FacesContext.getCurrentInstance().addMessage(null, 
                        new FacesMessage(FacesMessage.SEVERITY_WARN, Msg.setMsg("The list is empty!"), null));
            }
        } catch (Exception e) 
        {
            e.printStackTrace();
        }
    }
    
    public void editProformaInvoiceItem(ProformaInvoiceItem proformaInvoiceItem)
    {
        this.proformaInvoiceItem = proformaInvoiceItem;
        optionText = "Update";
    }
    public void removeProformaInvoiceItem(ProformaInvoiceItem proformaInvoiceItem)
    {
        proformaInvoiceItemList.remove(proformaInvoiceItem);
    }
    
    public void addInvoiceConfigItems()
    {
        if(invoiceConfigItemsList != null)
        {
            invoiceConfigItemsList = CollectionList.washList(invoiceConfigItemsList, invoiceConfigItems);
            
            clearInvoiceConfigItems();
        }
    }
    
    public void saveInvoiceConfigItems()
    {
        try
        {
            invoiceConfigItems.setProformaInvoice(proformaInvoice);
            if(crudApi.save(invoiceConfigItems) != null)
            {
                invoiceConfigItemsList = CollectionList.washList(invoiceConfigItemsList, invoiceConfigItems);
                FacesContext.getCurrentInstance().addMessage(null, 
                        new FacesMessage(FacesMessage.SEVERITY_INFO, Msg.SUCCESS_MESSAGE, null));
           
            }
            else
            {
              FacesContext.getCurrentInstance().addMessage(null, 
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, Msg.FAILED_MESSAGE, null));  
            }
             clearInvoiceConfigItems();
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    
    public void editInvoiceConfigItems(InvoiceConfigItems invoiceConfigItems)
    {
        this.invoiceConfigItems=invoiceConfigItems;
        optionText = "Update";
    }

    public void deleteInvoiceConfigItems(InvoiceConfigItems invoiceConfigItems
)    {
        try
        {
            if(crudApi.delete(invoiceConfigItems))
            {
                invoiceConfigItemsList.remove(invoiceConfigItems);
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
    
    public void closePage()
    {
       proformaInvoice = new ProformaInvoice();
       invoiceConfigItems = new InvoiceConfigItems();
       proformaInvoiceItemList = new LinkedList<>();
       invoiceConfigItemsList = new LinkedList<>();
       totalAmount = 0;
       optionText = "Save Changes";
       pageView.restToListView();
    }
    
    public void clearProformaInvoiceItem()
    {
        proformaInvoiceItem = new ProformaInvoiceItem();
        optionText = "Save Changes";
        proformaInvoiceItem.setProformaInvoice(proformaInvoice);
        SystemUtils.resetJsfUI();
    }
    
    public void clearInvoiceConfigItems()
    {
        invoiceConfigItems = new InvoiceConfigItems();
        optionText = "Save Changes";
        SystemUtils.resetJsfUI();
    }

    public void editProformaInvoice(ProformaInvoice proformaInvoice)
    {
        this.proformaInvoice = proformaInvoice;
        pageView.restToCreateView();
        optionText = "Update";
    }

    public void clearProformaInvoice()
    {
        proformaInvoice = new ProformaInvoice();
        optionText = "Save Changes";
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
    public FormView getPageView()
    {
        return pageView;
    }

    public void setPageView(FormView pageView)
    {
        this.pageView = pageView;
    }

    public DateRangeUtil getDateRange()
    {
        return dateRange;
    }

    public void setDateRange(DateRangeUtil dateRange)
    {
        this.dateRange = dateRange;
    }

    public ProformaInvoice getProformaInvoice()
    {
        return proformaInvoice;
    }

    public void setProformaInvoice(ProformaInvoice proformaInvoice)
    {
        this.proformaInvoice = proformaInvoice;
    }

    public List<ProformaInvoice> getProformaInvoiceList()
    {
        return proformaInvoiceList;
    }

    public String getOptionText()
    {
        return optionText;
    }

    public ProformaInvoiceItem getProformaInvoiceItem()
    {
        return proformaInvoiceItem;
    }

    public void setProformaInvoiceItem(ProformaInvoiceItem proformaInvoiceItem)
    {
        this.proformaInvoiceItem = proformaInvoiceItem;
    }
    
    public double getTotalAmount()
    {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount)
    {
        this.totalAmount = totalAmount;
    }

    public List<ProformaInvoiceItem> getProformaInvoiceItemList()
    {
        return proformaInvoiceItemList;
    }
    
    public InvoiceConfigItems getInvoiceConfigItems()
    {
        return invoiceConfigItems;
    }

    public void setInvoiceConfigItems(InvoiceConfigItems invoiceConfigItems)
    {
        this.invoiceConfigItems = invoiceConfigItems;
    }

    public List<InvoiceConfigItems> getInvoiceConfigItemsList()
    {
        return invoiceConfigItemsList;
    }
    
    public ProformaInvoice getStdProformaInvoice()
    {
        return stdProformaInvoice;
    }

    public void setStdProformaInvoice(ProformaInvoice stdProformaInvoice)
    {
        this.stdProformaInvoice = stdProformaInvoice;
    }

    public int getSelectedTabIndex()
    {
        return selectedTabIndex;
    }

    public void setSelectedTabIndex(int selectedTabIndex)
    {
        this.selectedTabIndex = selectedTabIndex;
    }
  
}
