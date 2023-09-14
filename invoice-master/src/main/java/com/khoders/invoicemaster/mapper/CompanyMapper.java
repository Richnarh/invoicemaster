/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.khoders.invoicemaster.mapper;

import com.khoders.invoicemaster.dto.AppConfigDto;
import com.khoders.invoicemaster.dto.CompanyBranchDto;
import com.khoders.invoicemaster.dto.CompanyProfileDto;
import com.khoders.invoicemaster.entities.AppConfig;
import com.khoders.invoicemaster.entities.system.CompanyBranch;
import com.khoders.invoicemaster.entities.system.CompanyProfile;
import com.khoders.resource.enums.Currency;
import com.khoders.resource.enums.Status;
import com.khoders.resource.exception.DataNotFoundException;
import com.khoders.resource.jpa.CrudApi;
import javax.inject.Inject;

/**
 *
 * @author Pascal
 */
public class CompanyMapper {
    @Inject private CrudApi crudApi;
    
    public CompanyBranch toEntity(CompanyBranchDto dto){
        CompanyBranch branch = new CompanyBranch();
        if (dto.getId() != null) {
            branch.setId(dto.getId());
        }
        branch.setBranchCode(dto.getBranchCode());
        branch.setBranchName(dto.getBranchName());
        if(dto.getCompanyProfileId() == null){
            throw new DataNotFoundException("Company profile cannot be null");
        }
        branch.setCompanyProfile(crudApi.find(CompanyProfile.class, dto.getCompanyProfileId()));
        branch.setGpsAddress(dto.getGpsAddress());
        branch.setTelephoneNo(dto.getTelephoneNo());
        return branch;
    }

    public CompanyBranchDto toDto(CompanyBranch cb){
        CompanyBranchDto dto = new CompanyBranchDto();
        if (cb.getId() == null) {
            return null;
        }
        dto.setId(cb.getId());
        dto.setCompanyProfile(cb.getCompanyProfile() != null ? cb.getCompanyProfile()+"" : null);
        dto.setCompanyProfileId(cb.getCompanyProfile() != null ? cb.getCompanyProfile().getId()+"" : null);
        dto.setBranchName(cb.getBranchName());
        dto.setBranchCode(cb.getBranchCode());
        dto.setGpsAddress(cb.getGpsAddress());
        dto.setTelephoneNo(cb.getTelephoneNo());
        return dto;
    }
    
    public CompanyProfile toEntity(CompanyProfileDto dto) {
        CompanyProfile profile = new CompanyProfile();
        if (dto.getId() != null) {
            profile.setId(dto.getId());
        }
        profile.setCompanyEmail(dto.getCompanyEmail());
        profile.setCurrency(Currency.valueOf(dto.getCurrency()));
        profile.setTinNo(dto.getTinNo());
        profile.setWebsite(dto.getWebsite());
        return profile;
    }
    
    public CompanyProfileDto toDto(CompanyProfile cb) {
        CompanyProfileDto dto = new CompanyProfileDto();
        if (cb.getId() != null) {
            dto.setId(cb.getId());
        }
        dto.setId(cb.getId());
        dto.setCompanyEmail(cb.getCompanyEmail());
        dto.setCurrency(cb.getCurrency().name());
        dto.setTinNo(cb.getTinNo());
        dto.setWebsite(cb.getWebsite());
        return dto;
    }
    
    public AppConfig toEntity(AppConfigDto dto) {
        AppConfig appConfig = new AppConfig();
        if (dto.getId() != null) {
            appConfig.setId(dto.getId());
        }
        appConfig.setConfigName(dto.getConfigName());
        appConfig.setConfigValue(dto.getConfigValue());
//        appConfig.setConfigStatus(dto.getConfigStatus() != null ? Status.valueOf(dto.getConfigStatus()) : Status.ACTIVE);
        return appConfig;
    }
    
    public AppConfigDto toDto(AppConfig appConfig) {
        AppConfigDto dto = new AppConfigDto();
        if (appConfig.getId() != null) {
            dto.setId(appConfig.getId());
        }
        dto.setId(appConfig.getId());
        dto.setConfigName(appConfig.getConfigName());
        dto.setConfigValue(appConfig.getConfigValue());
        dto.setConfigStatus(appConfig.getConfigStatus() != null ? appConfig.getConfigStatus().getLabel() : null);
        return dto;
    }
    
    
    
}
