/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.khoders.invoicemaster.entites;

import com.khoders.resource.jpa.BaseModel;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 *
 * @author pascal
 */
@Entity
@Table(name = "user_account")
public class UserAccount extends BaseModel implements Serializable
{
    @Column(name = "email_address")
    private String emailAddress;
}
