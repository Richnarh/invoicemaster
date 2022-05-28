/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.khoders.invoicemaster.jbeans.controller;

import com.khoders.invoicemaster.entities.OnlineClient;
import com.khoders.invoicemaster.entities.SaleItem;
import com.khoders.invoicemaster.entities.SalesTax;
import com.khoders.invoicemaster.entities.UserTransaction;
import com.khoders.invoicemaster.service.ClientService;
import com.khoders.invoicemaster.service.ProformaInvoiceService;
import com.khoders.invoicemaster.service.TaxService;
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
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author richa
 */
@Named(value = "userTransactionController")
@SessionScoped
public class UserTransactionController implements Serializable
{
    @Inject private CrudApi crudApi;
    @Inject private ClientService clientService;
    @Inject private ProformaInvoiceService proformaInvoiceService;
    
    private UserTransaction userTransaction = new UserTransaction();
    private OnlineClient onlineClient = new OnlineClient();
    private OnlineClient selectedOnlineClient = null;
    private List<UserTransaction> userTransactionList = new LinkedList<>();
    private List<OnlineClient> onlineClientList = new LinkedList<>();
    private List<SaleItem> saleItemList = new LinkedList<>();
    private List<SalesTax> salesTaxList = new LinkedList<>();
    
    private FormView pageView = FormView.listForm();
    private DateRangeUtil dateRange = new DateRangeUtil();
    private double totalPayable,installationFee,totalSaleAmount,productDiscountRate,subTotal;
    
    private String optionText;
    
    @PostConstruct
    private void init()
    {
       clearOnlineClient();
       onlineClientList = clientService.getOnlineClientList();
    }
    
    public void saveUserTransaction(){
        try
        {
            System.out.println("Data-- "+userTransaction.getPaymentMethod());
            if(crudApi.save(userTransaction) != null)
            {
                userTransactionList = CollectionList.washList(userTransactionList, userTransaction);
                Msg.info(Msg.SUCCESS_MESSAGE);
            }
            else
            {
                Msg.error(Msg.FAILED_MESSAGE);
            }
            clearUserTransaction();
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    
    public void editUserTransaction(UserTransaction userTransaction){
        this.userTransaction=userTransaction;
        optionText = "Update";
    }
    
    public void deleteUserTransaction(UserTransaction userTransaction){
        try
        {
            if(crudApi.delete(userTransaction))
            {
                userTransactionList.remove(userTransaction);
                Msg.info(Msg.SUCCESS_MESSAGE);
            }
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    
    public void manageUserTransaction(OnlineClient onlineClient)
    {
        this.selectedOnlineClient = onlineClient;
        pageView.restToCreateView();
        clearUserTransaction();
        userTransactionList = clientService.userTransactionList(onlineClient);
    }
    
    public void manageSales(OnlineClient onlineClient)
    {
       this.selectedOnlineClient = onlineClient;
        pageView.restToDetailView();
        
        saleItemList = clientService.salesList(onlineClient);
        UserTransaction ut = crudApi.find(UserTransaction.class, onlineClient.getId());
        salesTaxList = proformaInvoiceService.getSalesTaxList(ut);
        
        totalSaleAmount = ut.getTotalAmount();
        subTotal = saleItemList.stream().mapToDouble(SaleItem::getSubTotal).sum();
        
        calculateVat(ut);
    }
    
    private void calculateVat(UserTransaction userTransaction)
    {
        
        if(!salesTaxList.isEmpty())
        {
            SalesTax nhil = salesTaxList.get(0);
//            SalesTax getFund = salesTaxList.get(1);
            SalesTax covid19 = salesTaxList.get(1);
            SalesTax salesVat = salesTaxList.get(2);

            double totalLevies = nhil.getTaxAmount()+covid19.getTaxAmount();

            double taxableValue = userTransaction.getTotalAmount() + totalLevies;
            
            double vat = taxableValue*(salesVat.getTaxRate()/100);
            
            totalPayable = vat + taxableValue + installationFee;
            
            salesVat.setTaxAmount(vat);

//            crudApi.save(salesVat);
        }
    }
    
    public void filterTransaction(){
        onlineClientList = clientService.filterTransaction(dateRange);
    }
    
    public void reset()
    {
      onlineClientList = new LinkedList<>();
      dateRange = new DateRangeUtil();
    }
    
    public void clearUserTransaction()
    {
       userTransaction = new UserTransaction();
       userTransaction.setOnlineClient(selectedOnlineClient);
       optionText = "Save Changes";
        SystemUtils.resetJsfUI();
    }
  
    public void clearOnlineClient()
    {
       selectedOnlineClient = new OnlineClient();
       onlineClientList = new LinkedList<>(); 
       optionText = "Save Changes";
       SystemUtils.resetJsfUI();
    }

    public void closePage()
    {
        clearUserTransaction();
        
        userTransactionList = new LinkedList<>(); 
        pageView.restToListView();
    }
    public void goBack()
    {
      manageUserTransaction(selectedOnlineClient);
    }
    public UserTransaction getUserTransaction()
    {
        return userTransaction;
    }

    public void setUserTransaction(UserTransaction userTransaction)
    {
        this.userTransaction = userTransaction;
    }

    public String getOptionText()
    {
        return optionText;
    }

    public void setOptionText(String optionText)
    {
        this.optionText = optionText;
    }

    public List<UserTransaction> getUserTransactionList()
    {
        return userTransactionList;
    }

    public List<OnlineClient> getOnlineClientList()
    {
        return onlineClientList;
    }

    public FormView getPageView()
    {
        return pageView;
    }

    public void setPageView(FormView pageView)
    {
        this.pageView = pageView;
    }

    public OnlineClient getSelectedOnlineClient()
    {
        return selectedOnlineClient;
    }

    public void setSelectedOnlineClient(OnlineClient selectedOnlineClient)
    {
        this.selectedOnlineClient = selectedOnlineClient;
    }

    public List<SaleItem> getSaleItemList()
    {
        return saleItemList;
    }

    public DateRangeUtil getDateRange()
    {
        return dateRange;
    }

    public void setDateRange(DateRangeUtil dateRange)
    {
        this.dateRange = dateRange;
    }

    public double getTotalPayable()
    {
        return totalPayable;
    }

    public void setTotalPayable(double totalPayable)
    {
        this.totalPayable = totalPayable;
    }

    public double getInstallationFee()
    {
        return installationFee;
    }

    public void setInstallationFee(double installationFee)
    {
        this.installationFee = installationFee;
    }

    public double getTotalSaleAmount()
    {
        return totalSaleAmount;
    }

    public void setTotalSaleAmount(double totalSaleAmount)
    {
        this.totalSaleAmount = totalSaleAmount;
    }

    public double getProductDiscountRate()
    {
        return productDiscountRate;
    }

    public void setProductDiscountRate(double productDiscountRate)
    {
        this.productDiscountRate = productDiscountRate;
    }

    public double getSubTotal()
    {
        return subTotal;
    }

    public void setSubTotal(double subTotal)
    {
        this.subTotal = subTotal;
    }

    public List<SalesTax> getSalesTaxList()
    {
        return salesTaxList;
    }
    
}
