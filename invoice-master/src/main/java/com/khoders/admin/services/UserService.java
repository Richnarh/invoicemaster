/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.khoders.admin.services;

import com.khoders.invoicemaster.dto.SaleLeadDto;
import com.khoders.admin.mapper.AppParam;
import com.khoders.invoicemaster.dto.UserDto;
import com.khoders.invoicemaster.mapper.UserMapper;
import com.khoders.invoicemaster.dto.AuthRequest;
import com.khoders.invoicemaster.entities.SaleLead;
import com.khoders.invoicemaster.entities.SalesLog;
import com.khoders.invoicemaster.entities.UserAccount;
import com.khoders.invoicemaster.service.AppService;
import com.khoders.resource.jpa.CrudApi;
import static com.khoders.resource.utilities.SecurityUtil.hashText;
import java.util.LinkedList;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author Pascal
 */
@Stateless
public class UserService {
    @Inject private CrudApi crudApi;
    @Inject private CompanyService companyService;
    @Inject private AppService appService;
    @Inject private UserMapper mapper;
    
     public UserDto save(UserDto dto){
         UserAccount userAccount = mapper.toEntity(dto);
         UserAccount account = crudApi.save(userAccount);
         return mapper.toDto(account);
     }
     
    public UserDto findById(String userAccountId) {
        UserAccount account = appService.getUser(userAccountId);
        return mapper.toDto(account);
    }

    public List<UserDto> fetchAllUsers() {
        List<UserAccount> userAccounts = companyService.getUserAccountList();
        List<UserDto> dtoList = new LinkedList<>();
        userAccounts.forEach(user -> {
            dtoList.add(mapper.toDto(user));
        });
        return dtoList;
    }
    
    public boolean deleteUser(String userAccountId) {
        UserAccount userAccount = appService.getUser(userAccountId);
        return userAccount != null && crudApi.delete(userAccount);
    }

    public String updatePassword(String userAccountId,String password) {
        UserAccount userAccount = appService.getUser(userAccountId);
        userAccount.setPassword(hashText(password));
        if(crudApi.save(userAccount) != null){
            return userAccount.getFullname() +"'s password is updated";
        }
        return null;
    }

    public UserDto loginUser(String userAccountId) {
        UserAccount userAccount = appService.getUser(userAccountId);
        AuthRequest authRequest = new AuthRequest(userAccount.getEmail(), userAccount.getPassword());
        userAccount = companyService.login(authRequest);
        if (userAccount != null) {
            return mapper.toDto(userAccount);
        }
        return null;
    }
    
    public SaleLeadDto saveLead(SaleLeadDto leadDto, AppParam param){
        SaleLead lead = mapper.toEntity(leadDto, param);
        SaleLead saleLead  = crudApi.save(lead);
        return mapper.toDto(saleLead);
    }

    public SaleLeadDto findbyId(String saleLeadId) {
        SaleLead lead = crudApi.find(SaleLead.class, saleLeadId);
        return mapper.toDto(lead);
    }

    public boolean delete(String saleLeadId) {
        SaleLead lead = crudApi.find(SaleLead.class, saleLeadId);
        return lead != null ? crudApi.delete(lead) : false;
    }
}
