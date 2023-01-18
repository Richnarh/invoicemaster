/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.khoders.invoicemaster.jbeans.controller;

import com.khoders.invoicemaster.dto.ProformaInvoiceDto;
import com.khoders.invoicemaster.dto.ProformaInvoiceItemDto;
import com.khoders.invoicemaster.entities.Client;
import com.khoders.invoicemaster.entities.Inventory;
import com.khoders.invoicemaster.entities.PaymentData;
import com.khoders.invoicemaster.entities.ProformaInvoice;
import com.khoders.invoicemaster.entities.ProformaInvoiceItem;
import com.khoders.invoicemaster.enums.DeliveryStatus;
import com.khoders.invoicemaster.enums.SMSType;
import com.khoders.invoicemaster.jbeans.ReportFiles;
import com.khoders.invoicemaster.listener.AppSession;
import com.khoders.invoicemaster.service.PaymentService;
import com.khoders.invoicemaster.service.ProformaInvoiceService;
import com.khoders.invoicemaster.sms.SenderId;
import com.khoders.invoicemaster.sms.Sms;
import com.khoders.resource.enums.PaymentStatus;
import com.khoders.resource.jpa.CrudApi;
import com.khoders.resource.utilities.Msg;
import com.khoders.resource.utilities.SystemUtils;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
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
import org.primefaces.component.tabview.TabView;
import org.primefaces.event.TabChangeEvent;

/**
 *
 * @author khoders
 */
@Named(value = "paymentDataController")
@SessionScoped
public class PaymentDataController implements Serializable{
    @Inject private CrudApi crudApi;
    @Inject private AppSession appSession;
    @Inject private PaymentService paymentService;
    @Inject private ProformaInvoiceService proformaInvoiceService;
    
    private String optionText;
    
    private PaymentData paymentData = new PaymentData();
    private List<PaymentData> paymentDataStatusList = new LinkedList<>();
    private List<PaymentData> paymentDataDeliveryList = new LinkedList<>();
    private List<PaymentData> pendingDeliveryList = new LinkedList<>();
    
    private PaymentStatus paymentStatus;
    private DeliveryStatus deliveryStatus;
    
    private int selectedTabIndex;
    
    Sms sms = new Sms();
    String phoneNumber=null;
    Client client=null;
    SenderId senderId = null;
    double totalAmount=0.0;
    
    @PostConstruct
    public void init()
    {
        clearPaymentData();
        pendingDeliveryList = paymentService.getInvoiceByDeliveryStatus(DeliveryStatus.PENDING_DELIVERY);
    }
    
    public void selectTransaction(PaymentData paymentData)
    {
      this.paymentData = paymentData; 
    }
    public void fetchByPaymentStatus()
    {
        selectedTabIndex = 1;
        if(paymentStatus == null) return;
        paymentDataStatusList = paymentService.getInvoiceByPaymentStatus(paymentStatus);
        
        totalAmount = 0.0;
        for (PaymentData data : paymentDataStatusList)
        {
            if(data.getProformaInvoice() != null)
            {
               totalAmount += data.getProformaInvoice().getTotalAmount();
            }
            else
            {
                System.out.println("Data => "+data.getProformaInvoice());
            }
        }
    }
    public void fetchByDeliveryStatus()
    {
        selectedTabIndex = 2;
        if(deliveryStatus == null) return;
        paymentDataDeliveryList = paymentService.getInvoiceByDeliveryStatus(deliveryStatus);
    }
    
    public void updatePaymentData(PaymentData paymentData)
    {
        this.paymentData = paymentData;
    }
    public void updateDeliveryData(PaymentData paymentData)
    {
        this.paymentData = crudApi.find(PaymentData.class, paymentData.getId());
        this.paymentData.setDeliveryStatus(paymentData.getDeliveryStatus());  
    }
    
