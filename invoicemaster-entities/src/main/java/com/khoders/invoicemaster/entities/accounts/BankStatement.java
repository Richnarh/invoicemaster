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
public class BankStatement {
    private Account bankAccount;
    private LocalDate stmtDate;
    private String description;
    private double credit;
    private double debit;
    private double balance;
    private String referenceAccount;
    private String referenceParty;
    private String product;
}
