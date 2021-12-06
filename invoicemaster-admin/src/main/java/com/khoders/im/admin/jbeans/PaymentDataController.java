/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.khoders.im.admin.jbeans;

import Zenoph.SMSLib.Enums.MSGTYPE;
import Zenoph.SMSLib.Enums.REQSTATUS;
import static Zenoph.SMSLib.Enums.REQSTATUS.ERR_INSUFF_CREDIT;
import Zenoph.SMSLib.ZenophSMS;
import com.khoders.im.admin.dto.ProformaInvoiceDto;
import com.khoders.im.admin.listener.AppSession;
import com.khoders.im.admin.reports.ReportFiles;
import com.khoders.im.admin.services.PaymentService;
import com.khoders.invoicemaster.entities.Client;
import com.khoders.invoicemaster.entities.PaymentData;
import com.khoders.invoicemaster.entities.ProformaInvoice;
import com.khoders.invoicemaster.entities.ProformaInvoiceItem;
import com.khoders.invoicemaster.enums.DeliveryStatus;
import com.khoders.invoicemaster.enums.SMSType;
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

/**
 *
 * @author khoders
 */
@Named(value = "paymentDataController")
@SessionScoped
public class PaymentDataController implements Serializable{
    @Inject CrudApi crudApi;
    @Inject AppSession appSession;
    @Inject PaymentService paymentService;
    
    private String optionText;
    
    private PaymentData paymentData = new PaymentData();
    private List<PaymentData> paymentDataStatusList = new LinkedList<>();
    private List<PaymentData> paymentDataDeliveryList = new LinkedList<>();
    
    private PaymentStatus paymentStatus;
    private DeliveryStatus deliveryStatus;
    
    Sms sms = new Sms();
    String phoneNumber=null;
    Client client=null;
    SenderId senderId = null;
    double totalAmount=0.0;
    
    @PostConstruct
    private void init()
    {
        clearPaymentData();
        
    }
    
    public void fetchByPaymentStatus()
    {
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
        if(paymentStatus == null) return;
        paymentDataDeliveryList = paymentService.getInvoiceByDeliveryStatus(deliveryStatus);
    }
    
    public void updatePaymentData(PaymentData paymentData)
    {
        this.paymentData = crudApi.find(PaymentData.class, paymentData.getId());
        this.paymentData.setPaymentStatus(paymentData.getPaymentStatus());  
    }
    public void updateDeliveryData(PaymentData paymentData)
    {
        this.paymentData = crudApi.find(PaymentData.class, paymentData.getId());
        this.paymentData.setDeliveryStatus(paymentData.getDeliveryStatus());  
    }
    
   public void savePaymentData()
   {
        try 
        {
          if(crudApi.save(paymentData) != null)
          {
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
              
              FacesContext.getCurrentInstance().addMessage(null, 
                        new FacesMessage(FacesMessage.SEVERITY_INFO, Msg.DELETE_MESSAGE, null)); 
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
    
    public void deleteDeliveryData(PaymentData paymentData)
    {
        try
        {
          if(crudApi.delete(paymentData))
          {
              paymentDataDeliveryList.remove(paymentData);
              
              FacesContext.getCurrentInstance().addMessage(null, 
                        new FacesMessage(FacesMessage.SEVERITY_INFO, Msg.DELETE_MESSAGE, null)); 
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
    
    public void clearPaymentData() {
        paymentData = new PaymentData();
        paymentData.setUserAccount(appSession.getCurrentUser());
        optionText = "Save Changes";
        SystemUtils.resetJsfUI();
    }

    public void processPaymentMsg(PaymentData paymentData)
    {
       senderId = crudApi.getEm().createQuery("SELECT e FROM SenderId e", SenderId.class).getResultStream().findFirst().orElse(null);
        try 
        {
          ZenophSMS zsms = PaymentService.extractParams();
          zsms.setMessage(
                  "Thanks for shopping with Dolphin Doors, we'll be expecting you next time. \n "
                 + "Contact us: \n "
                 + "Website: https://dolphindoors.com/ \n"
                 + "Tel: +233 302 986 345/+233 302 252 027 \n"
                 + "Email: info@dolphindoors.com");
          
          if(paymentData.getProformaInvoice() != null){
              if(paymentData.getProformaInvoice().getClient() != null)
              {
                 client = paymentData.getProformaInvoice().getClient();
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
                                
                                paymentMessageSent(paymentData);
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
    
    public void generateWaybill(ProformaInvoice proformaInvoice)
    {
        
        List<ProformaInvoiceDto> proformaInvoiceDtoList = new LinkedList<>();
        
        List<ProformaInvoiceDto.ProformaInvoiceItem> invoiceItemDtoList = new LinkedList<>();
        
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
            ProformaInvoiceDto.ProformaInvoiceItem invoiceItemDto = new ProformaInvoiceDto.ProformaInvoiceItem();
                        
            if(invoiceItem.getInventory() != null)
            {
                if(invoiceItem.getInventory().getProduct() != null)
                {
                    invoiceItemDto.setProductCode(invoiceItem.getInventory().getProduct().getProductCode());
                    invoiceItemDto.setProductName(invoiceItem.getInventory().getProduct().getProductName());
                    invoiceItemDto.setDescription(invoiceItem.getInventory().getProduct().getDescription());
                }
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


    
}
