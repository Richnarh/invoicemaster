/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.khoders.invoicemaster.service;

import com.khoders.invoicemaster.dto.TaxDto;
import com.khoders.invoicemaster.entities.SalesTax;
import com.khoders.invoicemaster.entities.Tax;
import com.khoders.invoicemaster.entities.UserTransaction;
import com.khoders.invoicemaster.jbeans.controller.ProformaInvoiceController;
import com.khoders.resource.jpa.CrudApi;
import com.khoders.restapi.mapper.TransactionMapper;
import java.util.LinkedList;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author richa
 */
@Stateless
public class TaxService
{
    @Inject private CrudApi crudApi;
    @Inject private ProformaInvoiceService proformaInvoiceService;
    @Inject private TransactionMapper transactionMapper;
    
    private List<SalesTax> salesTaxList = new LinkedList<>();
    private List<Tax> taxList = new LinkedList<>();
    
    public void taxCalculation(UserTransaction userTransaction)
    {
        salesTaxList = proformaInvoiceService.getSalesTaxList(userTransaction);
        taxList = proformaInvoiceService.getTaxList();
        
        salesTaxList.forEach(salesTax ->
        {
            crudApi.delete(salesTax);
        });
        for (Tax tax : taxList)
        {
            SalesTax salesTax = new SalesTax();
            
            double calc = userTransaction.getTotalAmount() * (tax.getTaxRate()/100);

            salesTax.genCode();
            salesTax.setTaxName(tax.getTaxName());
            salesTax.setTaxRate(tax.getTaxRate());
            salesTax.setTaxAmount(calc);
            salesTax.setReOrder(tax.getReOrder());
            salesTax.setUserTransaction(userTransaction);

            crudApi.save(salesTax);
            
            salesTaxList = proformaInvoiceService.getSalesTaxList(userTransaction);
        }
        
        calculateVat(userTransaction);
    }
          
    private void calculateVat(UserTransaction userTransaction)
    {
        double installationFee=0,totalPayable=0;
        
        if(!salesTaxList.isEmpty())
        {
            SalesTax nhil = salesTaxList.get(0);
//            SalesTax getFund = salesTaxList.get(1);
            SalesTax covid19 = salesTaxList.get(1);
            SalesTax salesVat = salesTaxList.get(2);

            double totalLevies = nhil.getTaxAmount()+covid19.getTaxAmount();

            double taxableValue = userTransaction.getTotalAmount() + totalLevies;
            
            double vat = taxableValue*(salesVat.getTaxRate()/100);
            
            totalPayable = vat + taxableValue + installationFee;
            
            salesVat.setTaxAmount(vat);

            crudApi.save(salesVat);
        }
    }

}
