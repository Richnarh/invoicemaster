/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.khoders.invoicemaster.mapper;

import com.khoders.invoicemaster.dto.SaleLeadDto;
import com.khoders.invoicemaster.dto.UserDto;
import com.khoders.invoicemaster.entities.SaleLead;
import com.khoders.invoicemaster.entities.UserAccount;
import com.khoders.invoicemaster.entities.system.CompanyBranch;
import com.khoders.invoicemaster.enums.AppVersion;
import com.khoders.invoicemaster.service.AppService;
import com.khoders.resource.enums.AccessLevel;
import com.khoders.resource.enums.Status;
import com.khoders.resource.enums.UnitOfMeasurement;
import com.khoders.resource.exception.DataNotFoundException;
import com.khoders.resource.jpa.CrudApi;
import com.khoders.resource.utilities.DateUtil;
import com.khoders.resource.utilities.Pattern;
import javax.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Pascal
 */
public class UserMapper {
    private static final Logger log = LoggerFactory.getLogger(UserMapper.class);
    @Inject private CrudApi crudApi;
    @Inject private AppService as;
    
    public UserAccount toEntity(UserDto dto){
        UserAccount userAccount = new UserAccount();
        if(dto.getId() != null){
            userAccount.setId(dto.getId());
        }
        userAccount.setAccessLevel(AccessLevel.valueOf(dto.getAccessLevel()));
        userAccount.setEmail(dto.getEmail());
        userAccount.setFullname(dto.getFullname());
        userAccount.setAppVersion(AppVersion.valueOf(dto.getAppVersion()));
        userAccount.setFrame(UnitOfMeasurement.valueOf(dto.getFrame()));
        userAccount.setHeight(UnitOfMeasurement.valueOf(dto.getHeight()));
        userAccount.setWidth(UnitOfMeasurement.valueOf(dto.getWidth()));
        userAccount.setPhoneNumber(dto.getPhoneNumber());
        userAccount.setStatus(Status.valueOf(dto.getStatus()));
        if(dto.getCompanyBranchId() == null){
            throw new DataNotFoundException("Branch Id is required");
        }
        CompanyBranch branch = crudApi.find(CompanyBranch.class, dto.getCompanyBranchId());
        userAccount.setCompanyBranch(branch);
        
        return userAccount;
    } 
    
    public UserDto toDto(UserAccount userAccount){
        UserDto dto = new UserDto();
        if(userAccount.getId() == null) return null;
        dto.setId(userAccount.getId());
        dto.setAccessLevel(userAccount.getAccessLevel().getLabel());
        dto.setEmail(userAccount.getEmail());
        dto.setFullname(userAccount.getFullname());
        dto.setValueDate(DateUtil.parseLocalDateString(userAccount.getValueDate(), Pattern._ddMMyyyy));
        dto.setAppVersion(userAccount.getAppVersion() != null ? userAccount.getAppVersion().getLabel() : null);
        dto.setFrame(userAccount.getFrame().getLabel());
        dto.setHeight(userAccount.getHeight().getLabel());
        dto.setWidth(userAccount.getWidth().getLabel());
        dto.setPhoneNumber(userAccount.getPhoneNumber());
        dto.setStatus(userAccount.getStatus().getLabel());
        if(userAccount.getCompanyBranch() != null){
            dto.setCompanyBranch(userAccount.getCompanyBranch().getBranchName());
            dto.setCompanyBranchId(userAccount.getCompanyBranch().getId());
        }
        return dto;
    }
    
    public SaleLead toParam(AppParam param){
        SaleLead saleLead = new SaleLead();
        UserAccount userAccount = as.getUser(param.getUserAccountId());
        CompanyBranch companyBranch = as.getBranch(param.getCompanyBranchId());
        saleLead.setUserAccount(userAccount);
        saleLead.setCompanyBranch(companyBranch);
        return saleLead;
    }
    
    public SaleLead toEntity(SaleLeadDto leadDto, AppParam param){
        SaleLead lead = toParam(param);
        if(leadDto.getId() != null){
            lead.setId(leadDto.getId());
        }
        lead.setEmailAddress(leadDto.getEmailAddress());
        lead.setEmergencyContact(leadDto.getEmergencyContact());
        lead.setHouseAddress(leadDto.getHouseAddress());
        lead.setLeadCode(leadDto.getLeadCode());
        lead.setPhoneNumber(leadDto.getPhoneNumber());
        lead.setRate(leadDto.getRate());
        lead.setFirstname(leadDto.getFirstname());
        lead.setSurname(leadDto.getSurname());
        return lead;
    }

    public SaleLeadDto toDto(SaleLead lead) {
        SaleLeadDto dto = new SaleLeadDto();
        if(lead.getId() == null) return null;
        dto.setId(lead.getId());
        dto.setEmailAddress(lead.getEmailAddress());
        dto.setEmergencyContact(lead.getEmergencyContact());
        dto.setHouseAddress(lead.getHouseAddress());
        dto.setLeadCode(lead.getLeadCode());
        dto.setPhoneNumber(lead.getPhoneNumber());
        dto.setRate(lead.getRate());
        dto.setFirstname(lead.getFirstname());
        dto.setSurname(lead.getSurname());
        return dto;
    }
}
