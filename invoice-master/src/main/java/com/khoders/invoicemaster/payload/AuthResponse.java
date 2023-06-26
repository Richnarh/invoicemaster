/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.khoders.invoicemaster.payload;

import java.util.List;

/**
 *
 * @author richa
 */
public class AuthResponse extends BaseDto{
    private String sessionId;
    private String companyBranch;
    private String companyBranchId;
    private String userId;
    private String fullname;
    private String phoneNumber;
    private String email;
    private String status;
    private String accessLevel;
    private String frame;
    private String width ;
    private String height;
    private List<AppModuleDto> appModuleList;

    public String getSessionId()
    {
        return sessionId;
    }

    public void setSessionId(String sessionId)
    {
        this.sessionId = sessionId;
    }

    public List<AppModuleDto> getAppModuleList() {
        return appModuleList;
    }

    public void setAppModuleList(List<AppModuleDto> appModuleList) {
        this.appModuleList = appModuleList;
    }

    public String getCompanyBranch() {
        return companyBranch;
    }

    public void setCompanyBranch(String companyBranch) {
        this.companyBranch = companyBranch;
    }

    public String getCompanyBranchId() {
        return companyBranchId;
    }

    public void setCompanyBranchId(String companyBranchId) {
        this.companyBranchId = companyBranchId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAccessLevel() {
        return accessLevel;
    }

    public void setAccessLevel(String accessLevel) {
        this.accessLevel = accessLevel;
    }

    public String getFrame() {
        return frame;
    }

    public void setFrame(String frame) {
        this.frame = frame;
    }

    public String getWidth() {
        return width;
    }

    public void setWidth(String width) {
        this.width = width;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }
    
}
