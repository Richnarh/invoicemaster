/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.khoders.invoicemaster.entites;

import com.khoders.resource.jpa.BaseModel;
import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author pascal
 */
@Entity
@Table(name = "received_doc_config_items")
public class ReceivedDocumentConfigItems extends BaseModel implements Serializable
{
    @JoinColumn(name = "proforma_invoice", referencedColumnName = "id")
    @ManyToOne
    private ProformaInvoice proformaInvoice;
    
    @JoinColumn(name = "received_document", referencedColumnName = "id")
    @ManyToOne
    private ReceivedDocument receivedDocument;

    public ProformaInvoice getProformaInvoice()
    {
        return proformaInvoice;
    }

    public void setProformaInvoice(ProformaInvoice proformaInvoice)
    {
        this.proformaInvoice = proformaInvoice;
    }
    
    public ReceivedDocument getReceivedDocument() {
        return receivedDocument;
    }

    public void setReceivedDocument(ReceivedDocument receivedDocument) {
        this.receivedDocument = receivedDocument;
    }
    
    
}
