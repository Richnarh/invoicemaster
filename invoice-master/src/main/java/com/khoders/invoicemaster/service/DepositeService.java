package com.khoders.invoicemaster.service;

import com.khoders.invoicemaster.dto.accounts.CashDepositeDto;
import com.khoders.invoicemaster.entities.accounts.CashDeposite;
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
public class DepositeService {
    private static final Logger log = LoggerFactory.getLogger(DepositeService.class);
    @Inject private CrudApi crudApi;
    @Inject private AccountMapper mapper;

    public Object findAllCashDepositeList() {
        log.debug("Fetching all PettyCash");
        List<CashDeposite> cashDepositeList = crudApi.findAll(CashDeposite.class);
        List<CashDepositeDto> dtoList = new LinkedList<>();
        cashDepositeList.forEach(item -> {
            dtoList.add(mapper.toDto(item));
        });
        return dtoList;
    }

    public CashDepositeDto findCashDepositeById(String cashDepositeId) {
       CashDeposite cashDeposite = crudApi.find(CashDeposite.class, cashDepositeId);
       return mapper.toDto(cashDeposite);
    }

    public CashDepositeDto saveCashDeposite(CashDepositeDto cashDepositeDto) {
        CashDepositeDto dto = null;
        CashDeposite cashDeposite = mapper.toEntity(cashDepositeDto);
        if(crudApi.save(cashDeposite) != null){
            dto = mapper.toDto(cashDeposite);
        }
        return dto;
    }
    
    public boolean deleteCashDeposite(String cashDepositeId) {
        CashDeposite cashDeposite = crudApi.find(CashDeposite.class, cashDepositeId);
        return cashDeposite != null ? crudApi.delete(cashDeposite) : false;
    }
}
