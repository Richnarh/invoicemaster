/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.khoders.admin.services;

import com.khoders.invoicemaster.dto.AppConfigDto;
import com.khoders.admin.mapper.CompanyMapper;
import com.khoders.invoicemaster.entities.AppConfig;
import com.khoders.resource.jpa.CrudApi;
import java.util.LinkedList;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author Pascal
 */
@Stateless
public class AppConfigService {
    @Inject private CrudApi crudApi;
    @Inject private CompanyService companyService;
    @Inject private CompanyMapper mapper;
    
    public AppConfigDto save(AppConfigDto dto){
        AppConfigDto configDto = null;
        AppConfig appConfig = mapper.toEntity(dto);
        if(appConfig != null && crudApi.save(appConfig) != null){
            configDto = mapper.toDto(appConfig);
        }
        return configDto;
    }
    
    public boolean delete(String configId){
        AppConfig appConfig = crudApi.find(AppConfig.class, configId);
        return appConfig != null ? crudApi.delete(appConfig) : false;
    }

    public List<AppConfigDto> fetchAllConfigs() {
        List<AppConfig> appConfigs = companyService.getConfigList();
        List<AppConfigDto> dtoList = new LinkedList<>();
        appConfigs.forEach(config -> {
            dtoList.add(mapper.toDto(config));
        });
        return dtoList;
    }

    public AppConfigDto findById(String configId) {
        AppConfig appConfig = crudApi.find(AppConfig.class, configId);
        return mapper.toDto(appConfig);
    }
}
