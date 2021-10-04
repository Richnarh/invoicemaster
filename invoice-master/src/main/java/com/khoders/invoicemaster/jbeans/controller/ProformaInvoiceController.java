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
import com.khoders.invoicemaster.entites.model.ProformaInvoiceDto;
import com.khoders.invoicemaster.entities.Tax;
import com.khoders.invoicemaster.entites.model.Receipt;
import com.khoders.invoicemaster.entities.PaymentData;
import com.khoders.invoicemaster.enums.SMSType;
import com.khoders.invoicemaster.jbeans.ReportFiles;
import com.khoders.invoicemaster.listener.AppSession;
import com.khoders.invoicemaster.service.ProformaInvoiceService;
import com.khoders.invoicemaster.service.SmsService;
import com.khoders.invoicemaster.sms.SenderId;
import com.khoders.invoicemaster.sms.Sms;
import com.khoders.resource.jpa.CrudApi;
import com.khoders.resource.utilities.CollectionList;
import com.khoders.resource.utilities.DateRangeUtil;
import com.khoders.resource.utilities.FormView;
import com.khoders.resource.utilities.Msg;
import com.khoders.resource.utilities.SystemUtils;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

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
    @Inject private ReportHandler coverHandler;

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
    private double totalSaleAmount,calculatedDiscount,installationFee,taxAmount,totalPayable,invoiceAmount,productDiscountRate;
    
    Sms sms = new Sms();
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
      
    public void filterProformaInvoice()
    {
      proformaInvoiceList = proformaInvoiceService.getProformaInvoice(dateRange, proformaInvoice);   
    }
    
    public void reset()
    {
      proformaInvoiceList = new LinkedList<>();
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
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_INFO, Msg.SUCCESS_MESSAGE, null));

                closePage();
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
    
    public void recordPayment(ProformaInvoice proformaInvoice)
    {
        paymentDataList = proformaInvoiceService.getPaymentInfoList(proformaInvoice);
        
        paymentClient = proformaInvoice.getClient().getClientName();
        paymentInvoiceNo = proformaInvoice.getQuotationNumber();
        invoiceAmount = proformaInvoice.getTotalAmount();
        
        paymentData.setCompanyBranch(appSession.getCompanyBranch());
        paymentData.setUserAccount(appSession.getCurrentUser());
        paymentData.setProformaInvoice(proformaInvoice);
        
    }
    
    public void savePaymentData()
    {
        try 
        {
          paymentData.genCode();
          if(crudApi.save(paymentData) != null)
          {
              paymentDataList = CollectionList.washList(paymentDataList, paymentData);
              
              processPaymentSms(paymentData);
              
              FacesContext.getCurrentInstance().addMessage(null, 
                        new FacesMessage(FacesMessage.SEVERITY_INFO, Msg.SUCCESS_MESSAGE, null)); 
              
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
        SenderId senderId = crudApi.getEm().createQuery("SELECT e FROM SenderId e", SenderId.class).getResultStream().findFirst().orElse(null);
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
        totalPayable=0.0;
        
        this.proformaInvoice = proformaInvoice;
        pageView.restToDetailView();
        
        clearProformaInvoiceItem();
        
        installationFee = proformaInvoice.getInstallationFee();
        productDiscountRate = proformaInvoice.getDiscountRate();
        proformaInvoiceItemList = proformaInvoiceService.getProformaInvoiceItemList(proformaInvoice);
        salesTaxList = proformaInvoiceService.getSalesTaxList(proformaInvoice);
        
        totalSaleAmount = proformaInvoice.getTotalAmount();

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
              FacesContext.getCurrentInstance().addMessage(null, 
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, Msg.setMsg("Please enter price"), null));
              return;
            }
            
             if(proformaInvoiceItem != null)
              {
                double salesAmount = proformaInvoiceItem.getQuantity() * proformaInvoiceItem.getUnitPrice();
                
                proformaInvoiceItem.genCode();
                proformaInvoiceItem.setSubTotal(salesAmount);
                proformaInvoiceItemList.add(proformaInvoiceItem);
                proformaInvoiceItemList = CollectionList.washList(proformaInvoiceItemList, proformaInvoiceItem);
                
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, Msg.setMsg("One item added to cart"), null));
              }
              else
                {
                   FacesContext.getCurrentInstance().addMessage(null, 
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, Msg.setMsg("Proforma Invoice item removed!"), null));
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
                    calculatedDiscount = totalSaleAmount * (productDiscountRate/100);
                    double newTotalAmount = totalSaleAmount - calculatedDiscount;
                    proformaInvoice.setTotalAmount(newTotalAmount);
                    
                    setTotalSaleAmount(newTotalAmount); // updating the sales amount with the new totalAmount
                    
                }
                else
                {
                   proformaInvoice.setTotalAmount(totalSaleAmount);
                }
                
                proformaInvoice.setInstallationFee(installationFee);
                proformaInvoice.setDiscountRate(productDiscountRate);
                
                if(crudApi.save(proformaInvoice) != null)
                {
                   taxCalculation();
                   
                   FacesContext.getCurrentInstance().addMessage(null, 
                        new FacesMessage(FacesMessage.SEVERITY_INFO, Msg.setMsg("Proforma Invoice item list saved!"), null));

                }
                else
                {
                   FacesContext.getCurrentInstance().addMessage(null, 
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, Msg.setMsg("The invoice processing wasn't successful!"), null)); 
                }
        } catch (Exception e) 
        {
            e.printStackTrace();
        }
    }

    public void generateReceipt(ProformaInvoice proformaInvoice)
    {
        List<Receipt> receiptList = new LinkedList<>();
        try
        {
            List<SalesTax> salesTaxesList  = proformaInvoiceService.getSalesTaxList(proformaInvoice);
            Receipt receipt = new Receipt();
        
            double totalTax = salesTaxesList.stream().mapToDouble(SalesTax::getTaxAmount).sum();
        
            double invoiceValue = totalTax + proformaInvoice.getTotalAmount() + proformaInvoice.getInstallationFee();
            
            if (appSession.getCurrentUser().getCompanyBranch() != null)
            {
                receipt.setBranchName(appSession.getCurrentUser().getCompanyBranch() + "");
            }
            if (appSession.getCurrentUser().getCompanyBranch() != null)
            {
                receipt.setWebsite(appSession.getCurrentUser().getCompanyBranch().getCompanyProfile().getWebsite());
            }
            
            receipt.setReceiptNumber(proformaInvoice.getQuotationNumber());
            receipt.setTotalTax(totalTax);
            receipt.setInstallationFee(proformaInvoice.getInstallationFee());
            receipt.setTotalAmount(proformaInvoice.getTotalAmount());
            receipt.setDate(LocalDateTime.now());
            receipt.setTotalPayable(invoiceValue);
            try
            {
                receipt.setModeOfPayment(proformaInvoice.getModeOfPayment().getLabel());
            } catch (Exception e)
            {
            }
            
            receiptList.add(receipt);
            
            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(receiptList);
        
            InputStream coverStream = getClass().getResourceAsStream(ReportFiles.RECEIPT_FILE);
            reportHandler.reportParams.put("logo", ReportFiles.LOGO);
            JasperPrint receiptPrint = JasperFillManager.fillReport(coverStream, coverHandler.reportParams, dataSource);
            HttpServletResponse servletResponse = (HttpServletResponse)FacesContext.getCurrentInstance().getExternalContext().getResponse();
            servletResponse.setContentType("application/pdf");
            ServletOutputStream servletStream = servletResponse.getOutputStream();
            JasperExportManager.exportReportToPdfStream(receiptPrint, servletStream);
            FacesContext.getCurrentInstance().responseComplete();
            
//            File newFile = new File(this.getClass().getResource("/com/khoders/invoicemaster/resources/receipt").getFile());
//
//                        
//            String pdfFile = newFile+File.separator+SystemUtils.generateCode()+"_receipt.pdf";
//            System.out.println("PDF File => "+pdfFile);
//            JasperExportManager.exportReportToPdfFile(receiptPrint, pdfFile);
//            FacesContext.getCurrentInstance().responseComplete();
//            
//            DocFlavor flavor = DocFlavor.SERVICE_FORMATTED.PAGEABLE;
//            PrintRequestAttributeSet patts = new HashPrintRequestAttributeSet();
////            patts.add(Sides.DUPLEX);
//            PrintService[] ps = PrintServiceLookup.lookupPrintServices(flavor, patts);
//            if (ps.length == 0)
//            {
//                throw new IllegalStateException("No Printer found");
//            }
//            System.out.println("Available printers: " + Arrays.asList(ps));
//
//            PrintService myService = null;
//            for (PrintService printService : ps)
//            {
//                System.out.println("Printers => "+printService.getName());
//                if (printService.getName().equalsIgnoreCase("EPSON TM-T20III Receipt"))
//                {
//                    myService = printService;
//                    break;
//                }
//            }
//
//            if (myService == null)
//            {
//                throw new IllegalStateException("Printer not found");
//            }
//
//            PDDocument document = PDDocument.load(new File(pdfFile));
//            
//            PrinterJob job = PrinterJob.getPrinterJob();
//            job.setPageable(new PDFPageable(document));
//            job.setPrintService(myService);
//            job.print();
//            
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    
 
    public void generateProformaInvoice(ProformaInvoice proformaInvoice)
    {
        List<ProformaInvoiceDto> proformaInvoiceDtoList = new LinkedList<>();
        
        List<ProformaInvoiceDto.ProformaInvoiceItem> invoiceItemDtoList = new LinkedList<>();
        List<ProformaInvoiceDto.SalesTax> salesTaxs = new LinkedList<>();
        
        List<ProformaInvoiceItem> invoiceItemList  = proformaInvoiceService.getProformaInvoiceItemReceipt(proformaInvoice);
        List<SalesTax> salesTaxesList  = proformaInvoiceService.getSalesTaxList(proformaInvoice);
        
            ProformaInvoiceDto proformaInvoiceDto = new ProformaInvoiceDto();
            
            if(proformaInvoice.getClient() != null)
            {
              proformaInvoiceDto.setClientName(proformaInvoice.getClient().getClientName().toUpperCase());
              proformaInvoiceDto.setEmailAddress(proformaInvoice.getClient().getEmailAddress());
              proformaInvoiceDto.setAddress(proformaInvoice.getClient().getAddress().toUpperCase());  
            }
            proformaInvoiceDto.setIssuedDate(proformaInvoice.getIssuedDate());
            proformaInvoiceDto.setExpiryDate(proformaInvoice.getExpiryDate());
            proformaInvoiceDto.setQuotationNumber(proformaInvoice.getQuotationNumber());
            proformaInvoiceDto.setDescription(proformaInvoice.getDescription());
            proformaInvoiceDto.setTotalAmount(proformaInvoice.getTotalAmount());
            
            double sTaxAmount = salesTaxesList.stream().mapToDouble(SalesTax::getTaxAmount).sum();
            
            double invoiceValue = sTaxAmount + proformaInvoice.getTotalAmount() + proformaInvoice.getInstallationFee();
            
            proformaInvoiceDto.setInstallationFee(proformaInvoice.getInstallationFee());
            proformaInvoiceDto.setTotalDiscount(proformaInvoice.getDiscountRate());
            proformaInvoiceDto.setTotalPayable(invoiceValue);
        
            
        if (appSession.getCurrentUser().getCompanyBranch() != null)
        {
            proformaInvoiceDto.setTelephoneNo(appSession.getCurrentUser().getCompanyBranch().getTelephoneNo());
        }
        if (appSession.getCurrentUser().getCompanyBranch() != null)
        {
            proformaInvoiceDto.setBranchName(appSession.getCurrentUser().getCompanyBranch() + "");
        }
        if (appSession.getCurrentUser().getCompanyBranch() != null)
        {
            proformaInvoiceDto.setGpsAddress(appSession.getCurrentUser().getCompanyBranch().getGpsAddress());
        }
        if (appSession.getCurrentUser().getCompanyBranch() != null)
        {
            proformaInvoiceDto.setWebsite(appSession.getCurrentUser().getCompanyBranch().getCompanyProfile().getWebsite());
        }
        if (appSession.getCurrentUser().getCompanyBranch() != null)
        {
            proformaInvoiceDto.setTinNo(appSession.getCurrentUser().getCompanyBranch().getCompanyProfile().getTinNo());
        }
                
        for (SalesTax salesTax : salesTaxesList)
        {
            ProformaInvoiceDto.SalesTax taxItem = new ProformaInvoiceDto.SalesTax();
            taxItem.setTaxName(salesTax.getTaxName());
            taxItem.setTaxRate(salesTax.getTaxRate());
            taxItem.setTaxAmount(salesTax.getTaxAmount());

            salesTaxs.add(taxItem);
        }
        
        for (ProformaInvoiceItem invoiceItem : invoiceItemList)
        {
            ProformaInvoiceDto.ProformaInvoiceItem invoiceItemDto = new ProformaInvoiceDto.ProformaInvoiceItem();
            
            if(invoiceItem.getInventory() != null)
            {
                if (invoiceItem.getInventory().getProduct() != null)
                {
                    try
                    {
                        byte[] image = invoiceItem.getInventory().getProduct().getProductImage();
                        if (image != null)
                        {
                            InputStream imageStream = new ByteArrayInputStream(image);
                            invoiceItemDto.setProductImage(imageStream);
                        }
                    } catch (Exception e)
                    {
                        e.printStackTrace();
                    }

                    invoiceItemDto.setProductCode(invoiceItem.getInventory().getProduct().getProductCode());
                    invoiceItemDto.setDescription(invoiceItem.getInventory().getProduct().getDescription());
                }
                invoiceItemDto.setFrameSize(invoiceItem.getInventory().getFrameSize());
                invoiceItemDto.setWidth(invoiceItem.getInventory().getWidth());
                invoiceItemDto.setHeight(invoiceItem.getInventory().getHeight());
            }
            invoiceItemDto.setQuantity(invoiceItem.getQuantity());
            invoiceItemDto.setUnitPrice(invoiceItem.getUnitPrice());
            invoiceItemDto.setTotalAmount(invoiceItem.getSubTotal());
            
            if(appSession.getCurrentUser().getFrame() != null)
            {
                invoiceItemDto.setFrameUnit(appSession.getCurrentUser().getFrame().getLabel());
            }
            
            if(appSession.getCurrentUser().getWidth() != null)
            {
                invoiceItemDto.setWidthHeightUnits(appSession.getCurrentUser().getWidth().getLabel());
            }
            
            invoiceItemDtoList.add(invoiceItemDto);
        }
            proformaInvoiceDto.setInvoiceItemList(invoiceItemDtoList);
            proformaInvoiceDto.setTaxList(salesTaxs);
            
            proformaInvoiceDtoList.add(proformaInvoiceDto);
            
        try
        {
            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(proformaInvoiceDtoList);
            InputStream stream = getClass().getResourceAsStream(ReportFiles.PRO_INVOICE_FILE);
            
            
            reportHandler.reportParams.put("logo", ReportFiles.LOGO);
            
            JasperPrint jasperPrint = JasperFillManager.fillReport(stream, reportHandler.reportParams, dataSource);
            HttpServletResponse httpServletResponse = (HttpServletResponse)FacesContext.getCurrentInstance().getExternalContext().getResponse();
            httpServletResponse.setContentType("application/pdf");
            ServletOutputStream outputStream = httpServletResponse.getOutputStream();
            JasperExportManager.exportReportToPdfStream(jasperPrint, outputStream);
            FacesContext.getCurrentInstance().responseComplete();
                        
        } catch (IOException | JRException e)
        {
            e.printStackTrace();
        }
    }
    
    public void printCover(ProformaInvoice proformaInvoice)
    {
        List<ProformaInvoiceDto> proformaInvoiceDtoList = new LinkedList<>();
        
            ProformaInvoiceDto proformaInvoiceDto = new ProformaInvoiceDto();
            proformaInvoiceDto.setClientName(proformaInvoice.getClient().getClientName());
            proformaInvoiceDto.setAddress(proformaInvoice.getClient().getAddress());
            proformaInvoiceDto.setIssuedDate(proformaInvoice.getIssuedDate());
            proformaInvoiceDto.setQuotationNumber(proformaInvoice.getQuotationNumber());
            
        if (appSession.getCurrentUser().getCompanyBranch() != null)
        {
            proformaInvoiceDto.setTelephoneNo(appSession.getCurrentUser().getCompanyBranch().getTelephoneNo());
        }
        if (appSession.getCurrentUser().getCompanyBranch() != null)
        {
            proformaInvoiceDto.setBranchName(appSession.getCurrentUser().getCompanyBranch() + "");
        }
        if (appSession.getCurrentUser().getCompanyBranch() != null)
        {
            proformaInvoiceDto.setGpsAddress(appSession.getCurrentUser().getCompanyBranch().getGpsAddress());
        }
        if (appSession.getCurrentUser().getCompanyBranch() != null)
        {
            proformaInvoiceDto.setWebsite(appSession.getCurrentUser().getCompanyBranch().getCompanyProfile().getWebsite());
        }
        if (appSession.getCurrentUser().getCompanyBranch() != null)
        {
            proformaInvoiceDto.setTinNo(appSession.getCurrentUser().getCompanyBranch().getCompanyProfile().getTinNo());
        }
        if (appSession.getCurrentUser().getCompanyBranch() != null)
        {
            proformaInvoiceDto.setEmailAddress(appSession.getCurrentUser().getCompanyBranch().getCompanyProfile().getCompanyEmail());
        }
        
        proformaInvoiceDtoList.add(proformaInvoiceDto);
       
        try
        {
             JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(proformaInvoiceDtoList);
        
            InputStream coverStream = getClass().getResourceAsStream(ReportFiles.PRO_INVOICE_COVER);
            coverHandler.reportParams.put("logo", ReportFiles.LOGO);

            JasperPrint coverPrint = JasperFillManager.fillReport(coverStream, coverHandler.reportParams, dataSource);
            HttpServletResponse servletResponse = (HttpServletResponse)FacesContext.getCurrentInstance().getExternalContext().getResponse();
            servletResponse.setContentType("application/pdf");
            ServletOutputStream outputStream = servletResponse.getOutputStream();
            JasperExportManager.exportReportToPdfStream(coverPrint, outputStream);
            FacesContext.getCurrentInstance().responseComplete();
        } catch (Exception e)
        {
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
        proformaInvoice.setLastModifiedDate(LocalDate.now());
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
    
}
