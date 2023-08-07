/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.khoders.invoicemaster.service;

import com.khoders.admin.services.CompanyService;
import com.khoders.invoicemaster.entities.UserAccount;
import com.khoders.invoicemaster.dto.AuthRequest;
import com.khoders.invoicemaster.dto.UserDto;
import com.khoders.invoicemaster.mapper.UserMapper;
import com.khoders.resource.jpa.CrudApi;
import javax.ejb.Stateless;
import javax.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Pascal
 */
@Stateless
public class AuthService {
    private static final Logger log = LoggerFactory.getLogger(AuthService.class);
    
    @Inject private CrudApi crudApi;
    @Inject private UserMapper mapper;
    @Inject private CompanyService companyService;
    
    public UserDto doLogin(AuthRequest authRequest) {
        log.info("Initialising login...");
        UserAccount userAccount = companyService.login(authRequest);
        if (userAccount != null) {
            return mapper.toDto(userAccount);
        }
        return null;
    }
}
