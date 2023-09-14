package com.khoders.invoicemaster.entities.accounts;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "invoice_item")
public class InvoiceItem extends BillInvoice {
    public static final String _invoice = "invoice";
    public static final String _invoiceId = Invoice._id;
    @JoinColumn(name = "invoice", referencedColumnName = "id")
    @ManyToOne
    private Invoice invoice;

    public Invoice getInvoice() {
        return invoice;
    }

    public void setInvoice(Invoice invoice) {
        this.invoice = invoice;
    }
}
