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
public class PettyCash {
    private LocalDate date;
    private String fundName;
    private Account pettyAccount;
    private String fundPurpose;
    private double totalAmount;
    private double fundBalance;
    private double moneyUsed;
}
