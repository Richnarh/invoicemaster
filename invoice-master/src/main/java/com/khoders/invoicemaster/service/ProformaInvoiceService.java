/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.khoders.invoicemaster.service;

import com.khoders.invoicemaster.DefaultService;
import com.khoders.invoicemaster.entities.DiscountAction;
import com.khoders.invoicemaster.entities.Inventory;
import com.khoders.invoicemaster.entities.PaymentData;
import com.khoders.invoicemaster.entities.ProformaInvoice;
import com.khoders.invoicemaster.entities.ProformaInvoiceItem;
import com.khoders.invoicemaster.entities.SalesTax;
import com.khoders.invoicemaster.entities.Tax;
import com.khoders.invoicemaster.entities.TaxGroup;
import com.khoders.invoicemaster.entities.UserAccount;
import com.khoders.invoicemaster.entities.system.CompanyBranch;
import com.khoders.invoicemaster.enums.DeliveryStatus;
import com.khoders.invoicemaster.enums.InvoiceStatus;
import com.khoders.invoicemaster.listener.AppSession;
import com.khoders.resource.jpa.CrudApi;
import com.khoders.resource.utilities.DateRangeUtil;
import com.khoders.resource.utilities.DateUtil;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Properties;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.persistence.TypedQuery;

/**
 *
 * @author pascal
 */
@Stateless
public class ProformaInvoiceService{
    @Inject private AppSession appSession;
    @Inject private CrudApi crudApi;
    @Inject private DefaultService ds;

    public DiscountAction getDiscountAction() {
        try
        {
            String qryString = "SELECT e FROM DiscountAction e ";
            return crudApi.getEm().createQuery(qryString, DiscountAction.class).getResultStream().findFirst().orElse(null);
            
        } catch (Exception e)
        {
            e.printStackTrace();
        }

        return null;
    }
        
