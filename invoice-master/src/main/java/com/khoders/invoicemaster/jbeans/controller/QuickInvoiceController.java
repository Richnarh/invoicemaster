/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.khoders.invoicemaster.jbeans.controller;

import com.khoders.invoicemaster.dto.SalesTaxDto;
import com.khoders.invoicemaster.entities.ProformaInvoiceItem;
import com.khoders.invoicemaster.entities.Tax;
import com.khoders.invoicemaster.enums.AppVersion;
import com.khoders.invoicemaster.listener.AppSession;
import com.khoders.invoicemaster.service.ProformaInvoiceService;
import com.khoders.resource.utilities.CollectionList;
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
@Named(value = "quickInvoiceController")
@SessionScoped
public class QuickInvoiceController implements Serializable
{
    @Inject
    private ProformaInvoiceService proformaInvoiceService;
    @Inject
    private AppSession appSession;

    private ProformaInvoiceItem proformaInvoiceItem = new ProformaInvoiceItem();

    private List<ProformaInvoiceItem> proformaInvoiceItemList = new LinkedList<>();
    private List<ProformaInvoiceItem> removedProformaInvoiceItemList = new LinkedList<>();
    private List<Tax> taxList = new LinkedList<>();
    List<SalesTaxDto> salesTaxDtoList = new LinkedList<>();
    
    @PostConstruct
    void init()
    {
      taxList = proformaInvoiceService.getTaxList();
    }

    private double subTotal, totalSaleAmount, calculatedDiscount, installationFee, taxAmount, totalPayable, invoiceAmount, productDiscountRate;
    
    public void inventoryProperties()
    {
        if(proformaInvoiceItem.getInventory().getSellingPrice() != 0.0)
        {
          proformaInvoiceItem.setUnitPrice(proformaInvoiceItem.getInventory().getSellingPrice());
        }
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
                
              }
              else
                {
                   FacesContext.getCurrentInstance().addMessage(null, 
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, Msg.setMsg("Proforma Invoice item removed!"), null));
                }
            proformaInvoiceItem = new ProformaInvoiceItem();
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public void saveAll()
    {
        totalSaleAmount = proformaInvoiceItemList.stream().mapToDouble(ProformaInvoiceItem::getSubTotal).sum();
        try
        {

            if (productDiscountRate > 0.0)
            {
//                if (productDiscountRate > 5.0)
//                {
//                    Msg.error("Please dicount above 5% is not allowed!");
//                    return;
//                }
                calculatedDiscount = totalSaleAmount * (productDiscountRate / 100); // Calculating Discount on total Amount
                double newTotalAmount = totalSaleAmount - calculatedDiscount;

                setTotalSaleAmount(newTotalAmount); // updating the sales amount with the new totalAmount
                setSubTotal(totalSaleAmount);
            } else
            {
                setSubTotal(totalSaleAmount);
            }
            taxCalculation();

            Msg.info("Sales Processing complete!");
            
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public void taxCalculation()
    {
        salesTaxDtoList = new LinkedList<>();
                
        for (Tax tax : taxList)
        {
            // v2 exclude the NHIL tax in sales calculation
            if(appSession.getCurrentUser().getAppVersion().equals(AppVersion.V2)){
                if(tax.getTaxName().equals("NHIL")) continue;
            }
            SalesTaxDto dto = new SalesTaxDto();

            double calc = getTotalSaleAmount() * (tax.getTaxRate() / 100);

            dto.setTaxName(tax.getTaxName());
            dto.setTaxRate(tax.getTaxRate());
            dto.setTaxAmount(calc);
            dto.setReOrder(tax.getReOrder());

            salesTaxDtoList.add(dto);
        }

        calculateVat();
    }

    private void calculateVat()
    {
        SalesTaxDto covid19 = salesTaxDtoList.get(0);
        SalesTaxDto salesVat = salesTaxDtoList.get(1);
        double totalLevies = covid19.getTaxAmount();
        double taxableValue = getTotalSaleAmount() + totalLevies;
        double vat = taxableValue * (salesVat.getTaxRate() / 100);
        totalPayable = vat + taxableValue + installationFee;
        salesVat.setTaxAmount(vat);
    }
    
    public void deleteProformaInvoiceItem(ProformaInvoiceItem proformaInvoiceItem)
    {
        removedProformaInvoiceItemList = CollectionList.washList(removedProformaInvoiceItemList, proformaInvoiceItem);
        proformaInvoiceItemList.remove(proformaInvoiceItem);
    }
    
    public void clearProformaInvoiceItem()
    {
        proformaInvoiceItem = new ProformaInvoiceItem();
        proformaInvoiceItemList = new LinkedList<>();
        totalPayable = 0.0;
        totalSaleAmount = 0.0;
        installationFee = 0.0;
        productDiscountRate = 0.0;
        subTotal = 0.0;
        salesTaxDtoList = new LinkedList<>();
        SystemUtils.resetJsfUI();
    }
    public double getSubTotal()
    {
        return subTotal;
    }

    public void setSubTotal(double subTotal)
    {
        this.subTotal = subTotal;
    }

    public double getTotalSaleAmount()
    {
        return totalSaleAmount;
    }

    public void setTotalSaleAmount(double totalSaleAmount)
    {
        this.totalSaleAmount = totalSaleAmount;
    }

    public double getCalculatedDiscount()
    {
        return calculatedDiscount;
    }

    public void setCalculatedDiscount(double calculatedDiscount)
    {
        this.calculatedDiscount = calculatedDiscount;
    }

    public double getInstallationFee()
    {
        return installationFee;
    }

    public void setInstallationFee(double installationFee)
    {
        this.installationFee = installationFee;
    }

    public double getTaxAmount()
    {
        return taxAmount;
    }

    public void setTaxAmount(double taxAmount)
    {
        this.taxAmount = taxAmount;
    }

    public double getTotalPayable()
    {
        return totalPayable;
    }

    public void setTotalPayable(double totalPayable)
    {
        this.totalPayable = totalPayable;
    }

    public double getInvoiceAmount()
    {
        return invoiceAmount;
    }

    public void setInvoiceAmount(double invoiceAmount)
    {
        this.invoiceAmount = invoiceAmount;
    }

    public double getProductDiscountRate()
    {
        return productDiscountRate;
    }

    public void setProductDiscountRate(double productDiscountRate)
    {
        this.productDiscountRate = productDiscountRate;
    }

    public ProformaInvoiceItem getProformaInvoiceItem()
    {
        return proformaInvoiceItem;
    }

    public void setProformaInvoiceItem(ProformaInvoiceItem proformaInvoiceItem)
    {
        this.proformaInvoiceItem = proformaInvoiceItem;
    }

    public List<ProformaInvoiceItem> getProformaInvoiceItemList()
    {
        return proformaInvoiceItemList;
    }

    public List<SalesTaxDto> getSalesTaxDtoList()
    {
        return salesTaxDtoList;
    }

}
