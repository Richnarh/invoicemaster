/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.khoders.invoicemaster.entities;

import com.khoders.resource.enums.Status;
import com.khoders.resource.jpa.BaseModel;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Lob;
import javax.persistence.Table;

/**
 *
 * @author Pascal
 */
@Entity
@Table(name = "tax_group")
public class TaxGroup extends BaseModel implements Serializable{
    
    public static final String _groupName = "groupName";
    @Column(name = "group_name")
    private String groupName;
    
    public static final String _groupStatus = "groupStatus";
    @Column(name = "group_status")
    @Enumerated(EnumType.STRING)
    private Status groupStatus;
    
    @Column(name = "group_description")
    @Lob
    private String groupDescription;

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getGroupDescription() {
        return groupDescription;
    }

    public void setGroupDescription(String groupDescription) {
        this.groupDescription = groupDescription;
    }

    public Status getGroupStatus() {
        return groupStatus;
    }

    public void setGroupStatus(Status groupStatus) {
        this.groupStatus = groupStatus;
    }
    
}
