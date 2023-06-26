/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.khoders.invoicemaster.payload;

import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Pascal
 */
public class AppModuleDto {
    private String moduleId;
    private String moduleName;
    private List<UserPageDto> userPageData = new LinkedList<>();

    public String getModuleId() {
        return moduleId;
    }

    public void setModuleId(String moduleId) {
        this.moduleId = moduleId;
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public List<UserPageDto> getUserPageData() {
        return userPageData;
    }

    public void setUserPageData(List<UserPageDto> userPageData) {
        this.userPageData = userPageData;
    }
    
}
