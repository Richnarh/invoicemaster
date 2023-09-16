/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.khoders.invoicemaster.service;

import com.khoders.invoicemaster.dto.TaxDto;
import com.khoders.invoicemaster.dto.TaxGroupDto;
import com.khoders.invoicemaster.entities.Tax;
import com.khoders.invoicemaster.entities.TaxGroup;
import com.khoders.invoicemaster.mapper.TaxMapper;
import com.khoders.resource.jpa.CrudApi;
import java.util.LinkedList;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Pascal
 */
@Stateless
public class TaxService {
    private static final Logger log = LoggerFactory.getLogger(TaxService.class);
    
    @Inject private CrudApi crudApi;
    @Inject private TaxMapper mapper;
    @Inject private ProformaInvoiceService invoiceService;

    public TaxGroupDto saveTaxGroup(TaxGroupDto groupDto) {
        TaxGroupDto dto = null;
        TaxGroup taxGroup = mapper.toEntity(groupDto);
        if(crudApi.save(taxGroup) != null){
            dto = mapper.toDto(taxGroup);
        }
        return dto;
    }

    public TaxGroupDto findTaxgroupById(String taxGroupId) {
        TaxGroup taxGroup = crudApi.find(TaxGroup.class, taxGroupId);
       return mapper.toDto(taxGroup);
    }

    public List<TaxGroupDto> findAllTaxGroups() {
        log.debug("Fetching all tax groups");
        List<TaxGroup> taxgroupList = invoiceService.getTaxGroupList();
        List<TaxGroupDto> dtoList = new LinkedList<>();
        taxgroupList.forEach(item -> {
            dtoList.add(mapper.toDto(item));
        });
        return dtoList;
    }

    public boolean deleteTaxGroup(String taxGroupId) {
       TaxGroup taxGroup = crudApi.find(TaxGroup.class, taxGroupId);
       return taxGroup != null ? crudApi.delete(taxGroup) : false;
    }
    
    public TaxDto saveTax(TaxDto taxDto){
        TaxDto dto = null;
        Tax tax = mapper.toEntity(taxDto);
        if(crudApi.save(tax) != null){
            dto = mapper.toDto(tax);
        }
        return dto;
    }
    
    public TaxDto findTaxById(String taxId) {
       Tax tax = crudApi.find(Tax.class, taxId);
       return mapper.toDto(tax);
    }
    
    public boolean deleteTax(String taxId) {
       Tax tax = crudApi.find(Tax.class, taxId);
       return tax != null ? crudApi.delete(tax) : false;
    }
    
    public List<TaxDto> findAllTaxes(String taxGroupId){
        log.info("taxGroupId: "+taxGroupId);
        TaxGroup taxGroup = crudApi.find(TaxGroup.class, taxGroupId);
        List<Tax> taxList = invoiceService.getTaxList(taxGroup);
        List<TaxDto> dtoList = new LinkedList<>();
        taxList.forEach(item -> {
            dtoList.add(mapper.toDto(item));
        });
        return dtoList;
    }
}
