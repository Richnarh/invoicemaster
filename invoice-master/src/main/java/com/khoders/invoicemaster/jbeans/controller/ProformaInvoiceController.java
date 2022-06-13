/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.khoders.invoicemaster.jbeans.controller;
import Zenoph.SMSLib.Enums.MSGTYPE;
import Zenoph.SMSLib.Enums.REQSTATUS;
import Zenoph.SMSLib.ZenophSMS;
import com.khoders.invoicemaster.entities.ProformaInvoice;
import com.khoders.invoicemaster.entities.ProformaInvoiceItem;
import com.khoders.invoicemaster.entities.SalesTax;
import com.khoders.invoicemaster.dto.ProformaInvoiceDto;
import com.khoders.invoicemaster.entities.Tax;
import com.khoders.invoicemaster.dto.Receipt;
import com.khoders.invoicemaster.entities.Inventory;
import com.khoders.invoicemaster.entities.PaymentData;
import com.khoders.invoicemaster.enums.InvoiceStatus;
import com.khoders.invoicemaster.enums.SMSType;
import com.khoders.invoicemaster.jbeans.ReportFiles;
import com.khoders.invoicemaster.listener.AppSession;
import com.khoders.invoicemaster.service.ProformaInvoiceService;
import com.khoders.invoicemaster.service.SmsService;
import com.khoders.invoicemaster.service.XtractService;
import com.khoders.invoicemaster.sms.SenderId;
import com.khoders.invoicemaster.sms.Sms;
import com.khoders.resource.enums.PaymentStatus;
import com.khoders.resource.jpa.CrudApi;
import com.khoders.resource.utilities.CollectionList;
import com.khoders.resource.utilities.DateRangeUtil;
import com.khoders.resource.utilities.FormView;
import com.khoders.resource.utilities.Msg;
import com.khoders.resource.utilities.SystemUtils;
import java.io.Serializable;
import com.khoders.resource.reports.ReportManager;
import java.time.LocalDateTime;
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
 * @author pascal
 */
@Named(value = "proformaInvoiceController")
@SessionScoped
public class ProformaInvoiceController implements Serializable
{
    @Inject private CrudApi crudApi;
    @Inject private AppSession appSession;
    @Inject private ProformaInvoiceService proformaInvoiceService;
    @Inject private XtractService xtractService;
    @Inject private ReportManager reportManager;

    private FormView pageView = FormView.listForm();
    private DateRangeUtil dateRange = new DateRangeUtil();

    private ProformaInvoice proformaInvoice = new ProformaInvoice();
    private ProformaInvoice stdProformaInvoice = new ProformaInvoice();
    private List<ProformaInvoice> proformaInvoiceList = new LinkedList<>();
    private List<PaymentData> paymentDataList = new LinkedList<>();

    private PaymentData paymentData = new PaymentData();
    private ProformaInvoiceItem proformaInvoiceItem = new ProformaInvoiceItem();
    private List<ProformaInvoiceItem> proformaInvoiceItemList = new LinkedList<>();
    private List<ProformaInvoiceItem> removedProformaInvoiceItemList = new LinkedList<>();

    private List<Tax> taxList = new LinkedList<>();
    private List<SalesTax> salesTaxList = new LinkedList<>();
    private SalesTax taxSales = new SalesTax();
    
    private int selectedTabIndex;
    private String optionText,paymentInvoiceNo,paymentClient;
    private double subTotal,totalSaleAmount,calculatedDiscount,installationFee,taxAmount,totalPayable,invoiceAmount,productDiscountRate;
    
    private InvoiceStatus invoiceStatus = null;
    
    Sms sms = new Sms();
    SenderId senderId=null;
    String phoneNumber=null;
        
    private boolean panelFlag=false;
     
    @PostConstruct
    public void init()
    {
        proformaInvoiceList = proformaInvoiceService.getProformaInvoiceList();
        taxList = proformaInvoiceService.getTaxList();
        clearProformaInvoice();
    }

