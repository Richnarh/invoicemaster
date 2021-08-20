/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.khoders.invoicemaster.entities.system;

import com.khoders.invoicemaster.entities.UserAccount;
import com.khoders.resource.jpa.BaseModel;
import com.khoders.resource.utilities.SystemUtils;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author richa
 */
@Entity
@Table(name = "user_permissions")
public class UserPermissions extends BaseModel implements Serializable
{
    @Column(name = "permission_id")
    private String permissionsId = SystemUtils.generateCode();
    
    @JoinColumn(name = "user_account", referencedColumnName = "id")
    @ManyToOne
    private UserAccount userAccount;
    
    @Column(name = "perm_delete")
    private boolean permDelete = false;
    
    @Column(name = "perm_update")
    private boolean permUpdate = false;
    
    @Column(name = "perm_save")
    private boolean permSave = true;
    
    @Column(name = "perm_print")
    private boolean permPrint = false;

    public String getPermissionsId()
    {
        return permissionsId;
    }

    public void setPermissionsId(String permissionsId)
    {
        this.permissionsId = permissionsId;
    }

    public UserAccount getUserAccount()
    {
        return userAccount;
    }

    public void setUserAccount(UserAccount userAccount)
    {
        this.userAccount = userAccount;
    }

    public boolean isPermDelete()
    {
        return permDelete;
    }

    public void setPermDelete(boolean permDelete)
    {
        this.permDelete = permDelete;
    }

    public boolean isPermUpdate()
    {
        return permUpdate;
    }

    public void setPermUpdate(boolean permUpdate)
    {
        this.permUpdate = permUpdate;
    }

    public boolean isPermSave()
    {
        return permSave;
    }

    public void setPermSave(boolean permSave)
    {
        this.permSave = permSave;
    }

    public boolean isPermPrint()
    {
        return permPrint;
    }

    public void setPermPrint(boolean permPrint)
    {
        this.permPrint = permPrint;
    }
    
}
