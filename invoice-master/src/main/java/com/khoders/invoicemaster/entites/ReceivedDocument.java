/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.khoders.invoicemaster.entites;

import com.khoders.resource.jpa.BaseModel;
import com.khoders.resource.utilities.SystemUtils;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 *
 * @author pascal
 */
@Entity
@Table(name = "received_document")
public class ReceivedDocument extends BaseModel implements Serializable 
{
    @Column(name = "document_code")
    private String documentCode;

    @Column(name = "document_name")
    private String documentName;

    public String getDocumentCode() {
        return documentCode;
    }

    public void setDocumentCode(String documentCode) {
        this.documentCode = documentCode;
    }

    public String getDocumentName() {
        return documentName;
    }

    public void setDocumentName(String documentName) {
        this.documentName = documentName;
    }
    
    public void genCode()
    {
        if (getDocumentCode() != null)
        {
            setDocumentCode(getDocumentCode());
        } else
        {
            setDocumentCode(SystemUtils.generateCode());
        }
    }

    @Override
    public String toString() {
        return documentName;
    }
    
    
}
