/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.khoders.im.admin.jbeans;

import com.khoders.im.admin.services.CompanyService;
import com.khoders.invoicemaster.entities.AppConfig;
import com.khoders.resource.jpa.CrudApi;
import com.khoders.resource.utilities.CollectionList;
import com.khoders.resource.utilities.Msg;
import com.khoders.resource.utilities.SystemUtils;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author Pascal
 */
@Named(value="appConfigController")
@SessionScoped
public class AppConfigController implements Serializable{
    @Inject private CrudApi crudApi;
    @Inject private CompanyService companyService;
    private List<AppConfig> appConfigList = new LinkedList<>();
    private AppConfig appConfig = new AppConfig();
    
    private boolean disable;
    
    @PostConstruct
    private void init(){
        clearConfig();
        appConfigList = companyService.getConfigList();
    }
    
    public void saveConfig(){
        if(crudApi.save(appConfig) != null){
            appConfigList = CollectionList.washList(appConfigList, appConfig);
            Msg.info("App config saved successfully!");
        }
        clearConfig();
    }
    
    public void editAppConfig(AppConfig appConfig){
        this.appConfig = appConfig;
        disable = true;
    }
    
    public void deleteAppConfig(AppConfig appConfig){
        try {
            if(crudApi.delete(appConfig)){
                Msg.info("App config deleted successfully!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void clearConfig(){
        this.appConfig = new AppConfig();
        disable = false;
        SystemUtils.resetJsfUI();
    }

    public AppConfig getAppConfig() {
        return appConfig;
    }

    public void setAppConfig(AppConfig appConfig) {
        this.appConfig = appConfig;
    }

    public List<AppConfig> getAppConfigList() {
        return appConfigList;
    }

    public boolean isDisable() {
        return disable;
    }

    public void setDisable(boolean disable) {
        this.disable = disable;
    }
    
}