    public void inventoryProperties()
    {
        if(proformaInvoiceItem.getInventory().getSellingPrice() != 0.0)
        {
          proformaInvoiceItem.setUnitPrice(proformaInvoiceItem.getInventory().getSellingPrice());
        }
    }

    public void initProformaInvoice()
    {
        clearProformaInvoice();
        pageView.restToCreateView();
    }
    
    public void fetchByInvoiceStatus()
    {
      if(invoiceStatus != null)
      {
        proformaInvoiceList = proformaInvoiceService.getProformaInvoice(invoiceStatus); 
      }
    }
      
    public void filterProformaInvoice()
    {
      proformaInvoiceList = proformaInvoiceService.getProformaInvoice(dateRange, proformaInvoice);  
    }
    
    public void reset()
    {
      proformaInvoiceList = new LinkedList<>();
      dateRange = new DateRangeUtil();
      invoiceStatus = null;
    }
    
    public void editProformaInvoiceItem(ProformaInvoiceItem proformaInvoiceItem)
    {
        this.proformaInvoiceItem = proformaInvoiceItem;
        proformaInvoiceItemList.remove(proformaInvoiceItem);
//        totalSaleAmount -= (proformaInvoiceItem.getQuantity() * proformaInvoiceItem.getUnitPrice());
        optionText = "Update";
    }
    
    public void deleteProformaInvoiceItem(ProformaInvoiceItem proformaInvoiceItem)
    {
//        totalSaleAmount -= (proformaInvoiceItem.getQuantity() * proformaInvoiceItem.getUnitPrice());
        removedProformaInvoiceItemList = CollectionList.washList(removedProformaInvoiceItemList, proformaInvoiceItem);
        proformaInvoiceItemList.remove(proformaInvoiceItem);
    }
    
    public void editProformaInvoice(ProformaInvoice proformaInvoice)
    {
        this.proformaInvoice = proformaInvoice;
        pageView.restToCreateView();
        optionText = "Update";
    }

