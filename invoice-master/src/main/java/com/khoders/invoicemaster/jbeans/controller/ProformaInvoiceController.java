/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.khoders.invoicemaster.jbeans.controller;

import Zenoph.SMSLib.Enums.MSGTYPE;
import Zenoph.SMSLib.Enums.REQSTATUS;
import static Zenoph.SMSLib.Enums.REQSTATUS.ERR_INSUFF_CREDIT;
import static Zenoph.SMSLib.Enums.REQSTATUS.SUCCESS;
import Zenoph.SMSLib.ZenophSMS;
import com.khoders.invoicemaster.DefaultService;
import com.khoders.invoicemaster.entities.ProformaInvoice;
import com.khoders.invoicemaster.entities.ProformaInvoiceItem;
import com.khoders.invoicemaster.entities.SalesTax;
import com.khoders.invoicemaster.reportData.ProformaInvoiceDto;
import com.khoders.invoicemaster.entities.Tax;
import com.khoders.invoicemaster.reportData.Receipt;
import com.khoders.invoicemaster.entities.AppConfig;
import com.khoders.invoicemaster.entities.DiscountAction;
import com.khoders.invoicemaster.entities.Inventory;
import com.khoders.invoicemaster.entities.PaymentData;
import com.khoders.invoicemaster.enums.ActionType;
import com.khoders.invoicemaster.enums.AppVersion;
import com.khoders.invoicemaster.enums.InvoiceStatus;
import com.khoders.invoicemaster.enums.SMSType;
import com.khoders.invoicemaster.jbeans.ReportFiles;
import com.khoders.invoicemaster.listener.AppSession;
import com.khoders.invoicemaster.service.ProformaInvoiceService;
import com.khoders.invoicemaster.service.SmsService;
import com.khoders.invoicemaster.service.XtractService;
import com.khoders.invoicemaster.sms.Sms;
import com.khoders.resource.enums.PaymentStatus;
import com.khoders.resource.jpa.CrudApi;
import com.khoders.resource.reports.ReportManager;
import com.khoders.resource.utilities.CollectionList;
import com.khoders.resource.utilities.DateRangeUtil;
import com.khoders.resource.utilities.FormView;
import com.khoders.resource.utilities.Msg;
import com.khoders.resource.utilities.SystemUtils;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import net.sf.jasperreports.engine.JasperPrint;

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
    @Inject private ReportHandler reportHandler;
    @Inject private XtractService xtractService;
    @Inject private SmsService smsService;
    @Inject private ReportManager reportManager;
    @Inject private DefaultService ds;

    private FormView pageView = FormView.listForm();
    private DateRangeUtil dateRange = new DateRangeUtil();
    private boolean fullyPaid = false;

    private ProformaInvoice proformaInvoice = new ProformaInvoice();
    private ProformaInvoice stdProformaInvoice = new ProformaInvoice();
    private List<ProformaInvoice> proformaInvoiceList = new LinkedList<>();
    private List<PaymentData> paymentDataList = new LinkedList<>();

    private PaymentData paymentData = new PaymentData();
    private SalesTax salesTax = new SalesTax();
    private ProformaInvoiceItem proformaInvoiceItem = new ProformaInvoiceItem();
    private List<ProformaInvoiceItem> proformaInvoiceItemList = new LinkedList<>();
    private List<ProformaInvoiceItem> removedProformaInvoiceItemList = new LinkedList<>();

    private List<Tax> taxList = new LinkedList<>();
    private List<SalesTax> salesTaxList = new LinkedList<>();
    private SalesTax taxSales = new SalesTax();
    private ActionType actionType = null;
    
    private int selectedTabIndex;
    private String optionText,paymentInvoiceNo,paymentClient,pageLimit;
    private double subTotal,totalSaleAmount,calculatedDiscount,installationFee,taxAmount,totalPayable,invoiceAmount,productDiscountRate;
    
    private InvoiceStatus invoiceStatus = null;
    
    Sms sms = new Sms();
    String phoneNumber=null;
        
    private boolean panelFlag=false;
     
    @PostConstruct
    public void init()
    {
        proformaInvoiceList = proformaInvoiceService.getProformaInvoiceList();
        taxList = proformaInvoiceService.getTaxList();
        pageLimit = ds.getConfigValue("invoice.page.limit");
        clearProformaInvoice();
    }
    
    public void savePageLimit(){
        AppConfig config = ds.getAppConfig("invoice.page.limit");
        config.setConfigValue(pageLimit);
        crudApi.save(config);
        Msg.info("Page limit updated!");
    }

    public void inventoryProperties(){
        System.out.println("Over here----");
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
      proformaInvoiceList = proformaInvoiceService.getProformaInvoice(dateRange);  
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
       if(data != null){
           fullyPaid = data.getPaymentStatus() == PaymentStatus.FULLY_PAID;
           paymentData = data; 
       }else{
           fullyPaid = false;
       }
    }
    
    public void savePaymentData(){
        try{
          paymentData.genCode();
          if(paymentData.getProformaInvoice() == null){
              Msg.error("Please select the invoice again!");
              return;
          }
            PaymentData data = proformaInvoiceService.invoiceRecord(paymentData.getProformaInvoice());

            if (data != null)
            {
                Msg.error("Payment data already captured!");
                return;
            }
             
            if(crudApi.save(paymentData) != null){
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
            Msg.error(Msg.FAILED_MESSAGE);
          }
           clearPaymentData();
        } catch (Exception e) 
        {
            e.printStackTrace();
        }
    }
    
    public void processPaymentSms(PaymentData paymentData)
    {
        try 
        {
          ZenophSMS zsms = smsService.extractParams();
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
            
            zsms.setSenderId(ds.getSenderId() != null ? ds.getSenderId() : ds.getConfigValue("sms.sender.id"));
            zsms.setMessageType(MSGTYPE.TEXT);

            List<String[]> response = zsms.submit();
            for (String[] destination : response)
            {
                    REQSTATUS reqstatus = REQSTATUS.fromInt(Integer.parseInt(destination[0]));
                    if (reqstatus == null)
                    {
                      Msg.error("failed to send message");
                        break;
                    } else
                    {
                        switch (reqstatus)
                        {
                            case SUCCESS:
                                saveMessage();
                                break;
                            case ERR_INSUFF_CREDIT:
                                Msg.error("Insufficeint Credit");
                            default:
                                Msg.error("Failed to send message");
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
           if(crudApi.save(sms) != null)
           {
               FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, Msg.setMsg("SMS sent to "+paymentData.getProformaInvoice().getClient()), null));
               
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
        actionType = null;
        SystemUtils.resetJsfUI();
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
       
        if(proformaInvoiceItemList.size() > 0){
            DiscountAction action = proformaInvoiceService.getDiscountAction();
            if(action.getActionType().equals(ActionType.DISABLE_ON_EDIT))
                actionType = ActionType.DISABLE_ON_EDIT;
            else
                actionType = ActionType.ENABLE_ON_EDIT;
            
            System.out.println("actionType: "+actionType);
        }
        
        System.out.println("actionType: "+actionType);
        calculateVat();
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
                 Msg.info("Proforma Invoice item removed!");
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
        salesTaxList.forEach(tx ->
        {
            crudApi.delete(tx);
        });
      
        for (Tax tax : taxList)
        {
            // v2 exclude the NHIL tax in sales calculation
            if(appSession.getCurrentUser().getAppVersion().equals(AppVersion.V2)){
                if(tax.getTaxName().equals("NHIL")) continue;
            }
            SalesTax st = new SalesTax();

            double calc = proformaInvoice.getTotalAmount() * (tax.getTaxRate()/100);

            st.genCode();
            st.setTaxName(tax.getTaxName());
            st.setTaxRate(tax.getTaxRate());
            st.setTaxAmount(calc);
            st.setReOrder(tax.getReOrder());
            st.setUserAccount(appSession.getCurrentUser());
            st.setCompanyBranch(appSession.getCompanyBranch());
            st.setProformaInvoice(proformaInvoice);
            st.setSaleLead(salesTax.getSaleLead());
            crudApi.save(st);
        }
            
        salesTaxList = proformaInvoiceService.getSalesTaxList(proformaInvoice);
        
        calculateVat();
    }
    
    private void calculateVat()
    {
        if(!salesTaxList.isEmpty())
        {
//            SalesTax nhil = salesTaxList.get(0);
//            SalesTax getFund = salesTaxList.get(1);
            SalesTax covid19 = salesTaxList.get(0);
            SalesTax salesVat = salesTaxList.get(1);

//          double totalLevies = nhil.getTaxAmount()+covid19.getTaxAmount();
            double totalLevies = covid19.getTaxAmount();

            double taxableValue = proformaInvoice.getTotalAmount() + totalLevies;
            System.out.println("Covide19: "+covid19.getTaxName() +"\t taxAmnt: "+covid19.getTaxAmount());
            System.out.println("salesVat: "+salesVat.getTaxName() +"\t taxAmnt: "+salesVat.getTaxAmount());
            System.out.println("saleAmount => "+proformaInvoice.getTotalAmount());
            System.out.println("totalLevies => "+totalLevies);
            System.out.println("taxableValue => "+taxableValue);
            
            double vat = taxableValue*(salesVat.getTaxRate()/100);
            
            totalPayable = vat + taxableValue + installationFee;
            
            salesVat.setTaxAmount(vat);

            crudApi.save(salesVat);
            
        }
    }

    public void saveAll()
    {
        paymentData = proformaInvoiceService.invoiceRecord(proformaInvoice);
        if(paymentData != null){
            if(paymentData.getPaymentStatus() == PaymentStatus.FULLY_PAID){
                Msg.error("This invoice cannot be saved again!");
                return;
            }
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
                double totalDiscountRate;
                
                if(salesTax.getSaleLead() != null)
                    totalDiscountRate = productDiscountRate + salesTax.getSaleLead().getRate();
                else
                    totalDiscountRate = productDiscountRate;
                
                System.out.println("totalDiscountRate: "+totalDiscountRate);
                if (totalDiscountRate > 0.0)
                {
                    calculatedDiscount = totalSaleAmount * (totalDiscountRate/100); // Calculating Discount on total Amount
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
        reportHandler.reportParams.put("logo", ReportFiles.LOGO);
        reportManager.createReport(receiptList, ReportFiles.RECEIPT_FILE, reportHandler.reportParams);
    }
    
 
    public void generateProformaInvoice(ProformaInvoice proformaInvoice){
        List<JasperPrint> jasperPrintList = new LinkedList<>();
        List<ProformaInvoiceDto> proformaInvoiceDtoList = new LinkedList<>();
        List<ProformaInvoiceDto> coverDataList = new LinkedList<>();
        
        reportHandler.reportParams.put("logo", ReportFiles.LOGO);
        
        ProformaInvoiceDto coverData = xtractService.extractToProformaInvoiceCover(proformaInvoice);
        coverDataList.add(coverData);
        JasperPrint coverPrint = reportManager.createJasperPrint(coverDataList, ReportFiles.PRO_INVOICE_COVER, reportHandler.reportParams);
        jasperPrintList.add(coverPrint);
        
        ProformaInvoiceDto proformaInvoiceDto = xtractService.extractToProformaInvoice(proformaInvoice);
        proformaInvoiceDtoList.add(proformaInvoiceDto);
        JasperPrint invoicePrint = reportManager.createJasperPrint(proformaInvoiceDtoList, ReportFiles.PRO_INVOICE_FILE, reportHandler.reportParams);
        jasperPrintList.add(invoicePrint);
        
        reportManager.generateReport(jasperPrintList, proformaInvoice.getQuotationNumber());
        
        System.out.println("Done!");
    }
        
    public void reverseApproval(ProformaInvoice proformaInvoice){
//        String url = "http://192.168.1.112:8080/invoice-master/secured/templates/reverse-sale.xhtml?id="+proformaInvoice.getQuotationNumber();
        String url = "http://185.218.125.78:8080/invoicemaster/secured/templates/reverse-sale.xhtml?id="+proformaInvoice.getQuotationNumber();
        StringBuilder sb = new StringBuilder();
        sb.append("Request from ");
        sb.append(appSession.getCurrentUser().getFullname()).append(" - ");
        sb.append(appSession.getCompanyBranch().getBranchName());
        sb.append(" to reverse Invoice No.: ");
        sb.append(proformaInvoice.getQuotationNumber());
        sb.append("\n").append("Click the link below to complete the reversal.").append("\n");
        sb.append(url);
        System.out.println("Msg: "+sb.toString());
        sendMsg(sb.toString());
         
        boolean sentMail = proformaInvoiceService.processMail(sb.toString(), appSession.getCurrentUser().getEmail());
        if(sentMail){
            Msg.info("Reversal email request sent, admin will notify you shortly!");
        }
    }
        
    public void sendMsg(String msg){
        String senderId = ds.getConfigValue("sms.sender.id");
        String adminNumber = ds.getConfigValue("admin.number");
        try 
        {
            ZenophSMS zsms = smsService.extractParams();
            zsms.setMessage(msg);
            zsms.addRecipient(adminNumber);
            zsms.setSenderId(senderId != null && senderId.isEmpty() || senderId == null ? ds.getSenderId() : senderId);
            zsms.setMessageType(MSGTYPE.TEXT);

            List<String[]> response = zsms.submit();
            for (String[] destination : response) {
                REQSTATUS reqstatus = REQSTATUS.fromInt(Integer.parseInt(destination[0]));
                if (reqstatus == null) {
                    Msg.error("failed to send message");
                    break;
                } else {
                    switch (reqstatus) {
                        case SUCCESS:
                            Msg.info("Reversal SMS request sent, admin will notify you shortly!");
                            break;
                        case ERR_INSUFF_CREDIT:
                            Msg.error("Insufficeint Credit");
                        default:
                            Msg.error("Failed to send message");
                            return;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
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

    public boolean isFullyPaid() {
        return fullyPaid;
    }

    public ActionType getActionType() {
        return actionType;
    }

    public SalesTax getSalesTax() {
        return salesTax;
    }

    public void setSalesTax(SalesTax salesTax) {
        this.salesTax = salesTax;
    }

    public String getPageLimit() {
        return pageLimit;
    }

    public void setPageLimit(String pageLimit) {
        this.pageLimit = pageLimit;
    }
    
}
