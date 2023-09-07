/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.khoders.invoicemaster.entities.accounts;

import java.time.LocalDate;

/**
 *
 * @author Pascal
 */
public class CashTransfer {
    private LocalDate transferDate;
    private double totalAmount;
    private Account fromAccount;
    private Account toAccount;
    private String memo;
}
