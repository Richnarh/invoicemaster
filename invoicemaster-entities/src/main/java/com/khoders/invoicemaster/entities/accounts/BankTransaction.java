package com.khoders.invoicemaster.entities.accounts;

import com.khoders.resource.jpa.BaseModel;

import javax.persistence.*;

@Entity
@Table(name = "bank_transaction")
public class BankTransaction extends BaseModel {
    @JoinColumn(name = "account", referencedColumnName = "id")
    @ManyToOne
    private Account account;

    @Column(name = "amount")
    private double amount;

}
