/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.khoders.invoicemaster.sms;

import com.khoders.invoicemaster.entities.UserAccountRecord;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 *
 * @author richard
 * @richardnarh001@gmail.com
 * @ 6:34 AM March 10, 2023
 * I pledge to love my wife and Kids
 */
@Entity
@Table(name = "sms_access")
public class SmsAccess extends UserAccountRecord implements Serializable
{
    @Column(name = "username")
    private String username;
    
    @Column(name = "password")
    private String password;

    public String getUsername()
    {
        return username;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }
    
}
