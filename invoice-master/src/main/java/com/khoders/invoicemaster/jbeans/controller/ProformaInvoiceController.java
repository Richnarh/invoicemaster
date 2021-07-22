/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.khoders.invoicemaster.jbeans.controller;

import com.khoders.invoicemaster.entites.ColoursConfigItems;
import com.khoders.invoicemaster.entites.DeliveryTermConfigItems;
import com.khoders.invoicemaster.entites.Invoice;
import com.khoders.invoicemaster.entites.ProformaInvoice;
import com.khoders.invoicemaster.entites.ProformaInvoiceItem;
import com.khoders.invoicemaster.entites.ReceivedDocumentConfigItems;
import com.khoders.invoicemaster.entites.ValidationConfigItems;
import com.khoders.invoicemaster.entites.model.ProformaInvoiceDto;
import com.khoders.invoicemaster.entities.master.TaxScheme;
import com.khoders.invoicemaster.jbeans.ReportFiles;
import com.khoders.invoicemaster.listener.AppSession;
import com.khoders.invoicemaster.service.ProformaInvoiceService;
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
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;
import org.primefaces.component.tabview.TabView;
import org.primefaces.event.TabChangeEvent;

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

    private ProformaInvoiceItem proformaInvoiceItem = new ProformaInvoiceItem();
    private List<ProformaInvoiceItem> proformaInvoiceItemList = new LinkedList<>();
    private List<ProformaInvoiceItem> removedProformaInvoiceItemList = new LinkedList<>();

    private DeliveryTermConfigItems deliveryTermConfigItems = new DeliveryTermConfigItems();
    private List<DeliveryTermConfigItems> deliveryTermConfigItemsList = new LinkedList<>();
    
    private ValidationConfigItems validationConfigItems = new ValidationConfigItems();
    private List<ValidationConfigItems> validationConfigItemsList = new LinkedList<>();
    
    private ColoursConfigItems coloursConfigItems = new ColoursConfigItems();
    private List<ColoursConfigItems> coloursConfigItemsList = new LinkedList<>();
    
    private ReceivedDocumentConfigItems receivedDocumentConfigItems = new ReceivedDocumentConfigItems();
    private List<ReceivedDocumentConfigItems> receivedDocumentConfigItemsList = new LinkedList<>();
    
    private List<TaxScheme> taxSchemeList = new LinkedList<>();
    
    private int selectedTabIndex;
    private String optionText;
    private double totalAmount,totalDiscount;
     
    private ServletOutputStream servletOutputStream = null;

    @PostConstruct
    private void init()
    {
        proformaInvoiceList = proformaInvoiceService.getProformaInvoiceList();
        taxSchemeList = proformaInvoiceService.getTaxSchemeList();
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
    
    public void convertToInvoice(ProformaInvoice proformaInvoice)
    {
      Invoice invoice = proformaInvoiceService.extractFromProformaInvoice(proformaInvoice);  
      if(invoice != null)
      {
           proformaInvoice = crudApi.getEm().find(ProformaInvoice.class, proformaInvoice.getId());
           proformaInvoice.setConverted(true);
           crudApi.save(proformaInvoice);
            
            init();
            
          FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_INFO, Msg.setMsg("Converted Successfully!"), null));
      }
    }
        
    public void filterProformaInvoice()
    {
        proformaInvoiceList = proformaInvoiceService.getProformaInvoice(dateRange, proformaInvoice);   
    }
    
    public void reset()
    {
        proformaInvoiceList = new LinkedList<>();
    }
    
    public void saveProformaInvoice()
    {
        try
        {
            if (crudApi.save(proformaInvoice) != null)
            {
                proformaInvoiceList = CollectionList.washList(proformaInvoiceList, proformaInvoice);
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_INFO, Msg.SUCCESS_MESSAGE, null));

                clearProformaInvoice();
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

    public void manageProformaInvoiceItem(ProformaInvoice proformaInvoice)
    {
        this.proformaInvoice = proformaInvoice;
        pageView.restToDetailView();
        
        clearProformaInvoiceItem();
        
        proformaInvoiceItemList = proformaInvoiceService.getProformaInvoiceItemList(proformaInvoice);
        
        for (ProformaInvoiceItem items : proformaInvoiceItemList) 
        {
            totalAmount += (items.getQuantity() * items.getUnitPrice());
        }
    }

    public void manageDeliveryTermItemConfig(ProformaInvoice proformaInvoice)
    {
        this.proformaInvoice = proformaInvoice;
        clearDeliveryTermConfigItems();
        
        deliveryTermConfigItemsList = proformaInvoiceService.getDeliveryTermConfigItemsList(proformaInvoice);
    }

    public void manageValidationConfigItems(ProformaInvoice proformaInvoice)
    {
        this.proformaInvoice = proformaInvoice;
        clearValidationConfigItems();
        
        validationConfigItemsList = proformaInvoiceService.getValidationConfigItemsList(proformaInvoice);
    }

    public void manageColoursConfigItems(ProformaInvoice proformaInvoice)
    {
        this.proformaInvoice = proformaInvoice;
        clearColoursConfigItems();
        
        coloursConfigItemsList = proformaInvoiceService.getColoursConfigItemsList(proformaInvoice);
    }

    public void manageReceivedDocumentConfigItems(ProformaInvoice proformaInvoice)
    {
        this.proformaInvoice = proformaInvoice;
        clearReceivedDocumentConfigItems();
        
        receivedDocumentConfigItemsList = proformaInvoiceService.getReceivedDocumentConfigItemsList(proformaInvoice);
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
            
            if(proformaInvoiceItem.getUnitPrice() <= 0)
            {
              FacesContext.getCurrentInstance().addMessage(null, 
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, Msg.setMsg("Please enter price"), null));
              return;
            }
            
             if(proformaInvoiceItem != null)
              {
                totalAmount += proformaInvoiceItem.getQuantity() * proformaInvoiceItem.getUnitPrice();
                double x = proformaInvoiceItem.getQuantity() * proformaInvoiceItem.getUnitPrice();
                proformaInvoiceItem.setTotalAmount(x);
                proformaInvoiceItem.genCode();
                proformaInvoiceItemList.add(proformaInvoiceItem);
                proformaInvoiceItemList = CollectionList.washList(proformaInvoiceItemList, proformaInvoiceItem);
                
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, Msg.setMsg("Proforma Invoice item added"), null));
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
    
    public void saveAll()
    {
        try 
        {
                for (ProformaInvoiceItem item : proformaInvoiceItemList) 
                {
//                    if (totalAmount != proformaInvoice.getTotalAmount())
//                    {
//                        FacesContext.getCurrentInstance().addMessage(null,
//                                new FacesMessage(FacesMessage.SEVERITY_ERROR, Msg.setMsg("The item total sum: " + (totalAmount) + " is not equivalent to the proforma invoice total: " + proformaInvoice.getTotalAmount()), null));
//                        return;
//                    }
                    crudApi.save(item); 
                }
                
                for (ProformaInvoiceItem invoiceItem : removedProformaInvoiceItemList)
                {
                    crudApi.delete(invoiceItem);
                    removedProformaInvoiceItemList.remove(invoiceItem);
                }
                
                FacesContext.getCurrentInstance().addMessage(null, 
                        new FacesMessage(FacesMessage.SEVERITY_INFO, Msg.setMsg("Proforma Invoice item list saved!"), null));
            
        } catch (Exception e) 
        {
            e.printStackTrace();
        }
    }
    
    public void editProformaInvoiceItem(ProformaInvoiceItem proformaInvoiceItem)
    {
        this.proformaInvoiceItem = proformaInvoiceItem;
        proformaInvoiceItemList.remove(proformaInvoiceItem);
        totalAmount -= (proformaInvoiceItem.getQuantity() * proformaInvoiceItem.getUnitPrice());
        optionText = "Update";
    }
    
    public void deleteProformaInvoiceItem(ProformaInvoiceItem proformaInvoiceItem)
    {
        totalAmount -= (proformaInvoiceItem.getQuantity() * proformaInvoiceItem.getUnitPrice());
        removedProformaInvoiceItemList = CollectionList.washList(removedProformaInvoiceItemList, proformaInvoiceItem);
        proformaInvoiceItemList.remove(proformaInvoiceItem);
    }
    
    public void editProformaInvoice(ProformaInvoice proformaInvoice)
    {
        this.proformaInvoice = proformaInvoice;
        pageView.restToCreateView();
        optionText = "Update";
    }
    
