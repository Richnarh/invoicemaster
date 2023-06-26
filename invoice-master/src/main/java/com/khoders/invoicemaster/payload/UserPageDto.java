/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.khoders.invoicemaster.payload;

/**
 *
 * @author Pascal
 */
public class UserPageDto {
    private String pageId;
    private String pageName;
    private boolean userActivePage;
    
    public String getPageId() {
        return pageId;
    }

    public void setPageId(String pageId) {
        this.pageId = pageId;
    }

    public String getPageName() {
        return pageName;
    }

    public void setPageName(String pageName) {
        this.pageName = pageName;
    }

    public boolean isUserActivePage() {
        return userActivePage;
    }

    public void setUserActivePage(boolean userActivePage) {
        this.userActivePage = userActivePage;
    }
     
}
