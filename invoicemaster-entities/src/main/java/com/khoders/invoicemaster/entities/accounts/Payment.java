package com.khoders.invoicemaster.entities.accounts;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "payments")
public class Payment extends BillInvoicePayment {
    public static final String _bill = "bill";
    public static final String _billId = Bill._id;
    @JoinColumn(name = "bills", referencedColumnName = "id")
    @ManyToOne
    private Bill bill;

    public static final String _invoice = "invoice";
    public static final String _invoiceId = Invoice._id;
    @JoinColumn(name = "invoice", referencedColumnName = "id")
    @ManyToOne
    private Invoice invoice;

    public Bill getBill() {
        return bill;
    }

    public void setBill(Bill bill) {
        this.bill = bill;
    }

    public Invoice getInvoice() {
        return invoice;
    }

    public void setInvoice(Invoice invoice) {
        this.invoice = invoice;
    }
}
