/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.khoders.invoicemaster.jbeans.controller;

import com.khoders.invoicemaster.entities.Inventory;
import com.khoders.invoicemaster.entities.PurchaseOrder;
import com.khoders.invoicemaster.entities.PurchaseOrderItem;
import com.khoders.invoicemaster.listener.AppSession;
import com.khoders.invoicemaster.service.InventoryService;
import com.khoders.resource.jpa.CrudApi;
import com.khoders.resource.utilities.CollectionList;
import com.khoders.resource.utilities.FormView;
import com.khoders.resource.utilities.Msg;
import com.khoders.resource.utilities.SystemUtils;
import java.io.Serializable;
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
 * @author richa
 */
@Named(value = "purchaseOrderController")
@SessionScoped
public class PurchaseOrderController implements Serializable
{
    @Inject private CrudApi crudApi;
    @Inject private AppSession appSession;
    @Inject private InventoryService inventoryService;
    
    private PurchaseOrder purchaseOrder = new PurchaseOrder();
    private List<PurchaseOrder> purchaseOrderList = new LinkedList<>();
    
    private PurchaseOrderItem purchaseOrderItem = new PurchaseOrderItem();
    private List<PurchaseOrderItem> purchaseOrderItemList = new LinkedList<>();
    private List<PurchaseOrderItem> removedOrderItemList = new LinkedList<>();
    
    private FormView pageView = FormView.listForm();
    private String optionText;
    
     private double totalAmount = 0.0;
    
    @PostConstruct
    private void init()
    {
        purchaseOrderList = inventoryService.getPurchaseOrderList();
        
        clearPurchaseOrder();
    }
    
    public void initPurchaseOrder()
    {
        clearPurchaseOrder();
        pageView.restToCreateView();
    }
    
    public void savePurchaseOrder()
    {
        try
        {
           if(crudApi.save(purchaseOrder)!=null)
           {
               purchaseOrderList = CollectionList.washList(purchaseOrderList, purchaseOrder);
               FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_INFO, Msg.setMsg("Purchase order saved"), null)); 
               
           }
           
           clearPurchaseOrder();
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    
  public void deletePurchaseOrder(PurchaseOrder purchaseOrder)
    {
        try 
        {
          if(crudApi.delete(purchaseOrder))
          {
              purchaseOrderList.remove(purchaseOrder);
              
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
    public void managePurchaseOrderItem(PurchaseOrder purchaseOrder)
    {
        this.purchaseOrder = purchaseOrder;
        pageView.restToDetailView();
        totalAmount = 0.0;
        clearPurchaseOrderItem();
        
        purchaseOrderItemList = inventoryService.getPurchaseOrderItem(purchaseOrder);
              
        for (PurchaseOrderItem items : purchaseOrderItemList) 
        {
            totalAmount += (items.getTotaltQty() * items.getUnitPrice());
        }
       
    }

    public void addPurchaseOrderItem()
    {
        try
        {
            if (purchaseOrderItem.getTotaltQty() <= 0)
            {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, Msg.setMsg("Please enter quantity"), null));
                return;
            }
            
            if (purchaseOrderItem.getUnitPrice() <= 0.0) {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, Msg.setMsg("Please enter price"), null));
                return;
            }

            if (purchaseOrderItem != null) {
               
                totalAmount += purchaseOrderItem.getTotaltQty() * purchaseOrderItem.getUnitPrice();
                
                purchaseOrderItem.genCode();
                purchaseOrderItemList.add(purchaseOrderItem);
                purchaseOrderItemList = CollectionList.washList(purchaseOrderItemList, purchaseOrderItem);

                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, Msg.setMsg("Order item added"), null));
            } else {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, Msg.setMsg("Order item removed!"), null));
            }
            clearPurchaseOrderItem();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void saveAll()
    {
        try 
        {
            for (PurchaseOrderItem orderItem : purchaseOrderItemList)
            {

//                if (totalAmount != purchaseOrder.getTotalAmount())
//                {
//                    FacesContext.getCurrentInstance().addMessage(null,
//                            new FacesMessage(FacesMessage.SEVERITY_ERROR, Msg.setMsg("The item total sum: " + (totalAmount) + " is not equivalent to the purchase order total: " + purchaseOrder.getTotalAmount()), null));
//                        return;
//                }
                crudApi.save(orderItem);

            }

            for (PurchaseOrderItem orderItem : removedOrderItemList)
            {
                crudApi.delete(orderItem);
                removedOrderItemList.remove(orderItem);
            }
            System.out.println("Remove order size after -- " + removedOrderItemList.size());
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, Msg.setMsg("Purchase order item list saved!"), null));

        } catch (Exception e) 
        {
            e.printStackTrace();
        }
    }
    
    public void editPurchaseOrderItem(PurchaseOrderItem purchaseOrderItem)
    {
        this.purchaseOrderItem = purchaseOrderItem;
        totalAmount -= (purchaseOrderItem.getTotaltQty() * purchaseOrderItem.getUnitPrice());
        purchaseOrderItemList.remove(purchaseOrderItem);
    }
    
    public void removePurchaseOrderItem(PurchaseOrderItem purchaseOrderItem)
    {
        totalAmount -= (purchaseOrderItem.getTotaltQty() * purchaseOrderItem.getUnitPrice());
        removedOrderItemList = CollectionList.washList(removedOrderItemList, purchaseOrderItem);
        purchaseOrderItemList.remove(purchaseOrderItem);
        
        System.out.println("Size on removing --- "+removedOrderItemList.size());
    }
    
    public void postToInventory(PurchaseOrder purchaseOrder)
    {
        Inventory inventory = null;
        
        try
        {
             List<PurchaseOrderItem> orderItemList = inventoryService.getPurchaseOrderItem(purchaseOrder);
             
             if(orderItemList.isEmpty())
             {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, Msg.setMsg("Please add order items before posting!."), null));  
                 return; 
             }
             
             double itemCost = 0.0;
             
            for (PurchaseOrderItem items : orderItemList)
            {
                itemCost += (items.getTotaltQty() * items.getUnitPrice());
            }
            