//  Configs
    public void saveDeliveryTermConfigItems()
    {
        try
        {
           if (crudApi.save(deliveryTermConfigItems) != null)
           {
               deliveryTermConfigItemsList = CollectionList.washList(deliveryTermConfigItemsList, deliveryTermConfigItems);
               FacesContext.getCurrentInstance().addMessage(null, 
                        new FacesMessage(FacesMessage.SEVERITY_INFO, Msg.SUCCESS_MESSAGE, null));
           }
           else
           {
               FacesContext.getCurrentInstance().addMessage(null, 
                        new FacesMessage(FacesMessage.SEVERITY_INFO, Msg.FAILED_MESSAGE, null));
           }
               
               clearDeliveryTermConfigItems();
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    
    public void saveValidationConfigItems()
    {
        try
        {
           if (crudApi.save(validationConfigItems) != null)
           {
               validationConfigItemsList = CollectionList.washList(validationConfigItemsList, validationConfigItems);
               FacesContext.getCurrentInstance().addMessage(null, 
                        new FacesMessage(FacesMessage.SEVERITY_INFO, Msg.SUCCESS_MESSAGE, null));
           }
           else
           {
               FacesContext.getCurrentInstance().addMessage(null, 
                        new FacesMessage(FacesMessage.SEVERITY_INFO, Msg.FAILED_MESSAGE, null));
           }
               
               clearValidationConfigItems();
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    
    
    public void saveColoursConfigItems()
    {
        try
        {
           if (crudApi.save(coloursConfigItems) != null)
           {
               coloursConfigItemsList = CollectionList.washList(coloursConfigItemsList, coloursConfigItems);
               FacesContext.getCurrentInstance().addMessage(null, 
                        new FacesMessage(FacesMessage.SEVERITY_INFO, Msg.SUCCESS_MESSAGE, null));
           }
           else
           {
               FacesContext.getCurrentInstance().addMessage(null, 
                        new FacesMessage(FacesMessage.SEVERITY_INFO, Msg.FAILED_MESSAGE, null));
           }
               
            clearColoursConfigItems();
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    
    public void saveReceivedDocumentConfigItems()
    {
        try
        {
            if (crudApi.save(receivedDocumentConfigItems) != null)
            {
                receivedDocumentConfigItemsList = CollectionList.washList(receivedDocumentConfigItemsList, receivedDocumentConfigItems);
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_INFO, Msg.SUCCESS_MESSAGE, null));
            } else
            {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_INFO, Msg.FAILED_MESSAGE, null));
            }
   
            clearValidationConfigItems();
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    
    public void editDeliveryTermConfigItems(DeliveryTermConfigItems deliveryTermConfigItems)
    {
        this.deliveryTermConfigItems=deliveryTermConfigItems;
        optionText = "Update";
    }
    public void editReceivedDocumentConfigItems(ReceivedDocumentConfigItems receivedDocumentConfigItems)
    {
        this.receivedDocumentConfigItems=receivedDocumentConfigItems;
        optionText = "Update";
    }
    public void editColoursConfigItems(ColoursConfigItems coloursConfigItems)
    {
        this.coloursConfigItems=coloursConfigItems;
        optionText = "Update";
    }
    public void editValidationConfigItems(ValidationConfigItems validationConfigItems)
    {
        this.validationConfigItems=validationConfigItems;
        optionText = "Update";
    }

    public void deleteDeliveryTermConfigItems(DeliveryTermConfigItems deliveryTermConfigItems)    {
        try
        {
            if(crudApi.delete(deliveryTermConfigItems))
            {
                deliveryTermConfigItemsList.remove(deliveryTermConfigItems);
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
    
    public void deleteValidationConfigItems(ValidationConfigItems validationConfigItems)    {
        try
        {
            if(crudApi.delete(validationConfigItems))
            {
                validationConfigItemsList.remove(validationConfigItems);
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
    
    
    public void deleteColoursConfigItems(ColoursConfigItems coloursConfigItems)    {
        try
        {
            if(crudApi.delete(coloursConfigItems))
            {
                coloursConfigItemsList.remove(coloursConfigItems);
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
    
    public void deleteReceivedDocumentConfigItems(ReceivedDocumentConfigItems receivedDocumentConfigItems)    {
        try
        {
            if(crudApi.delete(receivedDocumentConfigItems))
            {
                receivedDocumentConfigItemsList.remove(receivedDocumentConfigItems);
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
       proformaInvoice = new ProformaInvoice();
       totalAmount = 0;
       optionText = "Save Changes";
       pageView.restToListView();
    }
    
    public void clearProformaInvoiceItem()
    {
        proformaInvoiceItem = new ProformaInvoiceItem();
        proformaInvoiceItem.setProformaInvoice(proformaInvoice);
        proformaInvoiceItem.setUserAccount(appSession.getCurrentUser());
        optionText = "Save Changes";
        SystemUtils.resetJsfUI();
    }
    
    public void clearDeliveryTermConfigItems()
    {
        deliveryTermConfigItems = new DeliveryTermConfigItems();
        deliveryTermConfigItems.setProformaInvoice(proformaInvoice);
        deliveryTermConfigItems.setUserAccount(appSession.getCurrentUser());
        optionText = "Save Changes";
        SystemUtils.resetJsfUI();
    }
    
    public void clearValidationConfigItems()
    {
        validationConfigItems = new ValidationConfigItems();
        validationConfigItems.setProformaInvoice(proformaInvoice);
        validationConfigItems.setUserAccount(appSession.getCurrentUser());
        optionText = "Save Changes";
        SystemUtils.resetJsfUI();
    }
    
    public void clearColoursConfigItems()
    {
        coloursConfigItems = new ColoursConfigItems();
        coloursConfigItems.setProformaInvoice(proformaInvoice);
        coloursConfigItems.setUserAccount(appSession.getCurrentUser());
        optionText = "Save Changes";
        SystemUtils.resetJsfUI();
    }
    
    public void clearReceivedDocumentConfigItems()
    {
        receivedDocumentConfigItems = new ReceivedDocumentConfigItems();
        receivedDocumentConfigItems.setProformaInvoice(proformaInvoice);
        optionText = "Save Changes";
        SystemUtils.resetJsfUI();
    }

    public void clearProformaInvoice()
    {
        proformaInvoice = new ProformaInvoice();
        proformaInvoice.setUserAccount(appSession.getCurrentUser());
        optionText = "Save Changes";
        SystemUtils.resetJsfUI();
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
    
    
    public void generateProformaInvoice(ProformaInvoice proformaInvoice)
    {
        List<ProformaInvoiceDto> proformaInvoiceDtoList = new LinkedList<>();
        
        List<ProformaInvoiceDto.DeliveryTerm> deliveryTermDtoList = new LinkedList<>();
        List<ProformaInvoiceDto.Validation> validationDtoList = new LinkedList<>();
        List<ProformaInvoiceDto.Colours> coloursDtoList = new LinkedList<>();
        List<ProformaInvoiceDto.ReceivedDocument> receivedDocumentDtoList = new LinkedList<>();
        List<ProformaInvoiceDto.ProformaInvoiceItem> invoiceItemDtoList = new LinkedList<>();
        
        List<DeliveryTermConfigItems> deliveryTermList  = proformaInvoiceService.getDeliveryTermConfigItemsList(proformaInvoice);
        List<ValidationConfigItems> validationItemsList  = proformaInvoiceService.getValidationConfigItems(proformaInvoice);
        List<ReceivedDocumentConfigItems> receivedDocumentItemsList  = proformaInvoiceService.getReceivedDocumentConfigItems(proformaInvoice);
        List<ColoursConfigItems> coloursItemsList  = proformaInvoiceService.getColoursConfigItems(proformaInvoice);
        List<ProformaInvoiceItem> invoiceItemList  = proformaInvoiceService.getProformaInvoiceItemReceipt(proformaInvoice);
        
        double grandTotalAmount = invoiceItemList.stream().mapToDouble(ProformaInvoiceItem::getTotalAmount).sum();
        
            ProformaInvoiceDto proformaInvoiceDto = new ProformaInvoiceDto();
            proformaInvoiceDto.setClientName(proformaInvoice.getClient().getClientName());
            proformaInvoiceDto.setEmailAddress(proformaInvoice.getClient().getEmailAddress());
            proformaInvoiceDto.setPhone(proformaInvoice.getClient().getPhone());
            proformaInvoiceDto.setAddress(proformaInvoice.getClient().getAddress());
            proformaInvoiceDto.setIssuedDate(proformaInvoice.getIssuedDate());
            proformaInvoiceDto.setExpiryDate(proformaInvoice.getExpiryDate());
            proformaInvoiceDto.setQuotationNumber(proformaInvoice.getQuotationNumber());
            proformaInvoiceDto.setClientCode(proformaInvoice.getClient().getClientCode());
            proformaInvoiceDto.setDescription(proformaInvoice.getDescription());
            proformaInvoiceDto.setTotalAmount(grandTotalAmount);
            
        if (appSession.getCurrentUser().getCompanyBranch().getTelephoneNo() != null)
        {
            proformaInvoiceDto.setTelephoneNo(appSession.getCurrentUser().getCompanyBranch().getTelephoneNo());
        }
        if (appSession.getCurrentUser().getCompanyBranch() != null)
        {
            proformaInvoiceDto.setBranchName(appSession.getCurrentUser().getCompanyBranch() + "");
        }
        if (appSession.getCurrentUser().getCompanyBranch().getGpsAddress() != null)
        {
            proformaInvoiceDto.setGpsAddress(appSession.getCurrentUser().getCompanyBranch().getGpsAddress());
        }
        if (appSession.getCurrentUser().getCompanyBranch().getCompanyProfile().getWebsite() != null)
        {
            proformaInvoiceDto.setWebsite(appSession.getCurrentUser().getCompanyBranch().getCompanyProfile().getWebsite());
        }
        if (appSession.getCurrentUser().getCompanyBranch().getCompanyProfile().getTinNo() != null)
        {
            proformaInvoiceDto.setTinNo(appSession.getCurrentUser().getCompanyBranch().getCompanyProfile().getTinNo());
        }
            
        for (DeliveryTermConfigItems configItems : deliveryTermList)
        {
            ProformaInvoiceDto.DeliveryTerm deliveryTerm = new ProformaInvoiceDto.DeliveryTerm();
            deliveryTerm.setDeliveryTerm(configItems.getDeliveryTerm().getDeliveryTerm());
            deliveryTermDtoList.add(deliveryTerm);
        }
        
        for (ValidationConfigItems items : validationItemsList)
        {
            ProformaInvoiceDto.Validation validation = new ProformaInvoiceDto.Validation();
            validation.setValidation(items.getValidation().getValidation());
            validationDtoList.add(validation);
            
        }
        
        for (ReceivedDocumentConfigItems documentConfigItems : receivedDocumentItemsList)
        {
            ProformaInvoiceDto.ReceivedDocument receivedDocument = new ProformaInvoiceDto.ReceivedDocument();
            receivedDocument.setReceivedDocument(documentConfigItems.getReceivedDocument().getDocumentName());
            
            receivedDocumentDtoList.add(receivedDocument);
            
        }
        
        for (ColoursConfigItems items : coloursItemsList)
        {
            ProformaInvoiceDto.Colours colours = new ProformaInvoiceDto.Colours();
            colours.setColours(items.getColours().getColourName());
            
            coloursDtoList.add(colours);
        }
        
        for (ProformaInvoiceItem invoiceItem : invoiceItemList)
        {
            ProformaInvoiceDto.ProformaInvoiceItem invoiceItemDto = new ProformaInvoiceDto.ProformaInvoiceItem();
            try
            {
                byte[] image = invoiceItem.getInventory().getProduct().getProductImage();
                if(image != null)
                {
                    InputStream inputStream = new ByteArrayInputStream(image);
//                    BufferedImage bufferedImage = ImageIO.read(inputStream);
                    invoiceItemDto.setProductImage(inputStream);  
                }
                
            } catch (Exception e)
            {
                e.printStackTrace();
            }
            
            invoiceItemDto.setProductCode(invoiceItem.getInventory().getProduct().getProductCode());
            invoiceItemDto.setFrameSize(invoiceItem.getInventory().getFrameSize());
            invoiceItemDto.setWidth(invoiceItem.getInventory().getWidth());
            invoiceItemDto.setHeight(invoiceItem.getInventory().getHeight());
            invoiceItemDto.setQuantity(invoiceItem.getQuantity());
            invoiceItemDto.setUnitPrice(invoiceItem.getUnitPrice());
            invoiceItemDto.setTotalAmount(invoiceItem.getTotalAmount());
            invoiceItemDto.setDescription(invoiceItem.getDescription());
            
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
            proformaInvoiceDto.setDeliveryTermList(deliveryTermDtoList);
            proformaInvoiceDto.setValidationList(validationDtoList);
            proformaInvoiceDto.setColoursList(coloursDtoList);
            proformaInvoiceDto.setReceivedDocumentList(receivedDocumentDtoList);
            proformaInvoiceDto.setInvoiceItemList(invoiceItemDtoList);
            
            proformaInvoiceDtoList.add(proformaInvoiceDto);
            
        try
        {
            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(proformaInvoiceDtoList);
//            JasperReport deliveryTermReport = (JasperReport) JRLoader.loadObject(getClass().getResourceAsStream(ReportFiles.DELIVERY_TERM_FILE));
//            JasperReport validationReport = (JasperReport) JRLoader.loadObject(getClass().getResourceAsStream(ReportFiles.VALIDATION_FILE));
//            JasperReport receiveDocumentReport = (JasperReport) JRLoader.loadObject(getClass().getResourceAsStream(ReportFiles.RECEIVED_DOCUMENT_FILE));
//            JasperReport coloursReport = (JasperReport) JRLoader.loadObject(getClass().getResourceAsStream(ReportFiles.COLOURS_FILE));
            JasperReport proformaInvoiceItemReport = (JasperReport) JRLoader.loadObject(getClass().getResourceAsStream(ReportFiles.PROFORMA_INVOICE_ITEM_FILE));
            InputStream stream = getClass().getResourceAsStream(ReportFiles.PRO_INVOICE_FILE);
            
            
            reportHandler.reportParams.put("logo", ReportFiles.LOGO);
            
//            reportHandler.reportParams.put("deliveryTerm", deliveryTermReport);
//            reportHandler.reportParams.put("validation", validationReport);
//            reportHandler.reportParams.put("colours", coloursReport);
//            reportHandler.reportParams.put("receivedDocument", receiveDocumentReport);
            reportHandler.reportParams.put("proformaInvoiceItem", proformaInvoiceItemReport);
            
            JasperPrint jasperPrint = JasperFillManager.fillReport(stream, reportHandler.reportParams, dataSource);
            HttpServletResponse httpServletResponse = (HttpServletResponse)FacesContext.getCurrentInstance().getExternalContext().getResponse();
            httpServletResponse.setContentType("application/pdf");
            servletOutputStream = httpServletResponse.getOutputStream();
            JasperExportManager.exportReportToPdfStream(jasperPrint, servletOutputStream);
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
            
        if (appSession.getCurrentUser().getCompanyBranch().getTelephoneNo() != null)
        {
            proformaInvoiceDto.setTelephoneNo(appSession.getCurrentUser().getCompanyBranch().getTelephoneNo());
        }
        if (appSession.getCurrentUser().getCompanyBranch() != null)
        {
            proformaInvoiceDto.setBranchName(appSession.getCurrentUser().getCompanyBranch() + "");
        }
        if (appSession.getCurrentUser().getCompanyBranch().getGpsAddress() != null)
        {
            proformaInvoiceDto.setGpsAddress(appSession.getCurrentUser().getCompanyBranch().getGpsAddress());
        }
        if (appSession.getCurrentUser().getCompanyBranch().getCompanyProfile().getWebsite() != null)
        {
            proformaInvoiceDto.setWebsite(appSession.getCurrentUser().getCompanyBranch().getCompanyProfile().getWebsite());
        }
        if (appSession.getCurrentUser().getCompanyBranch().getCompanyProfile().getTinNo() != null)
        {
            proformaInvoiceDto.setTinNo(appSession.getCurrentUser().getCompanyBranch().getCompanyProfile().getTinNo());
        }
        if (appSession.getCurrentUser().getCompanyBranch().getCompanyProfile().getCompanyEmail() != null)
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
    
    public double getTotalAmount()
    {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount)
    {
        this.totalAmount = totalAmount;
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

    public DeliveryTermConfigItems getDeliveryTermConfigItems()
    {
        return deliveryTermConfigItems;
    }

    public void setDeliveryTermConfigItems(DeliveryTermConfigItems deliveryTermConfigItems)
    {
        this.deliveryTermConfigItems = deliveryTermConfigItems;
    }

    public ValidationConfigItems getValidationConfigItems()
    {
        return validationConfigItems;
    }

    public void setValidationConfigItems(ValidationConfigItems validationConfigItems)
    {
        this.validationConfigItems = validationConfigItems;
    }

    public ColoursConfigItems getColoursConfigItems()
    {
        return coloursConfigItems;
    }

    public void setColoursConfigItems(ColoursConfigItems coloursConfigItems)
    {
        this.coloursConfigItems = coloursConfigItems;
    }

    public ReceivedDocumentConfigItems getReceivedDocumentConfigItems()
    {
        return receivedDocumentConfigItems;
    }

    public void setReceivedDocumentConfigItems(ReceivedDocumentConfigItems receivedDocumentConfigItems)
    {
        this.receivedDocumentConfigItems = receivedDocumentConfigItems;
    }

    public List<DeliveryTermConfigItems> getDeliveryTermConfigItemsList()
    {
        return deliveryTermConfigItemsList;
    }

    public List<ValidationConfigItems> getValidationConfigItemsList()
    {
        return validationConfigItemsList;
    }

    public List<ColoursConfigItems> getColoursConfigItemsList()
    {
        return coloursConfigItemsList;
    }

    public List<ReceivedDocumentConfigItems> getReceivedDocumentConfigItemsList()
    {
        return receivedDocumentConfigItemsList;
    }

    public List<TaxScheme> getTaxSchemeList()
    {
        return taxSchemeList;
    }

    public double getTotalDiscount()
    {
        return totalDiscount;
    }
    
}
