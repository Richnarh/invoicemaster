/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.khoders.invoicemaster.service;

import com.khoders.invoicemaster.mapper.AppParam;
import com.khoders.invoicemaster.dto.ClientDto;
import com.khoders.invoicemaster.entities.Client;
import com.khoders.invoicemaster.mapper.ClientMapper;
import com.khoders.resource.jpa.CrudApi;
import com.khoders.resource.jpa.QueryBuilder;
import java.util.LinkedList;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author richa
 */
@Stateless
public class ClientService{
    
    private static final Logger log = LoggerFactory.getLogger(ClientService.class);
    
    @Inject private QueryBuilder builder;
    @Inject private CrudApi crudApi;
    @Inject private InventoryService inventoryService;
    @Inject private ClientMapper mapper;
    
    public ClientDto save(ClientDto clientDto, AppParam param) {
       Client client = mapper.toEntity(clientDto, param);
       ClientDto dto = null;
       if(crudApi.save(client) != null){
           dto = mapper.toDto(client);
       }
       return dto;
    }

    public ClientDto findById(String clientId) {
       Client invoice = inventoryService.getClientById(clientId);
       return mapper.toDto(invoice);
    }

    public List<ClientDto> findAll() {
        log.debug("Fetching all Client");
        List<Client> clientList = inventoryService.getClientList();
        List<ClientDto> dtoList = new LinkedList<>();
        clientList.forEach(item -> {
            dtoList.add(mapper.toDto(item));
        });
        return dtoList;
    }

    public boolean delete(String clientId) {
        Client client = inventoryService.getClientById(clientId);
        return client != null ? crudApi.delete(client) : false;
    }
}
