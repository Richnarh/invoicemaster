/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.khoders.invoicemaster.jbeans.controller;

import com.khoders.invoicemaster.entites.Invoice;
import com.khoders.invoicemaster.entites.InvoiceConfigItems;
import com.khoders.invoicemaster.entites.InvoiceItem;
import com.khoders.invoicemaster.entites.enums.InvoiceType;
import com.khoders.invoicemaster.service.InvoiceService;
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
@Named(value = "invoiceController")
@SessionScoped
public class InvoiceController implements Serializable
{

    @Inject
    private CrudApi crudApi;
    @Inject
    private InvoiceService invoiceService;

    private FormView pageView = FormView.listForm();
    private DateRangeUtil dateRange = new DateRangeUtil();

    private Invoice invoice = new Invoice();
    private List<Invoice> invoiceList = new LinkedList<>();

    private InvoiceItem invoiceItem = new InvoiceItem();
    private List<InvoiceItem> invoiceItemList = new LinkedList<>();

    private InvoiceConfigItems invoiceConfigItems = new InvoiceConfigItems();
    private List<InvoiceConfigItems> invoiceConfigItemsList = new LinkedList<>();
    
    private int selectedTabIndex;
    private String optionText;
    private double totalAmount;
     
    private boolean toggleDisplay = false;

    @PostConstruct
    private void init()
    {
        invoiceList = invoiceService.getInvoiceList();
        clearInvoice();
    }
    
    public void show()
    {
        toggleDisplay = invoice.getInvoiceType() == InvoiceType.STANDARD_INVOICE;
    }
    
    public void inventoryProperties()
    {
        if(invoiceItem.getInventoryProduct().getSellingPrice() != 0.0)
        {
            invoiceItem.setUnitPrice(invoiceItem.getInventoryProduct().getSellingPrice());
        }
    }

