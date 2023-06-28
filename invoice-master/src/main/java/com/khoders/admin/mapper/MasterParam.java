/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.khoders.admin.mapper;

import javax.ws.rs.HeaderParam;

/**
 *
 * @author Pascal
 */
public class MasterParam {
    @HeaderParam("userAccountId")
    private String userAccountId;
    
    @HeaderParam("companyBranchId")
    private String companyBranchId;

    public String getUserAccountId() {
        return userAccountId;
    }

    public void setUserAccountId(String userAccountId) {
        this.userAccountId = userAccountId;
    }

    public String getCompanyBranchId() {
        return companyBranchId;
    }

    public void setCompanyBranchId(String companyBranchId) {
        this.companyBranchId = companyBranchId;
    }
    
}
