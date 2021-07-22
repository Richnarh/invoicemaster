/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.khoders.invoicemaster.jbeans.controller;

import com.khoders.invoicemaster.entites.Invoice;
import com.khoders.invoicemaster.entites.PaymentReceipt;
import com.khoders.invoicemaster.entites.model.ConvertToWords;
import com.khoders.invoicemaster.entites.model.PaymentReceiptDto;
import com.khoders.invoicemaster.jbeans.ReportFiles;
import com.khoders.invoicemaster.listener.AppSession;
import com.khoders.invoicemaster.service.InvoiceService;
import com.khoders.resource.enums.PaymentStatus;
import com.khoders.resource.jpa.CrudApi;
import com.khoders.resource.utilities.CollectionList;
import com.khoders.resource.utilities.DateRangeUtil;
import com.khoders.resource.utilities.FormView;
import com.khoders.resource.utilities.Msg;
import com.khoders.resource.utilities.SystemUtils;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.Serializable;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.print.Doc;
import javax.print.DocFlavor;
import javax.print.DocPrintJob;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.SimpleDoc;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.standard.Sides;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 *
 * @author richa
 */
@Named(value = "paymentReceiptController")
@SessionScoped
public class PaymentReceiptController implements Serializable
{
    @Inject private CrudApi crudApi;
    @Inject private AppSession appSession;
    @Inject private InvoiceService invoiceService;
    @Inject private ReportHandler reportHandler;
    
    private PaymentReceipt paymentReceipt = new PaymentReceipt();
    private List<PaymentReceipt> paymentReceiptList = new LinkedList<>();
    
    private DateRangeUtil dateRange = new DateRangeUtil();
    
    private FormView pageView = FormView.listForm();

    private String optionText;
    private Invoice selectedInvoice;