    public void initInvoice()
    {
        clearInvoice();
        pageView.restToCreateView();
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
    
    public void filterProformaInvoice()
    {
        selectedTabIndex = 1;
        invoiceList = invoiceService.getProformaInvoice(dateRange, invoice);   
    }
    
    public void reset()
    {
        invoiceList = new LinkedList<>();
    }
    
    public void saveInvoice()
    {
        try
        {
            if (crudApi.save(invoice) != null)
            {
                invoiceList = CollectionList.washList(invoiceList, invoice);
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_INFO, Msg.SUCCESS_MESSAGE, null));

                clearInvoice();
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

    public void manageInvoiceItem(Invoice invoice)
    {
        this.invoice = invoice;
        pageView.restToDetailView();
        
        clearInvoiceItem();
        
        invoiceItemList = invoiceService.getInvoiceItemList(invoice);
        
        for (InvoiceItem items : invoiceItemList) 
        {
            totalAmount += items.getTotalAmount();
            setTotalAmount(totalAmount);
        }
    }

    public void manageInvoiceConfig(Invoice invoice)
    {
        this.invoice = invoice;
        clearInvoiceConfigItems();
        
        invoiceConfigItemsList = invoiceService.getInvoiceConfigItemsList(invoice);
    }

    public void addInvoiceItem()
    {
        try
        {
            if (invoiceItem.getQuantity() <= 0)
            {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, Msg.setMsg("Please enter quantity"), null));
                return;
            }
            
            if(invoiceItem.getUnitPrice() <= 0)
            {
              FacesContext.getCurrentInstance().addMessage(null, 
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, Msg.setMsg("Please enter price"), null));
              return;
            }
            
             if(invoiceItem != null)
              {
                
                totalAmount= invoiceItem.getQuantity() * invoiceItem.getUnitPrice();
                  
                invoiceItem.setTotalAmount(totalAmount);
                
                invoiceItemList.add(invoiceItem);
                invoiceItemList = CollectionList.washList(invoiceItemList, invoiceItem);
                
                  for (InvoiceItem item : invoiceItemList)
                  {
                      System.out.println("Prod name -- "+item.getInventoryProduct().getProductName());
                      System.out.println("Inoice"+item.getInvoice());
                      System.out.println(""+item.getUnitPrice());
                  }
                  System.out.println("invoiceItemList -- "+invoiceItemList.size());
                  
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, Msg.setMsg("Invoice item added"), null));
              }
              else
                {
                   FacesContext.getCurrentInstance().addMessage(null, 
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, Msg.setMsg("Invoice item removed!"), null));
                }
            clearInvoiceItem();
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    
    public void saveAll()
    {
        try 
        {
            if(invoiceItemList != null)
            {
                for (InvoiceItem items : invoiceItemList) 
                {
                    crudApi.save(items);
                    
                    totalAmount += items.getTotalAmount();
                    
                    setTotalAmount(totalAmount);
                    
                }
                
                FacesContext.getCurrentInstance().addMessage(null, 
                        new FacesMessage(FacesMessage.SEVERITY_INFO, Msg.setMsg("Invoice item list saved!"), null));
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
    
    public void editInvoiceItem(InvoiceItem invoiceItem)
    {
        this.invoiceItem = invoiceItem;
        optionText = "Update";
    }
    public void deleteInvoiceItem(InvoiceItem invoiceItem)
    {
        try 
        {
            if(crudApi.delete(invoiceItem))
            {
                invoiceItemList.remove(invoiceItem);
                FacesContext.getCurrentInstance().addMessage(null, 
                        new FacesMessage(FacesMessage.SEVERITY_INFO, Msg.DELETE_MESSAGE, null));
            }
            else
            {
               FacesContext.getCurrentInstance().addMessage(null, 
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, Msg.DELETE_MESSAGE, null));
            }
        } 
        catch (Exception e) 
        {
            e.printStackTrace();
        }
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
            invoiceConfigItems.setInvoice(invoice);
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
       invoice = new Invoice();
       invoiceConfigItems = new InvoiceConfigItems();
       invoiceItemList = new LinkedList<>();
       invoiceConfigItemsList = new LinkedList<>();
       totalAmount = 0;
       optionText = "Save Changes";
       selectedTabIndex = 0;
       pageView.restToListView();
    }
    
    public void clearInvoiceItem()
    {
        invoiceItem = new InvoiceItem();
        optionText = "Save Changes";
        invoiceItem.setInvoice(invoice);
        SystemUtils.resetJsfUI();
    }
    
    public void clearInvoiceConfigItems()
    {
        invoiceConfigItems = new InvoiceConfigItems();
        optionText = "Save Changes";
        SystemUtils.resetJsfUI();
    }

    public void editInvoice(Invoice invoice)
    {
        this.invoice = invoice;
        pageView.restToCreateView();
        optionText = "Update";
    }

    public void clearInvoice()
    {
        invoice = new Invoice();
        optionText = "Save Changes";
        selectedTabIndex = 0;
        SystemUtils.resetJsfUI();
    }

    public FormView getPageView()
    {
        return pageView;
    }

    public void setPageView(FormView pageView)
    {
        this.pageView = pageView;
    }

    public int getSelectedTabIndex()
    {
        return selectedTabIndex;
    }

    public void setSelectedTabIndex(int selectedTabIndex)
    {
        this.selectedTabIndex = selectedTabIndex;
    }

    public DateRangeUtil getDateRange()
    {
        return dateRange;
    }

    public void setDateRange(DateRangeUtil dateRange)
    {
        this.dateRange = dateRange;
    }

    public Invoice getInvoice()
    {
        return invoice;
    }

    public void setInvoice(Invoice invoice)
    {
        this.invoice = invoice;
    }

    public List<Invoice> getInvoiceList()
    {
        return invoiceList;
    }

    public String getOptionText()
    {
        return optionText;
    }

    public InvoiceItem getInvoiceItem()
    {
        return invoiceItem;
    }

    public void setInvoiceItem(InvoiceItem invoiceItem)
    {
        this.invoiceItem = invoiceItem;
    }

    public double getTotalAmount()
    {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount)
    {
        this.totalAmount = totalAmount;
    }

    public boolean isToggleDisplay()
    {
        return toggleDisplay;
    }

    public void setToggleDisplay(boolean toggleDisplay)
    {
        this.toggleDisplay = toggleDisplay;
    }

    public List<InvoiceItem> getInvoiceItemList()
    {
        return invoiceItemList;
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
  
}