    public void saveDeliveryStatus(){
        if(crudApi.save(paymentData) != null)
        {
            Msg.info(Msg.SUCCESS_MESSAGE);
        }
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
    
   public void savePaymentData()
   {
        try 
        {
            PaymentData data = crudApi.find(PaymentData.class, paymentData.getId());
            if(data.getPaymentStatus() == PaymentStatus.FULLY_PAID){
                Msg.error("Status of this transaction is marked as "+data.getPaymentStatus()+", it can't be reverted!");
                return;
            }
          if(crudApi.save(paymentData) != null)
          {
              if(paymentData.getPaymentStatus() == PaymentStatus.FULLY_PAID)
              {
                  System.out.println("Here 1--- ");
                 List<Inventory> inventoryList = proformaInvoiceService.getInventoryList();
                 List<ProformaInvoiceItem> purchasedList = proformaInvoiceService.getProformaInvoiceItemList(paymentData.getProformaInvoice());
                  for (ProformaInvoiceItem item : purchasedList)
                  {
                      for (Inventory inventory : inventoryList)
                      {
                          System.out.println("here 2");
                          if(item.getInventory().getId().equals(inventory.getId()))
                          {
                              System.out.println("here 3");
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
   
    public void editPaymentData(PaymentData paymentData)
    {
       optionText = "Update";
       this.paymentData=paymentData;
    }
    
    public void deletePaymentData(PaymentData paymentData)
    {
        try
        {
          if(crudApi.delete(paymentData))
          {
              paymentDataStatusList.remove(paymentData);
              
              Msg.error(Msg.DELETE_MESSAGE);
          }
          else
          {
              Msg.error(Msg.FAILED_MESSAGE);
          }
        } catch (Exception e) 
        {
            e.printStackTrace();
        }
    }
    
    public void deleteDeliveryData(PaymentData paymentData)
    {
        try
        {
          if(crudApi.delete(paymentData))
          {
              paymentDataDeliveryList.remove(paymentData);
              Msg.error(Msg.DELETE_MESSAGE); 
          }
          else
          {
              Msg.error(Msg.FAILED_MESSAGE);
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
    public void processPaymentMsg(PaymentData paymentData)
    {
       senderId = crudApi.getEm().createQuery("SELECT e FROM SenderId e", SenderId.class).getResultStream().findFirst().orElse(null);
        try 
        {
//          ZenophSMS zsms = PaymentService.extractParams();
//          zsms.setMessage(
//                  "Thanks for shopping with Dolphin Doors, we'll be expecting you next time. \n "
//                 + "Contact us: \n "
//                 + "Website: https://dolphindoors.com/ \n"
//                 + "Tel: +233 302 986 345/+233 302 252 027 \n"
//                 + "Email: info@dolphindoors.com");
//          
//          if(paymentData.getProformaInvoice() != null){
//              if(paymentData.getProformaInvoice().getClient() != null)
//              {
//                 client = paymentData.getProformaInvoice().getClient();
//                 phoneNumber = paymentData.getProformaInvoice().getClient().getPhone();
//              }
//            }
//          
//            List<String> numbers = zsms.extractPhoneNumbers(phoneNumber);
//
//            for (String number : numbers)
//            {
//                zsms.addRecipient(number);
//            }
//            
//            zsms.setSenderId(senderId.getSenderIdentity());
//            zsms.setMessageType(MSGTYPE.TEXT);
//
//            List<String[]> response = zsms.submit();
//            for (String[] destination : response)
//            {
//                    REQSTATUS reqstatus = REQSTATUS.fromInt(Integer.parseInt(destination[0]));
//                    if (reqstatus == null)
//                    {
//                        FacesContext.getCurrentInstance().addMessage(null,
//                                new FacesMessage(FacesMessage.SEVERITY_ERROR, Msg.setMsg("failed to send message"), null));
//                        break;
//                    } else
//                    {
//                        switch (reqstatus)
//                        {
//                            case SUCCESS:
//                                saveMessage();
//                                
//                                paymentMessageSent(paymentData);
//                                break;
//                            case ERR_INSUFF_CREDIT:
//                                FacesContext.getCurrentInstance().addMessage(null,
//                                        new FacesMessage(FacesMessage.SEVERITY_ERROR, Msg.setMsg("Insufficeint Credit"), null));
//                            default:
//                                FacesContext.getCurrentInstance().addMessage(null,
//                                        new FacesMessage(FacesMessage.SEVERITY_ERROR, Msg.setMsg("Failed to send message"), null));
//                                return;
//                        }
//                    }
//                }

    }catch(Exception e)
    {
     e.printStackTrace();
    }
   }
    
    public void paymentMessageSent(PaymentData data) {
        paymentData = crudApi.find(PaymentData.class, data.getId());
        paymentData.setPaymentMessage(true);
        
        crudApi.save(paymentData);
        
        fetchByPaymentStatus();
    }
    
    public void saveMessage()
    {
        try
        {
            sms.genCode();
            sms.setSmsTime(LocalDateTime.now());
            sms.setMessage("Thanks for shopping with Dolphin Doors, we'll be expecting you next time.");
            sms.setClient(client);
            sms.setsMSType(SMSType.SYSTEM_SMS);
            sms.setSenderId(senderId);
            sms.setUserAccount(appSession.getCurrentUser());
           if(crudApi.save(sms) != null)
           {
               FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, Msg.setMsg("SMS sent to "+client.getClientName()), null));
               
               System.out.println("SMS sent and saved -- ");
           }
        } catch (Exception e)
        {
          e.printStackTrace();
        }
    }
    
    public void generateWaybill(PaymentData paymentData)
    {
        if(paymentData == null){
            Msg.error("Something went wrong processing this data");
            System.out.println("Something went wrong processing this data @generateWaybill");
            return;
        }
        
        ProformaInvoice proformaInvoice = paymentData.getProformaInvoice();
        List<ProformaInvoiceDto> proformaInvoiceDtoList = new LinkedList<>();
        
        List<ProformaInvoiceItemDto> invoiceItemDtoList = new LinkedList<>();
        
        List<ProformaInvoiceItem> invoiceItemList  = paymentService.getProformaInvoiceItem(proformaInvoice);
        
            ProformaInvoiceDto proformaInvoiceDto = new ProformaInvoiceDto();
            
            if(proformaInvoice.getClient() != null)
            {
              proformaInvoiceDto.setClientName(proformaInvoice.getClient().getClientName().toUpperCase());
              proformaInvoiceDto.setEmailAddress(proformaInvoice.getClient().getEmailAddress());
              proformaInvoiceDto.setAddress(proformaInvoice.getClient().getAddress().toUpperCase());  
            }
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
     
        for (ProformaInvoiceItem invoiceItem : invoiceItemList)
        {
            ProformaInvoiceItemDto invoiceItemDto = new ProformaInvoiceItemDto();
                        
            if(invoiceItem.getInventory() != null && invoiceItem.getInventory().getProduct() != null)
            {
               invoiceItemDto.setProductCode(invoiceItem.getInventory().getProduct().getProductCode());
               invoiceItemDto.setProductName(invoiceItem.getInventory().getProduct().getProductName());
               invoiceItemDto.setDescription(invoiceItem.getInventory().getProduct().getDescription());
            }
            invoiceItemDto.setFrameSize(invoiceItem.getInventory().getFrameSize());
            invoiceItemDto.setWidth(invoiceItem.getInventory().getWidth());
            invoiceItemDto.setHeight(invoiceItem.getInventory().getHeight());
            invoiceItemDto.setQuantity(invoiceItem.getQuantity());
            invoiceItemDto.setUnitPrice(invoiceItem.getUnitPrice());
            
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
            
            proformaInvoiceDtoList.add(proformaInvoiceDto);
            
        try
        {
            Map<String, Object> reportParams = new HashMap<>();
            
            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(proformaInvoiceDtoList);
            InputStream stream = getClass().getResourceAsStream(ReportFiles.WAYBILL_FILE);
            
            reportParams.put("logo", ReportFiles.LOGO);
            
            JasperPrint jasperPrint = JasperFillManager.fillReport(stream, reportParams, dataSource);
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

    public String getOptionText() {
        return optionText;
    }

    public void setOptionText(String optionText) {
        this.optionText = optionText;
    }

    public PaymentData getPaymentData()
    {
        return paymentData;
    }

    public void setPaymentData(PaymentData paymentData)
    {
        this.paymentData = paymentData;
    }

    public PaymentStatus getPaymentStatus()
    {
        return paymentStatus;
    }

    public void setPaymentStatus(PaymentStatus paymentStatus)
    {
        this.paymentStatus = paymentStatus;
    }

    public DeliveryStatus getDeliveryStatus()
    {
        return deliveryStatus;
    }

    public void setDeliveryStatus(DeliveryStatus deliveryStatus)
    {
        this.deliveryStatus = deliveryStatus;
    }

    public List<PaymentData> getPaymentDataStatusList()
    {
        return paymentDataStatusList;
    }

    public List<PaymentData> getPaymentDataDeliveryList()
    {
        return paymentDataDeliveryList;
    }

    public double getTotalAmount()
    {
        return totalAmount;
    }
    public int getSelectedTabIndex()
    {
        return selectedTabIndex;
    }

    public void setSelectedTabIndex(int selectedTabIndex)
    {
        this.selectedTabIndex = selectedTabIndex;
    }

    public List<PaymentData> getPendingDeliveryList()
    {
        return pendingDeliveryList;
    }

    
}
