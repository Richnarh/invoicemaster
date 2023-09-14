/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.khoders.invoicemaster.entities.accounts;

import com.khoders.invoicemaster.entities.Client;
import java.time.LocalDate;

/**
 *
 * @author Pascal
 */
public class CashDeposite {
    private Account depositeAccount;
    private LocalDate depositeDate;
    private String receiptNo;
    private Client client;
    private String memo;
}
