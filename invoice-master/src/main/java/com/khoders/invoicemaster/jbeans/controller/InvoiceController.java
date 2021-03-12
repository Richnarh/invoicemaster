/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.khoders.invoicemaster.jbeans.controller;

import com.khoders.invoicemaster.entites.Invoice;
import com.khoders.resource.utilities.DateRangeUtil;
import com.khoders.resource.utilities.FormView;
import java.io.Serializable;
import java.util.List;
import javax.enterprise.context.SessionScoped;
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

    private FormView pageView = FormView.listForm();
    private int selectedTabIndex;
    private DateRangeUtil dateRange = new DateRangeUtil();
    private Invoice invoice;
    private List<Invoice> invoiceList;
    
    
    public void initInvoice()
    {
//        clearInvoice();
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

}
