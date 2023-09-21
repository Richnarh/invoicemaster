/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.khoders.invoicemaster.mapper;

import com.khoders.invoicemaster.DefaultService;
import com.khoders.invoicemaster.dto.accounts.AccountDto;
import com.khoders.invoicemaster.dto.accounts.CashDepositeDto;
import com.khoders.invoicemaster.dto.accounts.CashTransferDto;
import com.khoders.invoicemaster.dto.accounts.PettyCashDto;
import com.khoders.invoicemaster.entities.Client;
import com.khoders.invoicemaster.entities.accounts.Account;
import com.khoders.invoicemaster.entities.accounts.CashDeposite;
import com.khoders.invoicemaster.entities.accounts.CashTransfer;
import com.khoders.invoicemaster.entities.accounts.PettyCash;
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
    @Inject private DefaultService ds;
    
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

    public PettyCash toEntity(PettyCashDto dto) {
        PettyCash cash = new PettyCash();
        if(dto.getId() != null){
            cash.setId(dto.getId());
        }
        cash.setFundBalance(dto.getFundBalance());
        cash.setFundDate(DateUtil.parseLocalDate(dto.getFundDate(), Pattern._yyyyMMdd));
        cash.setFundName(dto.getFundName());
        cash.setFundPurpose(dto.getFundPurpose());
        cash.setMoneyUsed(dto.getMoneyUsed());
        if(dto.getPettyAccountId() == null){
            throw new DataNotFoundException("Petty AccountId is required");
        }
        Account account = crudApi.find(Account.class, dto.getPettyAccountId());
        if(account != null){
            cash.setPettyAccount(account);
        }
        cash.setTotalAmount(dto.getTotalAmount());
        return cash;
    }

    public PettyCashDto toDto(PettyCash cash) {
        PettyCashDto dto = new PettyCashDto();
        if(cash.getId() == null) return null;
        dto.setFundBalance(cash.getFundBalance());
        dto.setFundDate(DateUtil.parseLocalDateString(cash.getFundDate(), Pattern.ddMMyyyy));
        dto.setFundName(cash.getFundName());
        dto.setFundPurpose(cash.getFundPurpose());
        dto.setMoneyUsed(cash.getMoneyUsed());
        if(cash.getPettyAccount() != null){
            dto.setPettyAccount(cash.getPettyAccount().getAccountName());
            dto.setPettyAccountId(cash.getPettyAccount().getId());
        }
        dto.setTotalAmount(cash.getTotalAmount());
        return dto;
    }

    public CashDeposite toEntity(CashDepositeDto dto) {
        CashDeposite cd = new CashDeposite();
        if(dto.getId() != null){
            cd.setId(dto.getId());
        }
        if(dto.getReceiptNo() == null){
            throw new RuntimeException("Receipt number is required.");
        }
        if(dto.getClientId()== null){
            throw new DataNotFoundException("ClientId is required");
        }
        if(dto.getDepositeAccountId() == null){
            throw new DataNotFoundException("DepositeAccountId is required");
        }
        Client client = crudApi.find(Client.class, dto.getClientId());
        if(client != null){
           cd.setClient(client); 
        }
        Account account = crudApi.find(Account.class, dto.getDepositeAccountId());
        if(account != null){
            cd.setDepositeAccount(account);
        }
        cd.setDepositeDate(DateUtil.parseLocalDate(dto.getDepositeDate(), Pattern._yyyyMMdd));
        cd.setReceiptNo(dto.getReceiptNo());
        cd.setMemo(dto.getMemo());
        return cd;
    }
    
    public CashDepositeDto toDto(CashDeposite cd) {
       CashDepositeDto dto = new CashDepositeDto();
        if(dto.getId() != null){
            cd.setId(dto.getId());
        }
        if(cd.getClient() != null){
            dto.setClient(cd.getClient()+"");
            dto.setClientId(cd.getClient().getId());
        }
        if(cd.getDepositeAccount() != null){
            dto.setDepositeAccount(cd.getDepositeAccount()+"");
            dto.setDepositeAccountId(cd.getDepositeAccount().getId());
        }
        dto.setDepositeDate(DateUtil.parseLocalDateString(cd.getDepositeDate(), Pattern.ddMMyyyy));
        dto.setReceiptNo(cd.getReceiptNo());
        dto.setMemo(cd.getMemo());
        return dto;
    }

}
