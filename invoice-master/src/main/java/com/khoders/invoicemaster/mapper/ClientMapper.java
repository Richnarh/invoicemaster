/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.khoders.invoicemaster.mapper;

import com.khoders.invoicemaster.dto.ClientDto;
import com.khoders.invoicemaster.entities.Client;
import com.khoders.invoicemaster.enums.ClientType;
import com.khoders.invoicemaster.service.AppService;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author Pascal
 */
@Stateless
public class ClientMapper {
    @Inject private AppService as;
    
    public Client toEntity(ClientDto dto, AppParam param) {
        Client client = new Client();
        if (dto.getId() != null){
            client.setId(dto.getId());
        }
        client.setAddress(dto.getAddress());
        client.setBusinessName(dto.getBusinessName());
        client.setClientCode(dto.getClientCode());
        client.setClientName(dto.getClientName());
        client.setClientType(ClientType.valueOf(dto.getClientType()));
        client.setEmailAddress(dto.getEmailAddress());
        client.setPhone(dto.getPhone());
        client.setUserAccount(as.getUser(param.getUserAccountId()));
        client.setCompanyBranch(as.getBranch(param.getCompanyBranchId()));
        return client;
    }

    public ClientDto toDto(Client client) {
        ClientDto dto = new ClientDto();
        if (client.getId() == null) return null;
        dto.setId(client.getId());
        dto.setAddress(client.getAddress());
        dto.setBusinessName(client.getBusinessName());
        dto.setClientCode(client.getClientCode());
        dto.setClientName(client.getClientName());
        dto.setClientType(client.getClientType() != null ? client.getClientType().getLabel() : null);
        dto.setEmailAddress(client.getEmailAddress());
        dto.setPhone(client.getPhone());
        return dto;
    }
    
}
