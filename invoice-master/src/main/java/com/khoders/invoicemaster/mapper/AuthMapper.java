/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.khoders.invoicemaster.mapper;

import com.khoders.invoicemaster.entities.UserAccount;
import com.khoders.invoicemaster.payload.AuthResponse;
import com.khoders.invoicemaster.payload.UserAccountDto;
import com.khoders.resource.enums.AccessLevel;
import com.khoders.resource.jpa.CrudApi;
import com.khoders.resource.utilities.DateUtil;
import com.khoders.resource.utilities.Pattern;
import javax.inject.Inject;

/**
 *
 * @author Pascal
 */
public class AuthMapper {

    @Inject
    private CrudApi crudApi;

    public UserAccount toEntity(UserAccountDto dto) {
        UserAccount userAccount = new UserAccount();
        if (dto.getId() != null) {
            userAccount.setId(dto.getId());
        }
        userAccount.setEmail(dto.getEmailAddress());
        userAccount.setPhoneNumber(dto.getPhoneNumber());
        userAccount.setAccessLevel(AccessLevel.SUPER_USER);

        return userAccount;
    }

    public AuthResponse toDto(UserAccount userAccount) {
        System.out.println("creating response...");
        AuthResponse dto = new AuthResponse();
        if (userAccount.getId() == null) {
            return null;
        }
        dto.setEmail(userAccount.getEmail());
        dto.setSessionId(userAccount.getId());
        dto.setId(userAccount.getId());
        dto.setValueDate(DateUtil.parseLocalDateString(userAccount.getValueDate(), Pattern._ddMMyyyy));
        dto.setAccessLevel(userAccount.getAccessLevel().getLabel());
        dto.setFrame(userAccount.getFrame().getLabel());
        dto.setHeight(userAccount.getHeight().getLabel());
        dto.setWidth(userAccount.getWidth().getLabel());
        dto.setPhoneNumber(userAccount.getPhoneNumber());
        if(userAccount.getCompanyBranch() != null){
            dto.setCompanyBranch(userAccount.getCompanyBranch().getBranchName());
            dto.setCompanyBranchId(userAccount.getCompanyBranch().getId());
        }
        return dto;
    }
}