//             if(purchaseOrder.getTotalAmount() != itemCost)
//             {
//                 FacesContext.getCurrentInstance().addMessage(null,
//                        new FacesMessage(FacesMessage.SEVERITY_ERROR, Msg.setMsg("The total cost of items is different from the entered total amount."), null));  
//                 return; 
//             }
             
            for (PurchaseOrderItem item : orderItemList)
            {
                inventory = new Inventory();
                inventory.setInventoryCode(purchaseOrder.getPurchaseOrderCode());
                inventory.setProduct(item.getProduct());
                inventory.setDescription(purchaseOrder.getDescription());
                inventory.setFrameSize(item.getFrameSize());
                inventory.setWidth(item.getWidth());
                inventory.setHeight(item.getHeight());
                inventory.setQuantity(item.getTotaltQty());
                inventory.setSellingPrice(item.getSellingPrice());
                inventory.setUnitPrice(item.getUnitPrice());
                inventory.setUserAccount(appSession.getCurrentUser());

                crudApi.save(inventory);
            
            }
            
        if(inventory != null)
        {
            purchaseOrder = crudApi.getEm().find(PurchaseOrder.class, purchaseOrder.getId());
            purchaseOrder.setPostedToInventory(true);
            crudApi.save(purchaseOrder);
            
            init();
            
           FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_INFO, Msg.setMsg("Purchase order posted to inventory successfully!"), null));  
           
        }
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    
    public void editPurchaseOrder(PurchaseOrder purchaseOrder)
    {
       pageView.restToCreateView();
       this.purchaseOrder=purchaseOrder;
       optionText = "Update";
    }
    
    public void clearPurchaseOrder() 
    {
        purchaseOrder = new PurchaseOrder();
        purchaseOrder.setUserAccount(appSession.getCurrentUser());
        optionText = "Save Changes";
        SystemUtils.resetJsfUI();
    }
    
    public void closePage()
    {
       purchaseOrder = new PurchaseOrder();
       optionText = "Save Changes";
       pageView.restToListView();
    }
    public void clearPurchaseOrderItem()
    {
        purchaseOrderItem = new PurchaseOrderItem();
        optionText = "Save Changes";
        purchaseOrderItem.setPurchaseOrder(purchaseOrder);
        purchaseOrderItem.setUserAccount(appSession.getCurrentUser());
        SystemUtils.resetJsfUI();
    }
    
    public PurchaseOrder getPurchaseOrder()
    {
        return purchaseOrder;
    }

    public void setPurchaseOrder(PurchaseOrder purchaseOrder)
    {
        this.purchaseOrder = purchaseOrder;
    }

    public PurchaseOrderItem getPurchaseOrderItem()
    {
        return purchaseOrderItem;
    }

    public void setPurchaseOrderItem(PurchaseOrderItem purchaseOrderItem)
    {
        this.purchaseOrderItem = purchaseOrderItem;
    }

    public List<PurchaseOrder> getPurchaseOrderList()
    {
        return purchaseOrderList;
    }

    public List<PurchaseOrderItem> getPurchaseOrderItemList()
    {
        return purchaseOrderItemList;
    }

    public FormView getPageView()
    {
        return pageView;
    }

    public void setPageView(FormView pageView)
    {
        this.pageView = pageView;
    }

    public String getOptionText()
    {
        return optionText;
    }

    public void setOptionText(String optionText)
    {
        this.optionText = optionText;
    }

    public double getTotalAmount()
    {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount)
    {
        this.totalAmount = totalAmount;
    }
    
    
}
