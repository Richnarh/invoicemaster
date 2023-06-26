/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.khoders.invoicemaster.service;

import com.khoders.invoicemaster.entities.UserAccount;
import com.khoders.invoicemaster.mapper.AuthMapper;
import com.khoders.invoicemaster.payload.AuthRequest;
import com.khoders.invoicemaster.payload.AuthResponse;
import com.khoders.resource.jpa.CrudApi;
import static com.khoders.resource.utilities.SecurityUtil.hashText;
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
    @Inject private AuthMapper authMapper;
    
    public AuthResponse doLogin(AuthRequest authRequest) {
        log.info("Initializing login...");
        UserAccount userAccount = login(authRequest);
        if (userAccount != null) {
            return authMapper.toDto(userAccount);
        }
        return null;
    }

    public UserAccount login(AuthRequest authRequest) {
        return crudApi.getEm().createQuery("SELECT e FROM UserAccount e WHERE e.email= :email AND e.password=:password", UserAccount.class)
                    .setParameter(UserAccount._email, authRequest.getEmailAddress())
                    .setParameter(UserAccount._password, hashText(authRequest.getPassword()))
                    .getResultStream().findFirst().orElse(null);
    }

}