    public void saveProformaInvoice()
    {
        try
        {
            if(appSession.getCurrentUser() != null){
                proformaInvoice.setLastModifiedBy(appSession.getCurrentUser().getFullname());
            }
            if (crudApi.save(proformaInvoice) != null)
            {
                proformaInvoiceList = CollectionList.washList(proformaInvoiceList, proformaInvoice);
                
                Msg.info(Msg.SUCCESS_MESSAGE);
                closePage();
            } else

            {
                Msg.error(Msg.FAILED_MESSAGE);
            }
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    
    public void recordPayment(ProformaInvoice proformaInvoice)
    {
        clearPaymentData();
        
        paymentDataList = proformaInvoiceService.getPaymentInfoList(proformaInvoice);
        
        paymentClient = proformaInvoice.getClient().getClientName();
        paymentInvoiceNo = proformaInvoice.getQuotationNumber();
        invoiceAmount = proformaInvoice.getTotalAmount();
        
        paymentData.setCompanyBranch(appSession.getCompanyBranch());
        paymentData.setUserAccount(appSession.getCurrentUser());
        paymentData.setProformaInvoice(proformaInvoice);
        
       PaymentData data = proformaInvoiceService.invoiceRecord(paymentData.getProformaInvoice());
       if(data != null)
       {
          paymentData = data; 
       }
        
    }
    
    public void savePaymentData()
    {
        try 
        {
          paymentData.genCode();
          if(paymentData.getProformaInvoice() == null)
          {
              Msg.error("Please select the invoice again!");
              return;
          }
            PaymentData data = proformaInvoiceService.invoiceRecord(paymentData.getProformaInvoice());
            System.out.println("data -- "+data);
            if (data != null)
            {
                Msg.error("Payment data already captured!");
                return;
            }
             
            if(crudApi.save(paymentData) != null)
            {
              ProformaInvoice invoice = crudApi.find(ProformaInvoice.class, paymentData.getProformaInvoice().getId());
              invoice.setConverted(true);
              crudApi.save(invoice);
             
              if(paymentData.getPaymentStatus() == PaymentStatus.FULLY_PAID)
              {
                 List<Inventory> inventoryList = proformaInvoiceService.getInventoryList();
                 List<ProformaInvoiceItem> purchasedList = proformaInvoiceService.getProformaInvoiceItemList(paymentData.getProformaInvoice());
                  for (ProformaInvoiceItem item : purchasedList)
                  {
                      for (Inventory inventory : inventoryList)
                      {
                          if(item.getInventory().getId().equals(inventory.getId()))
                          {
                            int qtyPurchased = item.getQuantity();
                            int qtyInStock = inventory.getQuantity();

                            int qtyAtHand = qtyInStock - qtyPurchased;
                            
                              System.out.println("qtyPurchased -- "+qtyPurchased);
                              System.out.println("qtyInStock -- "+qtyInStock);
                              System.out.println("qtyAtHand -- "+qtyAtHand);
                            
                            Inventory stock = crudApi.find(Inventory.class, inventory.getId());
                            stock.setQuantity(qtyAtHand);
                            crudApi.save(stock);
                          }
                      }
                  }
              }
              System.out.println("Not fully paid");
              
              paymentDataList = CollectionList.washList(paymentDataList, paymentData);
              
              processPaymentSms(paymentData);
              
              Msg.info(Msg.SUCCESS_MESSAGE);
          }
          else
          {
              FacesContext.getCurrentInstance().addMessage(null, 
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, Msg.FAILED_MESSAGE, null));
          }
           clearPaymentData();
        } catch (Exception e) 
        {
            e.printStackTrace();
        }
    }
    
    public void processPaymentSms(PaymentData paymentData)
    {
        senderId = crudApi.getEm().createQuery("SELECT e FROM SenderId e", SenderId.class).getResultStream().findFirst().orElse(null);
        System.out.println("Sender ID => "+senderId.getSenderIdentity());
        try 
        {
          ZenophSMS zsms = SmsService.extractParams();
          zsms.setMessage("Thanks for visiting Dolphin Doors, we're happy to see you. We'll be looking forward to seeing you again!. \n"
                 + "Contact us: \n "
                 + "Website: https://dolphindoors.com/ \n"
                 + "Tel: +233 302 986 345/+233 302 252 027 \n"
                 + "Email: info@dolphindoors.com");
          
          if(paymentData.getProformaInvoice() != null){
              if(paymentData.getProformaInvoice().getClient() != null)
              {
                 phoneNumber = paymentData.getProformaInvoice().getClient().getPhone();
              }
            }
          
            List<String> numbers = zsms.extractPhoneNumbers(phoneNumber);

            for (String number : numbers)
            {
                zsms.addRecipient(number);
            }
            
            zsms.setSenderId(senderId.getSenderIdentity());
            zsms.setMessageType(MSGTYPE.TEXT);

            List<String[]> response = zsms.submit();
            for (String[] destination : response)
            {
                    REQSTATUS reqstatus = REQSTATUS.fromInt(Integer.parseInt(destination[0]));
                    if (reqstatus == null)
                    {
                        FacesContext.getCurrentInstance().addMessage(null,
                                new FacesMessage(FacesMessage.SEVERITY_ERROR, Msg.setMsg("failed to send message"), null));
                        break;
                    } else
                    {
                        switch (reqstatus)
                        {
                            case SUCCESS:
                                saveMessage();
                                break;
                            case ERR_INSUFF_CREDIT:
                                FacesContext.getCurrentInstance().addMessage(null,
                                        new FacesMessage(FacesMessage.SEVERITY_ERROR, Msg.setMsg("Insufficeint Credit"), null));
                            default:
                                FacesContext.getCurrentInstance().addMessage(null,
                                        new FacesMessage(FacesMessage.SEVERITY_ERROR, Msg.setMsg("Failed to send message"), null));
                                return;
                        }
                    }
                }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void saveMessage()
    {
        try
        {
            sms.genCode();
            sms.setSmsTime(LocalDateTime.now());
            sms.setMessage("Thanks for visiting Dolphin Doors, we're happy to see you. We'll be looking forward to seeing you again!.");
            sms.setClient(paymentData.getProformaInvoice().getClient());
            sms.setsMSType(SMSType.SYSTEM_SMS);
            sms.setCompanyBranch(appSession.getCompanyBranch());
            sms.setUserAccount(appSession.getCurrentUser());
            sms.setSenderId(senderId);
           if(crudApi.save(sms) != null)
           {
            Msg.info("SMS sent to "+paymentData.getProformaInvoice().getClient());
            System.out.println("SMS sent and saved -- ");
           }
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }
      
    
    public void clearPaymentData() {
        paymentData = new PaymentData();
        paymentData.setUserAccount(appSession.getCurrentUser());
        paymentData.setCompanyBranch(appSession.getCompanyBranch());
        optionText = "Save Changes";
        SystemUtils.resetJsfUI();
    }

    public void manageProformaInvoiceItem(ProformaInvoice proformaInvoice)
    {
        totalPayable=0.0;
        
        this.proformaInvoice = proformaInvoice;
        pageView.restToDetailView();
        
        clearProformaInvoiceItem();
        
        installationFee = proformaInvoice.getInstallationFee();
        productDiscountRate = proformaInvoice.getDiscountRate();
        proformaInvoiceItemList = proformaInvoiceService.getProformaInvoiceItemList(proformaInvoice);
        salesTaxList = proformaInvoiceService.getSalesTaxList(proformaInvoice);
        
        totalSaleAmount = proformaInvoice.getTotalAmount();
        subTotal = proformaInvoice.getSubTotalAmount();

        calculateVat();
    }

    public void addProformaInvoiceItem()
    {
        try
        {
            if (proformaInvoiceItem.getQuantity() <= 0)
            {
               Msg.error("Please enter quantity");
               return;
            }
            
            if(proformaInvoiceItem.getUnitPrice() <= 0.0)
            {
             Msg.error("Please enter price");
              return;
            }
            
             if(proformaInvoiceItem != null)
              {
                double salesAmount = proformaInvoiceItem.getQuantity() * proformaInvoiceItem.getUnitPrice();
                
                proformaInvoiceItem.genCode();
                proformaInvoiceItem.setSubTotal(salesAmount);
                proformaInvoiceItemList.add(proformaInvoiceItem);
                proformaInvoiceItemList = CollectionList.washList(proformaInvoiceItemList, proformaInvoiceItem);
                
                Msg.info("One item added to cart");
              }
              else
                {
                  Msg.error("Proforma Invoice item removed!");
                }
            clearProformaInvoiceItem();
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public void taxCalculation()
    {
      
        // delete all salesTax for the selected proforma invoice
        salesTaxList.forEach(salesTax ->
        {
            crudApi.delete(salesTax);
        });
      
        for (Tax tax : taxList)
        {
            SalesTax salesTax = new SalesTax();

            double calc = proformaInvoice.getTotalAmount() * (tax.getTaxRate()/100);

            salesTax.genCode();
            salesTax.setTaxName(tax.getTaxName());
            salesTax.setTaxRate(tax.getTaxRate());
            salesTax.setTaxAmount(calc);
            salesTax.setReOrder(tax.getReOrder());
            salesTax.setUserAccount(appSession.getCurrentUser());
            salesTax.setCompanyBranch(appSession.getCompanyBranch());
            salesTax.setProformaInvoice(proformaInvoice);

            crudApi.save(salesTax);
            
//                else
//                {
//                    for (SalesTax tx : salesTaxList)
//                    {
//                        if (tx.getProformaInvoice().equals(proformaInvoice))
//                        {
//                            crudApi.save(tx);
//                        }
//                    }
//                }
        }
            
        salesTaxList = proformaInvoiceService.getSalesTaxList(proformaInvoice);
        
        calculateVat();
    }
    
    private void calculateVat()
    {
        if(!salesTaxList.isEmpty())
        {
            SalesTax nhil = salesTaxList.get(0);
//            SalesTax getFund = salesTaxList.get(1);
            SalesTax covid19 = salesTaxList.get(1);
            SalesTax salesVat = salesTaxList.get(2);

            double totalLevies = nhil.getTaxAmount()+covid19.getTaxAmount();

            double taxableValue = proformaInvoice.getTotalAmount() + totalLevies;
            
//            System.out.println("saleAmount => "+proformaInvoice.getTotalAmount());
//            System.out.println("taxableValue => "+taxableValue);
//            System.out.println("totalLevies => "+totalLevies);
//            
            double vat = taxableValue*(salesVat.getTaxRate()/100);
            
//            System.out.println("vat => "+vat);

            totalPayable = vat + taxableValue + installationFee;
            
            salesVat.setTaxAmount(vat);

            crudApi.save(salesVat);
            
        }
    }

    public void saveAll()
    {
        if(proformaInvoiceItemList.isEmpty()){
            Msg.error("Cannot process empty transaction");
            return;
        }
        totalSaleAmount = proformaInvoiceItemList.stream().mapToDouble(ProformaInvoiceItem::getSubTotal).sum();
        proformaInvoice = crudApi.find(ProformaInvoice.class, proformaInvoice.getId());
        try 
        {
                for (ProformaInvoiceItem pInvoiceItem : proformaInvoiceItemList) 
                {
                  crudApi.save(pInvoiceItem); 
                }
                
                for (ProformaInvoiceItem invoiceItem : removedProformaInvoiceItemList)
                {
                    crudApi.delete(invoiceItem);
                    removedProformaInvoiceItemList.remove(invoiceItem);
                }
                
                
                if (productDiscountRate > 0.0)
                {
                    if(productDiscountRate > 5.0)
                    {
                        Msg.error("Please dicount above 5% is not allowed!");
                        return;
                    }
                    calculatedDiscount = totalSaleAmount * (productDiscountRate/100); // Calculating Discount on total Amount
                    double newTotalAmount = totalSaleAmount - calculatedDiscount;
                    
                    proformaInvoice.setTotalAmount(newTotalAmount);
                    
                   proformaInvoice.setSubTotalAmount(totalSaleAmount);
                    setTotalSaleAmount(newTotalAmount); // updating the sales amount with the new totalAmount
                    setSubTotal(totalSaleAmount);
                }
                else
                {
                   setSubTotal(totalSaleAmount);
                   proformaInvoice.setTotalAmount(totalSaleAmount);
                   proformaInvoice.setSubTotalAmount(totalSaleAmount);
                }
                
                proformaInvoice.setInstallationFee(installationFee);
                proformaInvoice.setDiscountRate(productDiscountRate);
                
                if(crudApi.save(proformaInvoice) != null)
                {
                   taxCalculation();
                   
//                   FacesContext.getCurrentInstance().addMessage(null, 
//                        new FacesMessage(FacesMessage.SEVERITY_INFO, Msg.setMsg("Proforma Invoice item list saved!"), null));
                   
                   Msg.info("Proforma Invoice item list saved!");
                }
                else
                {
                 Msg.error("The invoice processing wasn't successful!");
                }
        } catch (Exception e) 
        {
            e.printStackTrace();
        }
    }

    public void generateReceipt(ProformaInvoice proformaInvoice)
    {
        List<Receipt> receiptList = new LinkedList<>();

        Receipt receipt = xtractService.extractToReceipt(proformaInvoice);

        receiptList.add(receipt);
        ReportManager.reportParams.put("logo", ReportFiles.LOGO);
        reportManager.createReport(receiptList, ReportFiles.RECEIPT_FILE, ReportManager.reportParams);
    }
    
 
    public void generateProformaInvoice(ProformaInvoice proformaInvoice)
    {
        List<ProformaInvoiceDto> proformaInvoiceDtoList = new LinkedList<>();
        
        ProformaInvoiceDto proformaInvoiceDto = xtractService.extractToProformaInvoice(proformaInvoice);
            
        proformaInvoiceDtoList.add(proformaInvoiceDto);
        ReportManager.reportParams.put("logo", ReportFiles.LOGO);
        reportManager.createReport(proformaInvoiceDtoList, ReportFiles.PRO_INVOICE_FILE, ReportManager.reportParams);
    }
    
    public void printCover(ProformaInvoice proformaInvoice)
    {
        List<ProformaInvoiceDto> proformaInvoiceDtoList = new LinkedList<>();
        
        ProformaInvoiceDto proformaInvoiceDto = xtractService.extractToProformaInvoiceCover(proformaInvoice);
        
        proformaInvoiceDtoList.add(proformaInvoiceDto);
        ReportManager.reportParams.put("logo", ReportFiles.LOGO);
        reportManager.createReport(proformaInvoiceDtoList, ReportFiles.PRO_INVOICE_COVER, ReportManager.reportParams);
    }
    
    public void closePage()
    {
        init();
       proformaInvoice = new ProformaInvoice();
       totalSaleAmount = 0;
       optionText = "Save Changes";
       pageView.restToListView();
    }
    
    public void clearProformaInvoiceItem()
    {
        proformaInvoiceItem = new ProformaInvoiceItem();
        proformaInvoiceItem.setProformaInvoice(proformaInvoice);
        proformaInvoiceItem.setUserAccount(appSession.getCurrentUser());
        proformaInvoiceItem.setCompanyBranch(appSession.getCompanyBranch());
        optionText = "Save Changes";
        SystemUtils.resetJsfUI();
    }
    
    public void clearProformaInvoice()
    {
        proformaInvoice = new ProformaInvoice();
        proformaInvoice.setUserAccount(appSession.getCurrentUser());
        proformaInvoice.setCompanyBranch(appSession.getCompanyBranch());
        proformaInvoice.setLastModifiedDate(LocalDateTime.now());
        optionText = "Save Changes";
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
    
    public double getTotalSaleAmount()
    {
        return totalSaleAmount;
    }

    public void setTotalSaleAmount(double totalSaleAmount)
    {
        this.totalSaleAmount = totalSaleAmount;
    }

    public List<ProformaInvoiceItem> getProformaInvoiceItemList()
    {
        return proformaInvoiceItemList;
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

    public double getCalculatedDiscount()
    {
        return calculatedDiscount;
    }
    
    public double getTaxAmount()
    {
        return taxAmount;
    }

    public void setTaxAmount(double taxAmount)
    {
        this.taxAmount = taxAmount;
    }

    public List<SalesTax> getSalesTaxList()
    {
        return salesTaxList;
    }

    public double getTotalPayable()
    {
        return totalPayable;
    }

    public List<Tax> getTaxList()
    {
        return taxList;
    }

    public double getInstallationFee()
    {
        return installationFee;
    }

    public void setInstallationFee(double installationFee)
    {
        this.installationFee = installationFee;
    }

    public SalesTax getTaxSales()
    {
        return taxSales;
    }

    public void setTaxSales(SalesTax taxSales)
    {
        this.taxSales = taxSales;
    }

    public PaymentData getPaymentData()
    {
        return paymentData;
    }

    public void setPaymentData(PaymentData paymentData)
    {
        this.paymentData = paymentData;
    }

    public List<PaymentData> getPaymentDataList()
    {
        return paymentDataList;
    }

    public String getPaymentInvoiceNo()
    {
        return paymentInvoiceNo;
    }

    public String getPaymentClient()
    {
        return paymentClient;
    }

    public double getInvoiceAmount() {
        return invoiceAmount;
    }

    public boolean isPanelFlag() {
        return panelFlag;
    }

    public void setPanelFlag(boolean panelFlag) {
        this.panelFlag = panelFlag;
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

    public InvoiceStatus getInvoiceStatus()
    {
        return invoiceStatus;
    }

    public void setInvoiceStatus(InvoiceStatus invoiceStatus)
    {
        this.invoiceStatus = invoiceStatus;
    }
    
}
