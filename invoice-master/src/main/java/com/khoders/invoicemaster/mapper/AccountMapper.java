/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.khoders.invoicemaster.mapper;

import com.khoders.invoicemaster.dto.accounts.AccountDto;
import com.khoders.invoicemaster.dto.accounts.CashTransferDto;
import com.khoders.invoicemaster.entities.accounts.Account;
import com.khoders.invoicemaster.entities.accounts.CashTransfer;
import com.khoders.resource.exception.DataNotFoundException;
import com.khoders.resource.jpa.CrudApi;
import com.khoders.resource.utilities.DateUtil;
import com.khoders.resource.utilities.Pattern;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author Richard Narh
 */
@Stateless
public class AccountMapper {
    @Inject private CrudApi crudApi;
    
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
    
    public CashTransfer toEntity(CashTransferDto dto){
        CashTransfer ct = new CashTransfer();
        if(dto.getId() != null){
            ct.setId(dto.getId());
        }
        if(dto.getFromAccountId() == null){
            throw new DataNotFoundException("From Account Id is required.");
        }
        if(dto.getToAccountId() == null){
            throw new DataNotFoundException("To Account Id is required.");
        }
        Account from = crudApi.find(Account.class, dto.getFromAccountId());
        Account to = crudApi.find(Account.class, dto.getToAccountId());
        ct.setFromAccount(from);
        ct.setToAccount(to);
        ct.setMemo(dto.getMemo());
        ct.setTransferDate(DateUtil.parseLocalDate(dto.getTransferDate(), Pattern._yyyyMMdd));
        ct.setTotalAmount(dto.getTotalAmount());
        return ct;
    }
    
    public CashTransferDto toDto(CashTransfer ct){
        CashTransferDto dto = new CashTransferDto();
        if(ct.getId() == null)return null;
        dto.setId(ct.getId());
        if(ct.getFromAccount() != null){
            dto.setFromAccount(ct.getFromAccount().getAccountName());
            dto.setFromAccountId(ct.getFromAccount().getId());
        }
        if(ct.getToAccount()!= null){
            dto.setFromAccount(ct.getToAccount().getAccountName());
            dto.setFromAccountId(ct.getToAccount().getId());
        }
        dto.setMemo(ct.getMemo());
        dto.setTotalAmount(ct.getTotalAmount());
        dto.setTransferDate(DateUtil.parseLocalDateString(ct.getTransferDate(), Pattern.ddMMyyyy));
        return dto;
    }
}
