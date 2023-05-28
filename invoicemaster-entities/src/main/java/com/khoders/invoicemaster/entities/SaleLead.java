/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.khoders.invoicemaster.entities;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 *
 * @author Pascal
 */
@Entity
@Table(name = "sales_lead")
public class SaleLead extends UserAccountRecord implements Serializable{
    @Column(name = "first_name")
    private String firstname;
    
    @Column(name = "surname")
    private String surname;
    
    @Column(name = "phone_number")
    private String phoneNumber;
    
    @Column(name = "email_address")
    private String emailAddress;
    
    @Column(name = "lead_code")
    private String leadCode;
    
    @Column(name = "house_address")
    private String houseAddress;
    
    @Column(name = "emergency_contact")
    private String emergencyContact;
    
    @Column(name = "rate")
    private double rate;

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getLeadCode() {
        return leadCode;
    }

    public void setLeadCode(String leadCode) {
        this.leadCode = leadCode;
    }

    public String getHouseAddress() {
        return houseAddress;
    }

    public void setHouseAddress(String houseAddress) {
        this.houseAddress = houseAddress;
    }

    public String getEmergencyContact() {
        return emergencyContact;
    }

    public void setEmergencyContact(String emergencyContact) {
        this.emergencyContact = emergencyContact;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    @Override
    public String toString() {
        return surname +"-"+leadCode+" ("+rate+"%)";
    }
}
