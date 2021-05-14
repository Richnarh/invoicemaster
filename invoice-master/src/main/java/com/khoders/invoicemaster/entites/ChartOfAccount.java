/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.khoders.invoicemaster.entites;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 *
 * @author richa
 */
@Entity
@Table(name = "chart_of_account")
public class ChartOfAccount extends UserAccountRecord implements Serializable
{
    @Column(name = "account_code")
    private String accountCode;
    
    @Column(name = "account_name")
    private String accountName;
    
    @Column(name = "account_type")
    private String accountType;
}
