/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.khoders.admin.mapper;

import javax.ws.rs.HeaderParam;
import javax.ws.rs.QueryParam;

/**
 *
 * @author Pascal
 */
public class AppParam {
    @HeaderParam("userAccountId")
    private String userAccountId;
    @HeaderParam("companyBranchId")
    private String companyBranchId;
    @QueryParam("paymentStatus")
    private String paymentStatus;
    @QueryParam("fromDate")
    private String fromDate;
    @QueryParam("toDate")
    private String toDate;

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

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public String getFromDate() {
        return fromDate;
    }

    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
    }

    public String getToDate() {
        return toDate;
    }

    public void setToDate(String toDate) {
        this.toDate = toDate;
    }
    
}