    public void initPaymentReceipt()
    {
        clearPaymentReceipt();
        
        if(selectedInvoice == null)
        {
           FacesContext.getCurrentInstance().addMessage(null, 
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, Msg.setMsg("Please select Invoice"), null));
           return;
        }
        pageView.restToCreateView();
    }
    
    public void searchReceipt()
    {
       paymentReceiptList = invoiceService.getPaymenReceiptList(selectedInvoice, dateRange);
    }

    public void printPayment(PaymentReceipt paymentReceipt)
    {  
        if(selectedInvoice == null)
        {
            return;
        }
        paymentReceiptList = invoiceService.getPrintReceipt(selectedInvoice, paymentReceipt.getId());
        
        List<PaymentReceiptDto> paymentReceiptDtoList = new LinkedList<>();
       
        for (PaymentReceipt receipt : paymentReceiptList)
        {
            PaymentReceiptDto paymentReceiptDto = new PaymentReceiptDto();
            paymentReceiptDto.setReceiptNo(receipt.getInvoice().getInvoiceNumber());
            paymentReceiptDto.setTotalAmount(receipt.getInvoice().getTotalAmount());
            paymentReceiptDto.setPaymentDate(receipt.getPaymentDate());
            paymentReceiptDto.setRecievedFrom(receipt.getReceivedFrom().getClientName());
            paymentReceiptDto.setReceivedAmount(receipt.getReceivedAmount());
            paymentReceiptDto.setPaymentMethod(receipt.getPaymentMethod() + "");
            paymentReceiptDto.setDescription(receipt.getDescription());
            paymentReceiptDto.setReceivedBy(appSession.getCurrentUser().getFullname());
            paymentReceiptDto.setUnpaidAmount(receipt.getAmountUnpaid());
            paymentReceiptDto.setAmountInWords(ConvertToWords.convertNumber(receipt.getReceivedAmount()));
            paymentReceiptDto.setPaymentStatus(receipt.getPaymentStatus().getLabel());

            paymentReceiptDtoList.add(paymentReceiptDto);
        }
        
        try
        {
            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(paymentReceiptDtoList);
            InputStream stream = getClass().getResourceAsStream(ReportFiles.PAYMENT_RECEIPT_FILE);
            
            reportHandler.reportParams.put("image", ReportFiles.LOGO);
            
            DocFlavor flavor = DocFlavor.SERVICE_FORMATTED.PAGEABLE;
            PrintRequestAttributeSet patts = new HashPrintRequestAttributeSet();
            patts.add(Sides.DUPLEX);
            PrintService[] ps = PrintServiceLookup.lookupPrintServices(flavor, patts);
            if (ps.length == 0)
            {
                throw new IllegalStateException("No Printer found");
            }
            System.out.println("Available printers: " + Arrays.asList(ps));

            PrintService myService = null;
            for (PrintService printService : ps)
            {
                if (printService.getName().equals("Your printer name"))
                {
                    myService = printService;
                    break;
                }
            }

            if (myService == null)
            {
                throw new IllegalStateException("Printer not found");
            }

            FileInputStream fis = new FileInputStream(ReportFiles.PAYMENT_RECEIPT_FILE);
            Doc pdfDoc = new SimpleDoc(fis, DocFlavor.INPUT_STREAM.AUTOSENSE, null);
            DocPrintJob printJob = myService.createPrintJob();
            printJob.print(pdfDoc, new HashPrintRequestAttributeSet());
            fis.close();
            
//            JasperPrint jasperPrint = JasperFillManager.fillReport(stream, reportHandler.reportParams, dataSource);
//            HttpServletResponse httpServletResponse = (HttpServletResponse)FacesContext.getCurrentInstance().getExternalContext().getResponse();
//            httpServletResponse.setContentType("application/pdf");
//            ServletOutputStream servletOutputStream = httpServletResponse.getOutputStream();
//            JasperExportManager.exportReportToPdfStream(jasperPrint, servletOutputStream);
//            FacesContext.getCurrentInstance().responseComplete();
            
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    
    public void savePaymentReceipt()
    {
        if (paymentReceipt.getReceivedAmount() > selectedInvoice.getAmountRemaining())
        {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN, 
                            "customer with receipt No.: "+paymentReceipt.getReceiptNumber()+" is over paying", null));
            return;
        }

        if (paymentReceipt.getReceivedAmount() == 0.0)
        {
            return; 
        }

        if (paymentReceipt.getReceivedAmount() != selectedInvoice.getAmountRemaining())
        {
            paymentReceipt.setPaymentStatus(PaymentStatus.PARTIALLY_PAID);
        } 
        else
        {
            paymentReceipt.setPaymentStatus(PaymentStatus.FULLY_PAID);

            selectedInvoice = crudApi.getEm().find(Invoice.class, paymentReceipt.getInvoice().getId());
            selectedInvoice.setPaymentStatus(PaymentStatus.FULLY_PAID);
//            crudApi.save(selectedInvoice);
        }

        try
        {
            selectedInvoice.setAmountRemaining(selectedInvoice.getAmountRemaining() - paymentReceipt.getReceivedAmount());
            crudApi.save(selectedInvoice);
    
            paymentReceipt.setAmountUnpaid(selectedInvoice.getAmountRemaining());
            
            System.out.println("Unpaid amount -- "+paymentReceipt.getAmountUnpaid());
            System.out.println("Amount Remaining -- "+selectedInvoice.getAmountRemaining());
            
            paymentReceipt.genCode();
            paymentReceipt.setReceivedFrom(selectedInvoice.getClient());
            paymentReceipt.setUserAccount(appSession.getCurrentUser());
            if(crudApi.save(paymentReceipt) != null)
            {
                paymentReceiptList = CollectionList.washList(paymentReceiptList, paymentReceipt);
                FacesContext.getCurrentInstance().addMessage(null, 
                        new FacesMessage(FacesMessage.SEVERITY_INFO, Msg.SUCCESS_MESSAGE, null));
                
                clearPaymentReceipt();
            }
            else
            {
                FacesContext.getCurrentInstance().addMessage(null, 
                        new FacesMessage(FacesMessage.SEVERITY_INFO, Msg.FAILED_MESSAGE, null));
            }
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    
    public void undoPaymentReceipt(PaymentReceipt paymentReceipt)
    {
         try 
        {          
            selectedInvoice = crudApi.getEm().find(Invoice.class, paymentReceipt.getInvoice().getId());
                        
            selectedInvoice.setAmountRemaining(selectedInvoice.getAmountRemaining() + paymentReceipt.getReceivedAmount());
            
            crudApi.save(selectedInvoice);
          
          if(crudApi.delete(paymentReceipt))
          {
              paymentReceiptList.remove(paymentReceipt);
               FacesContext.getCurrentInstance().addMessage(null, 
                        new FacesMessage(FacesMessage.SEVERITY_INFO, Msg.setMsg("Undo successfully!"), null));
          }
          else
          {
              FacesContext.getCurrentInstance().addMessage(null, 
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, Msg.FAILED_MESSAGE, null));
          }
        } 
        catch (Exception e) 
        {
           e.printStackTrace();
        }
    }
   
    public void editPaymentReceipt(PaymentReceipt paymentReceipt)
    {
        this.paymentReceipt=paymentReceipt;
        pageView.restToCreateView();
        optionText = "Update";
    }
 
    public void deletePaymentReceipt(PaymentReceipt paymentReceipt)
    {
        try
        {
            if(crudApi.delete(paymentReceipt))
            {
                paymentReceiptList.remove(paymentReceipt);
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
 
    public void clearPaymentReceipt()
    {
        paymentReceipt = new PaymentReceipt();
        paymentReceipt.setInvoice(selectedInvoice);
        paymentReceipt.setUserAccount(appSession.getCurrentUser());
        optionText = "Save Changes";
        SystemUtils.resetJsfUI();
    }
    
    public void closePage()
    {
       paymentReceipt = null;
       pageView.restToListView();
    }
    
    public void reset()
    {
        paymentReceiptList = new LinkedList<>();
        selectedInvoice = null;
    }
    
    public PaymentReceipt getPaymentReceipt()
    {
        return paymentReceipt;
    }

    public void setPaymentReceipt(PaymentReceipt paymentReceipt)
    {
        this.paymentReceipt = paymentReceipt;
    }

    public String getOptionText()
    {
        return optionText;
    }

    public void setOptionText(String optionText)
    {
        this.optionText = optionText;
    }

    public Invoice getSelectedInvoice()
    {
        return selectedInvoice;
    }

    public void setSelectedInvoice(Invoice selectedInvoice)
    {
        this.selectedInvoice = selectedInvoice;
    }

    public List<PaymentReceipt> getPaymentReceiptList()
    {
        return paymentReceiptList;
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
    
}