    public List<ProformaInvoiceItem> getProformaInvoiceItemList(ProformaInvoice proformaInvoice)
    {
        try
        {
          String query = "SELECT e FROM ProformaInvoiceItem e WHERE e.proformaInvoice = :proformaInvoice";
        
        TypedQuery<ProformaInvoiceItem> typedQuery = crudApi.getEm().createQuery(query, ProformaInvoiceItem.class)
                                .setParameter(ProformaInvoiceItem._proformaInvoice, proformaInvoice);
                return typedQuery.getResultList();      
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }
    public List<SalesTax> getSalesTaxList(ProformaInvoice proformaInvoice){
        return crudApi.getEm().createQuery("SELECT e FROM SalesTax e WHERE e.proformaInvoice = :proformaInvoice ORDER BY e.reOrder ASC", SalesTax.class)
                                .setParameter(SalesTax._proformaInvoice, proformaInvoice)
                                .getResultList();   
    }

    public List<ProformaInvoice> getProformaInvoiceList()
    {
        try
        {
            String qryString = "SELECT e FROM ProformaInvoice e WHERE e.companyBranch = :companyBranch AND e.valueDate = :valueDate ORDER BY e.issuedDate DESC";
            TypedQuery<ProformaInvoice> typedQuery = crudApi.getEm().createQuery(qryString, ProformaInvoice.class)
                                        .setParameter(ProformaInvoice._companyBranch, appSession.getCompanyBranch())
                                        .setParameter(ProformaInvoice._valueDate, LocalDate.now());
                         return typedQuery.getResultList();
                         
        } catch (Exception e)
        {
            e.printStackTrace();
        }

        return Collections.emptyList();
    }
    public List<ProformaInvoice> getProformaInvoiceList(CompanyBranch companyBranch){
       String qryString = "SELECT e FROM ProformaInvoice e WHERE e.companyBranch = :companyBranch AND e.valueDate = :valueDate ORDER BY e.issuedDate DESC";
            TypedQuery<ProformaInvoice> typedQuery = crudApi.getEm().createQuery(qryString, ProformaInvoice.class)
                                        .setParameter(ProformaInvoice._companyBranch, companyBranch)
                                        .setParameter(ProformaInvoice._valueDate, LocalDate.now());
                         return typedQuery.getResultList();
    }
    
    public List<ProformaInvoice> getProformaInvoice(DateRangeUtil dateRange){
        try{
            String limit = ds.getConfigValue("invoice.page.limit");
            Integer limitSize = Integer.parseInt(limit);
            if(dateRange.getFromDate() == null && dateRange.getToDate() == null){
                System.out.println("here 1");
                  String  queryString = "SELECT e FROM ProformaInvoice e WHERE e.companyBranch =:companyBranch ORDER BY e.issuedDate DESC";
                  TypedQuery<ProformaInvoice> typedQuery = crudApi.getEm().createQuery(queryString, ProformaInvoice.class)
                                              .setParameter(ProformaInvoice._companyBranch, appSession.getCompanyBranch());
                                    return typedQuery.setMaxResults(limitSize).getResultList();
                                    
            }
            System.out.println("here 2");
            String qryString = "SELECT e FROM ProformaInvoice e WHERE e.valueDate BETWEEN ?1 AND ?2 AND e.companyBranch=?3 ORDER BY e.issuedDate DESC";
            TypedQuery<ProformaInvoice> typedQuery = crudApi.getEm().createQuery(qryString, ProformaInvoice.class)
                    .setParameter(1, dateRange.getFromDate())
                    .setParameter(2, dateRange.getToDate())
                    .setParameter(3, appSession.getCompanyBranch());
           return typedQuery.getResultList();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }
    public List<ProformaInvoice> getProformaInvoice(DateRangeUtil dateRange, CompanyBranch companyBranch){
        try{
            String limit = ds.getConfigValue("invoice.page.limit");
            Integer limitSize = Integer.parseInt(limit);
            if(dateRange.getFromDate() == null && dateRange.getToDate() == null){
                System.out.println("here 1");
                  String  queryString = "SELECT e FROM ProformaInvoice e WHERE e.companyBranch =:companyBranch ORDER BY e.issuedDate DESC";
                  TypedQuery<ProformaInvoice> typedQuery = crudApi.getEm().createQuery(queryString, ProformaInvoice.class)
                                              .setParameter(ProformaInvoice._companyBranch, companyBranch);
                                    return typedQuery.setMaxResults(limitSize).getResultList();
                                    
            }
            System.out.println("here 2");
            String qryString = "SELECT e FROM ProformaInvoice e WHERE e.valueDate BETWEEN :valueDate AND :valueDate AND e.companyBranch =:companyBranch ORDER BY e.issuedDate DESC";
            TypedQuery<ProformaInvoice> typedQuery = crudApi.getEm().createQuery(qryString, ProformaInvoice.class)
                    .setParameter(ProformaInvoice._valueDate, dateRange.getFromDate())
                    .setParameter(ProformaInvoice._valueDate, dateRange.getToDate())
                    .setParameter(ProformaInvoice._companyBranch, companyBranch);
           return typedQuery.getResultList();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }
    
    public List<ProformaInvoice> getInvoiceByDates(DateRangeUtil dateRange){
        try {
            if(dateRange.getFromDate() == null || dateRange.getToDate() == null)
            {
                String  queryString = "SELECT e FROM ProformaInvoice e ";
                return crudApi.getEm().createQuery(queryString, ProformaInvoice.class).getResultList();
            }
            
            String qryString = "SELECT e FROM ProformaInvoice e WHERE e.valueDate BETWEEN ?1 AND ?2";
            
            TypedQuery<ProformaInvoice> typedQuery = crudApi.getEm().createQuery(qryString, ProformaInvoice.class)
                    .setParameter(1, dateRange.getFromDate())
                    .setParameter(2, dateRange.getToDate());
           return typedQuery.getResultList();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }
    public List<ProformaInvoice> getInvoiceByBranch(CompanyBranch companyBranch)
    {
        try {
           
            String qryString = "SELECT e FROM ProformaInvoice e WHERE e.companyBranch=?1";
            
            TypedQuery<ProformaInvoice> typedQuery = crudApi.getEm().createQuery(qryString, ProformaInvoice.class)
                    .setParameter(1, companyBranch);
           return typedQuery.getResultList();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }
  
    public List<ProformaInvoice> getInvoiceByEmployee(UserAccount userAccount)
    {
        try 
        {
         
        String qryString = "SELECT e FROM ProformaInvoice e WHERE e.userAccount=?1";
            
        TypedQuery<ProformaInvoice> typedQuery = crudApi.getEm().createQuery(qryString, ProformaInvoice.class)
                    .setParameter(1, userAccount);
        return typedQuery.getResultList();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }
    public List<ProformaInvoice> getProformaInvoice(InvoiceStatus invoiceStatus)
    {
        try {
            
            String qryString = "";
            TypedQuery<ProformaInvoice> typedQuery = null;
            if(InvoiceStatus.OVERDUE_INVOICE == invoiceStatus)
            {
                qryString = "SELECT e FROM ProformaInvoice e WHERE e.expiryDate <= ?1 AND e.userAccount=?2 ORDER BY e.issuedDate DESC";   
            }
            else if(InvoiceStatus.VALID_INVOICE == invoiceStatus)
            {
                qryString = "SELECT e FROM ProformaInvoice e WHERE e.expiryDate >= ?1 AND e.userAccount=?2 ORDER BY e.issuedDate DESC";
            }
            
             typedQuery = crudApi.getEm().createQuery(qryString, ProformaInvoice.class)
              .setParameter(1, DateUtil._7DaysBeforeToday())
              .setParameter(2, appSession.getCurrentUser());
            
           return typedQuery.getResultList();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }

    public List<Inventory> getInventoryList()
    {
        try
        {
            String qryString = "SELECT e FROM Inventory e WHERE e.companyBranch=?1";
            TypedQuery<Inventory> typedQuery = crudApi.getEm().createQuery(qryString, Inventory.class)
                                    .setParameter(1, appSession.getCompanyBranch());
            return typedQuery.getResultList();

        } catch (Exception e)
        {
            e.printStackTrace();
        }

        return Collections.emptyList();
    }

    public List<ProformaInvoiceItem> getProformaInvoiceItemReceipt(ProformaInvoice proformaInvoice)
    {
        try
        {
            String qryString = "SELECT e FROM ProformaInvoiceItem e WHERE e.proformaInvoice=:proformaInvoice";
            TypedQuery<ProformaInvoiceItem> typedQuery = crudApi.getEm().createQuery(qryString, ProformaInvoiceItem.class)
                    .setParameter(ProformaInvoiceItem._proformaInvoice, proformaInvoice);
            return typedQuery.getResultList();
            
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        
        return Collections.emptyList();
    }
    
    public List<Tax> getTaxList()
    {
        try
        {
            return crudApi.getEm().createQuery("SELECT e FROM Tax e ORDER BY e.reOrder ASC", Tax.class).getResultList();
        } catch (Exception e)
        {
            e.printStackTrace();
        }

        return Collections.emptyList();
    }
    public List<Tax> getTaxList(TaxGroup taxGroup){
       return crudApi.getEm().createQuery("SELECT e FROM Tax e WHERE e.taxGroup = :taxGroup ORDER BY e.reOrder ASC", Tax.class)
               .setParameter(Tax._taxGroup, taxGroup)
               .getResultList();
    }
    
    public List<PaymentData> getPaymentInfoList(ProformaInvoice proformaInvoice)
    {
        try
        {
            String qryString = "SELECT e FROM PaymentData e WHERE e.proformaInvoice=?1";
            return crudApi.getEm().createQuery(qryString, PaymentData.class)
                    .setParameter(1, proformaInvoice).getResultList();
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }
    
    public PaymentData invoiceRecord(ProformaInvoice proformaInvoice)
    {
        try
        {
          String qryString = "SELECT e FROM PaymentData e WHERE e.proformaInvoice=:proformaInvoice";
            return crudApi.getEm().createQuery(qryString, PaymentData.class)
                    .setParameter(PaymentData._proformaInvoice, proformaInvoice).getResultStream().findFirst().orElse(null);
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }
        
    public List<Inventory> getStockShortageList()
    {
        try
        {
          String qryString = "SELECT e FROM Inventory e WHERE e.quantity <= 5 ORDER BY e.product ASC";
          return crudApi.getEm().createQuery(qryString, Inventory.class)
//                  .setParameter(1, companyBranch)
                  .getResultList();
           
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        
        return Collections.emptyList();
    }

    public ProformaInvoiceItem getInvoiceExist(ProformaInvoice proformaInvoice) {
        return crudApi.getEm().createQuery("SELECT e FROM ProformaInvoiceItem e WHERE e.proformaInvoice = :proformaInvoice", ProformaInvoiceItem.class)
                .setParameter(ProformaInvoiceItem._proformaInvoice, proformaInvoice)
                .setMaxResults(1)
                .getResultStream().findFirst().orElse(null);
    }
    
    public List<ProformaInvoice> getInvoiceList(CompanyBranch companyBranch, UserAccount userAccount)
    {
        try
        {
            if(companyBranch != null || userAccount != null)
            {
                String qryString = "SELECT e FROM ProformaInvoice e WHERE e.companyBranch=?1 AND e.userAccount=?2";
                TypedQuery<ProformaInvoice> typedQuery = crudApi.getEm().createQuery(qryString, ProformaInvoice.class)
                    .setParameter(1, companyBranch)
                    .setParameter(2, userAccount);
                return typedQuery.getResultList();  
            }
            
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        
        return Collections.emptyList();
    }
    
    public boolean processMail(String msg,String fromEmail){
        Properties prop = new Properties();
	prop.put("mail.smtp.host", ds.getConfigValue("mail.smtp.host"));
        prop.put("mail.smtp.port", ds.getConfigValue("mail.smtp.port"));
        prop.put("mail.smtp.auth", ds.getConfigValue("mail.smtp.auth"));
        prop.put("mail.smtp.starttls.enable", ds.getConfigValue("mail.smtp.starttls.enable"));
        
        Session session = Session.getInstance(prop,
                new javax.mail.Authenticator() {
                    @Override
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(ds.getConfigValue("mail.username"), ds.getConfigValue("mail.password"));
                    }
                });
        
        try {
//            session.setDebug(true);
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(fromEmail));
            message.setRecipients(
                    Message.RecipientType.TO,
                    InternetAddress.parse(ds.getConfigValue("admin.email"))
            );
            message.setSubject("Requesting For Invoice Reversal");
            message.setText(msg);

            Transport.send(message);

            System.out.println("Email sent");
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<PaymentData> getPendingDeliveryInvoice(ProformaInvoice proformaInvoice){
      return crudApi.getEm().createQuery("SELECT e FROM PaymentData e WHERE e.proformaInvoice=:proformaInvoice AND e.deliveryStatus=:deliveryStatus", PaymentData.class)
                    .setParameter(PaymentData._proformaInvoice, proformaInvoice)
                    .setParameter(PaymentData._deliveryStatus, DeliveryStatus.PENDING_DELIVERY)
                    .getResultList();
    }
    
    public List<PaymentData> getPendingDeliveryInvoice(){
      return crudApi.getEm().createQuery("SELECT e FROM PaymentData e WHERE e.deliveryStatus=:deliveryStatus", PaymentData.class)
                    .setParameter(PaymentData._deliveryStatus, DeliveryStatus.PENDING_DELIVERY)
                    .getResultList();
    }
       
    public List<TaxGroup> getTaxGroupList() {
        return crudApi.getEm().createQuery("SELECT e FROM TaxGroup e ORDER BY e.groupName DESC", TaxGroup.class).getResultList();
    }
}
