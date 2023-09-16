package com.khoders.invoicemaster.entities.accounts;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "bill_item")
public class BillItem extends BillInvoice {
    
    public static final String _bill = "bill";
    public static final String _billId = Bill._id;
    @JoinColumn(name = "bills", referencedColumnName = "id")
    @ManyToOne
    private Bill bill;

    public Bill getBill() {
        return bill;
    }

    public void setBill(Bill bill) {
        this.bill = bill;
    }
}
