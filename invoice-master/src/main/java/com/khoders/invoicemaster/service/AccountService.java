/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.khoders.invoicemaster.service;

import com.khoders.invoicemaster.dto.accounts.AccountDto;
import com.khoders.invoicemaster.dto.accounts.CashTransferDto;
import com.khoders.invoicemaster.dto.accounts.PettyCashDto;
import com.khoders.invoicemaster.entities.accounts.Account;
import com.khoders.invoicemaster.entities.accounts.CashTransfer;
import com.khoders.invoicemaster.entities.accounts.PettyCash;
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

    public boolean deleteAccount(String accountId) {
        Account account = crudApi.find(Account.class, accountId);
        return account != null ? crudApi.delete(account) : false;
    }
    
    public CashTransferDto saveCashTransfer(CashTransferDto transferDto){
        CashTransferDto dto = null;
        CashTransfer cashTransfer = mapper.toEntity(transferDto);
        if(crudApi.save(cashTransfer) != null){
            dto = mapper.toDto(cashTransfer);
        }
        return dto;
    }
    
    public List<CashTransferDto> findAllCashTransfers(){
        log.debug("Fetching all CashTransfer");
        List<CashTransfer> cashTransfers = crudApi.findAll(CashTransfer.class);
        List<CashTransferDto> dtoList = new LinkedList<>();
        cashTransfers.forEach(item -> {
            dtoList.add(mapper.toDto(item));
        });
        return dtoList;
    }
    
    public CashTransferDto findCashTransferById(String cashTransferId) {
       CashTransfer cashTransfer = crudApi.find(CashTransfer.class, cashTransferId);
       return mapper.toDto(cashTransfer);
    }
    
    public boolean deleteCashTransfer(String cashTransferId) {
        CashTransfer cashTransfer = crudApi.find(CashTransfer.class, cashTransferId);
        return cashTransfer != null ? crudApi.delete(cashTransfer) : false;
    }

    // Petty cash
    public PettyCashDto savePettyCash(PettyCashDto pettyCashDto) {
        PettyCashDto dto = null;
        PettyCash pettyCash = mapper.toEntity(pettyCashDto);
        if(crudApi.save(pettyCash) != null){
            dto = mapper.toDto(pettyCash);
        }
        return dto;
    }

    public PettyCashDto findPettyCashById(String pettyCashId) {
       PettyCash pettyCash = crudApi.find(PettyCash.class, pettyCashId);
       return mapper.toDto(pettyCash);
    }

    public List<PettyCashDto> findAllPettyCashList() {
        log.debug("Fetching all PettyCash");
        List<PettyCash> pettyCashList = crudApi.findAll(PettyCash.class);
        List<PettyCashDto> dtoList = new LinkedList<>();
        pettyCashList.forEach(item -> {
            dtoList.add(mapper.toDto(item));
        });
        return dtoList;
    }

    public boolean deletePettyCash(String pettyCashId) {
        PettyCash pettyCash = crudApi.find(PettyCash.class, pettyCashId);
        return pettyCash != null ? crudApi.delete(pettyCash) : false;
    }
}
