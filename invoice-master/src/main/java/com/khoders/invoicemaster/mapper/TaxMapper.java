/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.khoders.invoicemaster.mapper;

import com.khoders.invoicemaster.dto.TaxDto;
import com.khoders.invoicemaster.dto.TaxGroupDto;
import com.khoders.invoicemaster.entities.Tax;
import com.khoders.invoicemaster.entities.TaxGroup;
import com.khoders.resource.exception.DataNotFoundException;
import com.khoders.resource.jpa.CrudApi;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author Richard Narh
 */
@Stateless
public class TaxMapper {
    @Inject private CrudApi crudApi;
    
    public TaxGroup toEntity(TaxGroupDto dto){
        TaxGroup taxGroup = new TaxGroup();
        if (dto.getId() != null){
            taxGroup.setId(dto.getId());
        }
        taxGroup.setGroupDescription(dto.getGroupDescription());
        taxGroup.setGroupName(dto.getGroupName());
        taxGroup.setGroupStatus(dto.getGroupStatus());
        return taxGroup;
    }
    
    public TaxGroupDto toDto(TaxGroup taxGroup){
        TaxGroupDto dto = new TaxGroupDto();
        if (taxGroup == null){
            return null;
        }
        dto.setId(taxGroup.getId());
        dto.setGroupDescription(taxGroup.getGroupDescription());
        dto.setGroupName(taxGroup.getGroupName());
        dto.setGroupStatus(taxGroup.getGroupStatus());
        return dto;
    }
    
    public Tax toEntity(TaxDto dto){
        Tax tax = new Tax();
        if(dto.getId() != null){
            tax.setId(dto.getId());
        }
        tax.setReOrder(dto.getReOrder());
        tax.setTaxId(dto.getTaxId());
        tax.setTaxName(dto.getTaxName());
        tax.setTaxRate(dto.getTaxRate());
        if(dto.getTaxGroupId() == null){
            throw new DataNotFoundException("taxGroupId cannot be null");
        }
        TaxGroup taxGroup = crudApi.find(TaxGroup.class, dto.getTaxGroupId());
        tax.setTaxGroup(taxGroup);
        return tax;
    }
    
    public TaxDto toDto(Tax tax){
        TaxDto dto = new TaxDto();
        if (tax == null){
            return null;
        }
        tax.setId(tax.getId());
        dto.setReOrder(tax.getReOrder());
        dto.setTaxId(tax.getTaxId());
        dto.setTaxName(tax.getTaxName());
        dto.setTaxRate(tax.getTaxRate());
        return dto;
    }
}
