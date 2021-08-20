/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.khoders.invoicemaster.jbeans.controller;

import com.khoders.invoicemaster.entities.ProductType;
import com.khoders.invoicemaster.entities.PurchaseOrder;
import com.khoders.invoicemaster.entities.PurchaseOrderItem;
import com.khoders.invoicemaster.listener.AppSession;
import com.khoders.resource.excel.FileExtension;
import com.khoders.resource.jpa.CrudApi;
import com.khoders.resource.utilities.Msg;
import com.khoders.resource.utilities.NumberUtil;
import com.khoders.resource.utilities.SystemUtils;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.primefaces.model.file.UploadedFile;

/**
 *
 * @author khoders
 */
@Named(value = "purchaseOrderUploadController")
@SessionScoped
public class PurchaseOrderUploadController implements Serializable
{
    @Inject private CrudApi crudApi;
    @Inject private AppSession appSession;
    
    private PurchaseOrderItem purchaseOrderItem;
    private PurchaseOrder purchaseOrder = new PurchaseOrder();
    private List<PurchaseOrderItem> purchaseOrderItemList = new LinkedList<>();
    private UploadedFile file = null;
    
    public String getFileExtension(String filename) {
        if(filename == null)
        {
            return null;
        }
        return filename.substring(filename.lastIndexOf(".") + 1, filename.length());
    }
    
    public void uploadOrder()
    {
        if(file.getSize() < 1)
        {
            FacesContext.getCurrentInstance().addMessage(null, 
                new FacesMessage(FacesMessage.SEVERITY_ERROR, Msg.setMsg("No excel file is selected!"), null));
            return;
        }
        try
        {
               String extension = getFileExtension(file.getFileName());
               
               System.out.println("type ==> "+extension);
               
//               if(!extension.equals(FileExtension.xls.name()) || !extension.equals(FileExtension.xlsx.name()))
//               {
//                 FacesContext.getCurrentInstance().addMessage(null, 
//                new FacesMessage(FacesMessage.SEVERITY_ERROR, Msg.setMsg("File not supported!"), null));  
//                 return;
//               }
                
                InputStream inputStream = file.getInputStream();
                XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
                XSSFSheet  sheet =  workbook.getSheetAt(0);
                
                sheet.removeRow(sheet.getRow(0));
                
                Iterator<Row> iterator = sheet.iterator();
                System.out.println("Starting....");
                while(iterator.hasNext())
                {
                  purchaseOrderItem = new PurchaseOrderItem();
                  System.out.println("initializing....");
                  Row currentRow = iterator.next();
                  
                      System.out.println("going through cells....");
                      purchaseOrderItem.setOrderItemCode(NumberUtil.objToString(currentRow.getCell(0)));
                      purchaseOrderItem.setWings(NumberUtil.objToString(currentRow.getCell(1)));
                      purchaseOrderItem.setHeight(NumberUtil.objToInteger(currentRow.getCell(2)));
                      purchaseOrderItem.setWidth(NumberUtil.objToInteger(currentRow.getCell(3)));
                      purchaseOrderItem.setModel(NumberUtil.objToString(currentRow.getCell(4)));
                      purchaseOrderItem.setAccessories(NumberUtil.objToString(currentRow.getCell(5)));
                      purchaseOrderItem.setRightQty(NumberUtil.objToInteger(currentRow.getCell(6)));
                      purchaseOrderItem.setLeftQty(NumberUtil.objToInteger(currentRow.getCell(7)));
                      purchaseOrderItem.setTotaltQty(NumberUtil.objToInteger(currentRow.getCell(8)));
                      purchaseOrderItem.setUnitPrice(NumberUtil.objToInteger(currentRow.getCell(9)));
                      purchaseOrderItem.setTotalPrice(NumberUtil.objToDouble(currentRow.getCell(10)));
//                      purchaseOrderItem.setFrameSize(NumberUtil.objToInteger(currentRow.getCell(7)));
                     
                      purchaseOrderItem.setSellingPrice(NumberUtil.objToDouble(currentRow.getCell(11)));
                      
                      String prdtType = NumberUtil.objToString(currentRow.getCell(12));
                      
                      ProductType productType = crudApi.getEm().createQuery("SELECT e FROM ProductType e WHERE e.productTypeName=?1", ProductType.class)
                                            .setParameter(1, prdtType)
                                            .getResultStream().findFirst().orElse(null);
                      
                      if(productType != null)
                      {
                        purchaseOrderItem.setProductType(productType);
                        continue;
                      }
                      
//                      if(purchaseOrder == null)
//                      {
//                        purchaseOrderItem.setPurchaseOrder(purchaseOrder);
//                        return;
//                      }
                      
                      purchaseOrderItemList.add(purchaseOrderItem);
                          
                      
                      System.out.println("Done!!!");
                      System.out.println("Size => "+purchaseOrderItemList.size());
                }
                workbook.close();
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }
    
    public void saveUpload()
    {
        try
        {
            if(crudApi.save(purchaseOrder) != null)
            {
               purchaseOrderItemList.forEach(order ->{
                order.genCode();
                order.setUserAccount(appSession.getCurrentUser());
                order.setPurchaseOrder(purchaseOrder);
                crudApi.save(order);
            }); 
            FacesContext.getCurrentInstance().addMessage(null, 
                new FacesMessage(FacesMessage.SEVERITY_ERROR, Msg.setMsg("File upload successful!"), null));   
            }
            
            
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public void clear()
    {
        purchaseOrderItemList = new LinkedList<>();
        file = null;
        purchaseOrder = new PurchaseOrder();
        SystemUtils.resetJsfUI();
    }
        
    public PurchaseOrderItem getPurchaseOrderItem()
    {
        return purchaseOrderItem;
    }

    public void setPurchaseOrderItem(PurchaseOrderItem purchaseOrderItem)
    {
        this.purchaseOrderItem = purchaseOrderItem;
    }

    public PurchaseOrder getPurchaseOrder()
    {
        return purchaseOrder;
    }

    public void setPurchaseOrder(PurchaseOrder purchaseOrder)
    {
        this.purchaseOrder = purchaseOrder;
    }

    public UploadedFile getFile()
    {
        return file;
    }

    public void setFile(UploadedFile file)
    {
        this.file = file;
    }

    public List<PurchaseOrderItem> getPurchaseOrderItemList()
    {
        return purchaseOrderItemList;
    }
}
