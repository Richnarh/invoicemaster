/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.khoders.invoicemaster.mapper;

import com.khoders.invoicemaster.dto.accounts.AccountDto;
import com.khoders.invoicemaster.entities.accounts.Account;
import javax.ejb.Stateless;

/**
 *
 * @author Richard Narh
 */
@Stateless
public class AccountMapper {
    
    public Account toEntity(AccountDto dto){
        Account account = new Account();
        if(dto.getId() != null){
            account.setId(dto.getId());
        }
        account.setAccountCode(dto.getAccountCode());
        account.setAccountName(dto.getAccountName());
        account.setAccountType(dto.getAccountType());
        account.setDescription(dto.getDescription());
        account.setParentAccount(dto.getParentAccount());
        account.setSubAccount(dto.isSubAccount());
        return account;
    }
    
    public AccountDto toDto(Account account){
        AccountDto dto = new AccountDto();
        if(dto.getId() == null)return null;
        dto.setId(account.getId());
        dto.setAccountCode(account.getAccountCode());
        dto.setAccountName(account.getAccountName());
        dto.setAccountType(account.getAccountType());
        dto.setDescription(account.getDescription());
        dto.setParentAccount(account.getParentAccount());
        dto.setSubAccount(account.isSubAccount());
        return dto;
    }
}
