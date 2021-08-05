/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.khoders.invoicemaster.entites;

import com.khoders.resource.utilities.SystemUtils;
import java.io.Serializable;
import javax.persistence.Column;

/**
 *
 * @author richa
 */
//@Entity
//@Table(name = "pos_printer")
public class PosPrinter extends UserAccountRecord implements Serializable
{
    @Column(name = "printer_id")
    private String printerId = SystemUtils.generateCode();
    
    @Column(name = "printer_name")
    private String printerName;

    public String getPrinterId()
    {
        return printerId;
    }

    public void setPrinterId(String printerId)
    {
        this.printerId = printerId;
    }

    public String getPrinterName()
    {
        return printerName;
    }

    public void setPrinterName(String printerName)
    {
        this.printerName = printerName;
    }
    
}
