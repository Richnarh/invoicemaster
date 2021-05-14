/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.khoders.invoicemaster.jbeans.controller;

import com.khoders.invoicemaster.entites.Inventory;
import com.khoders.invoicemaster.entites.Invoice;
import com.khoders.invoicemaster.entites.InvoiceItem;
import com.khoders.invoicemaster.entites.PaymentReceipt;
import com.khoders.invoicemaster.listener.AppSession;
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
    @Inject private CrudApi crudApi;
    @Inject private AppSession appSession;
    @Inject private InvoiceService invoiceService;

    private FormView pageView = FormView.listForm();
    private DateRangeUtil dateRange = new DateRangeUtil();

    private Invoice invoice = new Invoice();
    private List<Invoice> invoiceList = new LinkedList<>();

    private PaymentReceipt payment = new PaymentReceipt();
    private List<PaymentReceipt> paymentList = new LinkedList<>();

    private InvoiceItem invoiceItem = new InvoiceItem();
    private List<InvoiceItem> invoiceItemList = new LinkedList<>();
    
    private Inventory inventory = new Inventory();

    private int selectedTabIndex;
    private String optionText;
    private double totalAmount, cashInvoiceAmount;
     
    @PostConstruct
    private void init()
    {
        invoiceList = invoiceService.getInvoiceList();
        getCashInvoice();
        clearInvoice();
    }
        
    public void inventoryProperties()
    {
        if(invoiceItem.getInventory().getSellingPrice() != 0.0)
        {
            invoiceItem.setUnitPrice(invoiceItem.getInventory().getSellingPrice());
        }
    }

    public void initInvoice()
    {
        clearInvoice();
        pageView.restToCreateView();
    }
    
    public void filterInvoice()
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
        if (invoice.getAmountRemaining() == 0.0)
        {
            invoice.setAmountRemaining(invoice.getTotalAmount());
        } else
        {
            invoice.setAmountRemaining(invoice.getAmountRemaining());
        }
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
            totalAmount += (items.getQuantity() * items.getUnitPrice());
        }
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
            
            if (invoiceItem.getUnitPrice() <= 0.0) {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, Msg.setMsg("Please enter price"), null));
                return;
            }

            if (invoiceItem != null) {
               
                totalAmount = invoiceItem.getQuantity() * invoiceItem.getUnitPrice();
                invoiceItem.genCode();
                invoiceItemList.add(invoiceItem);
                invoiceItemList = CollectionList.washList(invoiceItemList, invoiceItem);

                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, Msg.setMsg("Invoice item added"), null));
            } else {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, Msg.setMsg("Invoice item removed!"), null));
            }
            clearInvoiceItem();
        } catch (Exception e) {
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
                    int qtyPurchased = items.getQuantity();
                    int qtyAtInventory = items.getInventory().getQuantity();
                    int qtyAtHand = qtyAtInventory - qtyPurchased;
                    
                    inventory = crudApi.getEm().find(Inventory.class, items.getInventory().getId());
                    inventory.setQuantity(qtyAtHand);
                    
                    crudApi.save(inventory);
                    
                    if(totalAmount != invoice.getTotalAmount())
                    {
                        FacesContext.getCurrentInstance().addMessage(null, 
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, Msg.setMsg("The item total sum: "+(totalAmount)+" is not equivalent to the invoice total: "+invoice.getTotalAmount()), null));
                        return;
                    }

                    crudApi.save(items);
                    
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
        invoiceItemList.remove(invoiceItem);
        totalAmount -= (invoiceItem.getQuantity() * invoiceItem.getUnitPrice());
        optionText = "Update";
    }
    
    public void deleteInvoiceItem(InvoiceItem invoiceItem)
    {
        totalAmount -= (invoiceItem.getQuantity() * invoiceItem.getUnitPrice());
        invoiceItemList.remove(invoiceItem);
    }
  
    public void closePage()
    {
       invoice = new Invoice();
       invoiceItemList = new LinkedList<>();
       totalAmount = 0;
       optionText = "Save Changes";
       selectedTabIndex = 0;
       pageView.restToListView();
    }
    
    public void clearInvoiceItem()
    {
        invoiceItem = new InvoiceItem();
        invoiceItem.setUserAccount(appSession.getCurrentUser());
        optionText = "Save Changes";
        invoiceItem.setInvoice(invoice);
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
        invoice.setUserAccount(appSession.getCurrentUser());
        optionText = "Save Changes";
        selectedTabIndex = 0;
        SystemUtils.resetJsfUI();
    }
    
    
    public int getPaidInvoiceCount()
    {
//        int  count = crudApi.getEm().createQuery("SELECT COUNT(e.paymentStatus) FROM Invoice e WHERE e.paymentStatus=?1", Invoice.class)
//                .setParameter(1, PaymentStatus.FULLY_PAID)
//                        .getFirstResult();
//                
////        int count = ((Integer)typedQuery.getFirstResult());
//        System.out.println("Count -- "+count);
//
        
        invoiceList = invoiceService.getCashInvoiceList();
        
        
        return invoiceList.size();
    }
    
    public void getCashInvoice()
    {
        invoiceList = new LinkedList<>();
        invoiceList = invoiceService.getCashInvoiceList();

        invoiceList.forEach(item ->
        {
            cashInvoiceAmount += item.getTotalAmount();
        });
        System.out.println("cashInvoiceAmount -- "+cashInvoiceAmount);
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

    public List<InvoiceItem> getInvoiceItemList()
    {
        return invoiceItemList;
    }

    public PaymentReceipt getPayment()
    {
        return payment;
    }

    public void setPayment(PaymentReceipt payment)
    {
        this.payment = payment;
    }

    public List<PaymentReceipt> getPaymentList()
    {
        return paymentList;
    }

    public double getCashInvoiceAmount()
    {
        return cashInvoiceAmount;
    }

}
