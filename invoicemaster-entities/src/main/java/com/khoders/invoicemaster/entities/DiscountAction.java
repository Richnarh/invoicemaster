/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.khoders.invoicemaster.entities;

import com.khoders.invoicemaster.enums.ActionType;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

/**
 *
 * @author Pascal
 */
@Entity
@Table(name = "discount_action")
public class DiscountAction extends UserAccountRecord implements Serializable{
    @Column(name = "action_type")
    @Enumerated(EnumType.STRING)
    private ActionType actionType = ActionType.ENABLE_ON_EDIT;

    public ActionType getActionType() {
        return actionType;
    }

    public void setActionType(ActionType actionType) {
        this.actionType = actionType;
    }
    
}
