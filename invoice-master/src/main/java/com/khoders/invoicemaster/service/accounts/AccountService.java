/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.khoders.invoicemaster.service.accounts;

import com.khoders.invoicemaster.dto.accounts.AccountDto;
import com.khoders.invoicemaster.entities.accounts.Account;
import com.khoders.invoicemaster.mapper.AccountMapper;
import com.khoders.resource.jpa.CrudApi;
import java.util.LinkedList;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Richard Narh
 */
@Stateless
public class AccountService {
    private static final Logger log = LoggerFactory.getLogger(AccountService.class);
    @Inject private AccountMapper mapper;
    @Inject private CrudApi crudApi;
    
    public AccountDto saveAccount(AccountDto accountDto) {
        AccountDto dto = null;
        Account account = mapper.toEntity(accountDto);
        if(crudApi.save(account) != null){
            dto = mapper.toDto(account);
        }
        return dto;
    }

    public AccountDto findById(String accountId) {
       Account account = crudApi.find(Account.class, accountId);
       return mapper.toDto(account);
    }

    public List<AccountDto> findAllAccounts() {
        log.debug("Fetching all accounts");
        List<Account> clientList = crudApi.findAll(Account.class);
        List<AccountDto> dtoList = new LinkedList<>();
        clientList.forEach(item -> {
            dtoList.add(mapper.toDto(item));
        });
        return dtoList;
    }

    public boolean delete(String accountId) {
        Account account = crudApi.find(Account.class, accountId);
        return account != null ? crudApi.delete(account) : false;
    }
    
}
