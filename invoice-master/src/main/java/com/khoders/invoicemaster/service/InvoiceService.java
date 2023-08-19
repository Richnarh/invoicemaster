/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.khoders.invoicemaster.service;

import Zenoph.SMSLib.Enums.MSGTYPE;
import Zenoph.SMSLib.Enums.REQSTATUS;
import static Zenoph.SMSLib.Enums.REQSTATUS.ERR_INSUFF_CREDIT;
import static Zenoph.SMSLib.Enums.REQSTATUS.SUCCESS;
import Zenoph.SMSLib.ZenophSMS;
import com.google.common.primitives.Bytes;
import com.khoders.admin.mapper.AppParam;
import com.khoders.invoicemaster.DefaultService;
import com.khoders.invoicemaster.dto.InvoiceDto;
import com.khoders.invoicemaster.dto.InvoiceItemDto;
import com.khoders.invoicemaster.dto.PaymentDataDto;
import com.khoders.invoicemaster.dto.ReverseData;
import com.khoders.invoicemaster.dto.SalesDto;
import com.khoders.invoicemaster.dto.SalesTaxDto;
import com.khoders.invoicemaster.entities.Inventory;
import com.khoders.invoicemaster.entities.PaymentData;
import com.khoders.invoicemaster.entities.ProformaInvoice;
import com.khoders.invoicemaster.entities.ProformaInvoiceItem;
import com.khoders.invoicemaster.entities.SaleLead;
import com.khoders.invoicemaster.entities.SalesTax;
import com.khoders.invoicemaster.entities.Tax;
import com.khoders.invoicemaster.entities.UserAccount;
import com.khoders.invoicemaster.entities.system.CompanyBranch;
import com.khoders.invoicemaster.enums.AppVersion;
import com.khoders.invoicemaster.jbeans.ReportFiles;
import com.khoders.invoicemaster.mapper.InvoiceMapper;
import com.khoders.invoicemaster.mapper.PaymentMapper;
import com.khoders.invoicemaster.reportData.ProformaInvoiceDto;
import com.khoders.invoicemaster.reportData.Receipt;
import com.khoders.resource.enums.PaymentStatus;
import com.khoders.resource.jpa.CrudApi;
import com.khoders.resource.reports.ReportManager;
import com.khoders.resource.utilities.DateRangeUtil;
import com.khoders.resource.utilities.DateUtil;
import com.khoders.resource.utilities.Msg;
import com.khoders.resource.utilities.Pattern;
import java.time.LocalDate;
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
public class InvoiceService {
    private static final Logger log = LoggerFactory.getLogger(InvoiceService.class);
    @Inject private CrudApi crudApi;
    @Inject private AppService as;
    @Inject private SmsService smsService;
    @Inject private InvoiceMapper mapper;
    @Inject private ReportManager reportManager;
    @Inject private XtractService xtractService;
    @Inject private PaymentMapper pm;
    @Inject private ProformaInvoiceService invoiceService;
    @Inject private InventoryService inventoryService;
    @Inject private DefaultService ds;
    
    private double totalPayable;
    
    public InvoiceDto save(InvoiceDto invoiceDto, AppParam param) {
        log.debug("Saving proforma invoice");
        ProformaInvoice invoice = mapper.toEntity(invoiceDto, param);
        InvoiceDto dto = null;
        if(crudApi.save(invoice) != null){
            dto = mapper.toDto(invoice);
        }
        return dto;
    }
    
    public SalesDto saveAll(SalesDto salesDto, AppParam param){
        ProformaInvoice proformaInvoice = ds.getInvoiceById(salesDto.getId());
        String actionType = ds.getConfigValue("action.type");
        List<ProformaInvoiceItem> invoiceItemList = mapper.toEntity(salesDto.getInvoiceItemList());
        for (ProformaInvoiceItem proformaInvoiceItem : invoiceItemList) {
            proformaInvoiceItem.genCode();
            proformaInvoiceItem.setUserAccount(as.getUser(param.getUserAccountId()));
            proformaInvoiceItem.setCompanyBranch(as.getBranch(param.getCompanyBranchId()));
            crudApi.save(proformaInvoiceItem);
        }
        taxCalculation(salesDto, as.getUser(param.getUserAccountId()));
        
        SalesDto sales = new SalesDto();
        List<ProformaInvoiceItem> itemList = invoiceService.getProformaInvoiceItemList(proformaInvoice);
        List<SalesTax> taxList = invoiceService.getSalesTaxList(proformaInvoice);
        
        sales.setInvoiceItemList(mapper.toDto(itemList));
        sales.setSalesTaxList(mapper.toSalesTaxDto(taxList));
        sales.setTotalPayable(totalPayable);
        sales.setActionType(actionType);
        return sales;
    }
    
    public SalesDto manageInvoice(String invoiceId){
        SalesDto sales = new SalesDto();
        ProformaInvoice proformaInvoice = ds.getInvoiceById(invoiceId);
        String actionType = ds.getConfigValue("action.type");
        
        totalPayable = calculateVat(proformaInvoice);
        
        List<ProformaInvoiceItem> itemList = invoiceService.getProformaInvoiceItemList(proformaInvoice);
        List<SalesTax> taxList = invoiceService.getSalesTaxList(proformaInvoice);
        List<InvoiceItemDto> invoiceItemList = mapper.toDto(itemList);
        List<SalesTaxDto> salesTaxList = mapper.toSalesTaxDto(taxList);
        
        sales.setSubTotal(itemList.stream().mapToDouble(ProformaInvoiceItem::getSubTotal).sum());
        sales.setTotalPayable(totalPayable);
        sales.setActionType(actionType);
        sales.setInvoiceItemList(invoiceItemList);
        sales.setSalesTaxList(salesTaxList);
        return sales;
    }
    
    public double calculateVat(ProformaInvoice proformaInvoice){
        List<SalesTax> salesTaxList = invoiceService.getSalesTaxList(proformaInvoice);
        
        SalesTax covid19 = salesTaxList.get(0);
        SalesTax salesVat = salesTaxList.get(1);

        double totalLevies = covid19.getTaxAmount();

        double taxableValue = proformaInvoice.getTotalAmount() + totalLevies;
        log.debug("Covide19: {} taxAmnt: {} ", covid19.getTaxName(), covid19.getTaxAmount());
        log.debug("salesVat: {} taxAmnt: {} ", salesVat.getTaxName(), salesVat.getTaxAmount());
        log.debug("saleAmount: {} ", proformaInvoice.getTotalAmount());
        log.debug("totalLevies: {} ", totalLevies);
        log.debug("taxableValue: {} ", taxableValue);
        
        double vat = taxableValue * (salesVat.getTaxRate() / 100);

        totalPayable = vat + taxableValue + proformaInvoice.getInstallationFee();

        salesVat.setTaxAmount(vat);

        crudApi.save(salesVat);
        
        return totalPayable;
    }
    
    public void taxCalculation(SalesDto dto, UserAccount user){
        ProformaInvoice proformaInvoice = ds.getInvoiceById(dto.getId());
        SaleLead saleLead = crudApi.find(SaleLead.class, dto.getSalesLeadId());
        List<SalesTax> salesTaxList = invoiceService.getSalesTaxList(proformaInvoice);
        
        // delete all salesTax for the selected proforma invoice
        salesTaxList.forEach(tx-> {
            crudApi.delete(tx);
        });
        for (Tax tax : invoiceService.getTaxList()) {
            // v2 exclude the NHIL tax in sales calculation
            if (user.getAppVersion().equals(AppVersion.V2)) {
                if (tax.getTaxName().equals("NHIL")) {
                    continue;
                }
            }
            SalesTax st = new SalesTax();

            double calc = proformaInvoice.getTotalAmount() * (tax.getTaxRate() / 100);

            st.genCode();
            st.setTaxName(tax.getTaxName());
            st.setTaxRate(tax.getTaxRate());
            st.setTaxAmount(calc);
            st.setReOrder(tax.getReOrder());
            st.setUserAccount(user);
            st.setCompanyBranch(user.getCompanyBranch());
            st.setProformaInvoice(proformaInvoice);
            st.setSaleLead(saleLead);
            crudApi.save(st);
        }

        calculateVat(proformaInvoice);
    }
    
    public InvoiceDto findInvoiceById(String invoiceId){
        ProformaInvoice invoice = ds.getInvoiceById(invoiceId);
        return mapper.toDto(invoice);
    }
    public List<InvoiceDto> findAll() {
        log.debug("Fetching all invoices");
        List<ProformaInvoice> invoiceList = invoiceService.getProformaInvoiceList();
        List<InvoiceDto> dtoList = new LinkedList<>();
        invoiceList.forEach(item -> {
            dtoList.add(mapper.toDto(item));
        });
        return dtoList;
    }

    public boolean delete(String invoiceId) {
        ProformaInvoice invoice = ds.getInvoiceById(invoiceId);
        return invoice != null ? crudApi.delete(invoice) : false;
    }
    
    public InvoiceDto findByInvoiceNo(String invoiceNo){
        ProformaInvoice invoice = ds.getInvoice(invoiceNo);
        if(invoice == null) return null;
        return mapper.toDto(invoice);
    }

    public String reverseInvoice(String invoiceId) {
        ProformaInvoice proformaInvoice = crudApi.find(ProformaInvoice.class, invoiceId);
        boolean paymentData = ds.deletePaymentData(proformaInvoice);
        boolean taxInfo = ds.deleteSalesTax(proformaInvoice);
        boolean saleItem = ds.deleteSaleItem(proformaInvoice);
        
        ReverseData reverseData = new ReverseData();
        boolean reversed = false;
        if(paymentData){
            reverseData.setPaymentData("Payment data and delivery info cleared!");
            reversed = true;
            log.info("Payment data and delivery info cleared!");
        }
        if(taxInfo){
            reverseData.setTaxInfo("Tax info cleared");
            reversed = true;
            log.info("Tax info cleared");
        }
        if(saleItem){
            reverseData.setSaleItem("Invoice item info cleared and inventory quantity updated!");
            reversed = true;
            log.info("Invoice item info cleared and inventory quantity updated!");
        }
        proformaInvoice.setConverted(false);
        crudApi.save(proformaInvoice);
        
        if(reversed)
            return "Invoice reverse successful!";
        else
            return "No item to reverse";
    }

    public List<InvoiceDto> searchByDate(AppParam param) {
        System.out.println("fromDate: "+param.getFromDate());
        System.out.println("toDate: "+param.getToDate());
        LocalDate fromDate = DateUtil.parseLocalDate(param.getFromDate(), Pattern._yyyyMMdd);
        LocalDate toDate = DateUtil.parseLocalDate(param.getToDate(), Pattern._yyyyMMdd);
        
        System.out.println("fromDate#: "+fromDate);
        System.out.println("toDate#: "+toDate);
        
        DateRangeUtil dateRange = new DateRangeUtil(fromDate, toDate);
        
        System.out.println("fromDate___: "+dateRange.getFromDate());
        System.out.println("toDate_____: "+dateRange.getToDate());
        
        List<ProformaInvoice> proformaInvoiceList = invoiceService.getProformaInvoice(dateRange, as.getBranch(param.getCompanyBranchId()));
        List<InvoiceDto> dtoList = new LinkedList<>();
        proformaInvoiceList.forEach(item -> {
            dtoList.add(mapper.toDto(item));
        });
        return dtoList;
    }
    
    public List<InvoiceDto> invoicesToday(AppParam param) {
        List<ProformaInvoice> proformaInvoiceList = invoiceService.getProformaInvoiceList(as.getBranch(param.getCompanyBranchId()));
        List<InvoiceDto> dtoList = new LinkedList<>();
        proformaInvoiceList.forEach(item -> {
            dtoList.add(mapper.toDto(item));
        });
        return dtoList;
    }
    
    public List<InvoiceDto> searchByBranch(String branchId) {
        CompanyBranch branch = as.getBranch(branchId);
        List<ProformaInvoice> proformaInvoiceList = invoiceService.getInvoiceByBranch(branch);
        List<InvoiceDto> dtoList = new LinkedList<>();
        proformaInvoiceList.forEach(item -> {
            dtoList.add(mapper.toDto(item));
        });
        return dtoList;
    }
    
    public List<InvoiceDto> searchByUser(String userAccountId) {
        UserAccount userAccount = as.getUser(userAccountId);
        List<ProformaInvoice> proformaInvoiceList = invoiceService.getInvoiceByEmployee(userAccount);
        List<InvoiceDto> dtoList = new LinkedList<>();
        proformaInvoiceList.forEach(item -> {
            dtoList.add(mapper.toDto(item));
        });
        return dtoList;
    }
    
    public List<InvoiceItemDto> salesDetails(String invoiceId) {
        ProformaInvoice proformaInvoice = ds.getInvoiceById(invoiceId);
        List<InvoiceItemDto> dtoList = new LinkedList<>();
        List<ProformaInvoiceItem> invoiceItemList = invoiceService.getProformaInvoiceItemReceipt(proformaInvoice);
        invoiceItemList.forEach(item ->{
            dtoList.add(mapper.toDto(item));
        });
        return dtoList;
    }

    public List<InvoiceDto> searchByParam(String branchId, String userId) {
        List<ProformaInvoice> proformaInvoiceList = invoiceService.getInvoiceList(as.getBranch(branchId), as.getUser(userId));
        List<InvoiceDto> dtoList = new LinkedList<>();
        proformaInvoiceList.forEach(item -> {
            dtoList.add(mapper.toDto(item));
        });
        return dtoList;
    }

    public String savePaymentData(PaymentDataDto paymentData, AppParam param, String invoiceId){
        PaymentData infoData = invoiceService.invoiceRecord(ds.getInvoiceById(invoiceId));
        if (infoData != null) {
            return "Payment data already captured!";
        }
        PaymentData data = pm.toEntity(paymentData);
        data.setCompanyBranch(as.getBranch(param.getCompanyBranchId()));
        data.setUserAccount(as.getUser(param.getUserAccountId()));
        
        if(crudApi.save(data) != null){
            ProformaInvoice invoice = crudApi.find(ProformaInvoice.class, data.getProformaInvoice().getId());
            invoice.setConverted(true);
            crudApi.save(invoice);
            
            if(data.getPaymentStatus() == PaymentStatus.FULLY_PAID){
                List<Inventory> inventoryList = inventoryService.getInventoryList();
                List<ProformaInvoiceItem> purchasedList = invoiceService.getProformaInvoiceItemList(data.getProformaInvoice());
                for (ProformaInvoiceItem item : purchasedList) {
                    for (Inventory inventory : inventoryList) {
                        if (item.getInventory().getId().equals(inventory.getId())) {
                            int qtyPurchased = item.getQuantity();
                            int qtyInStock = inventory.getQuantity();

                            int qtyAtHand = qtyInStock - qtyPurchased;

                            log.debug("qtyPurchased: {} ", qtyPurchased);
                            log.debug("qtyInStock: {} ", qtyInStock);
                            log.debug("qtyAtHand: {} ", qtyAtHand);

                            Inventory stock = crudApi.find(Inventory.class, inventory.getId());
                            stock.setQuantity(qtyAtHand);
                            crudApi.save(stock);
                        }
                    }
                }
            }
            processPaymentSms(data);
            
            return "Payment info saved successfully!";
        }
        
        return null;
    }
    
    public void processPaymentSms(PaymentData paymentData) {
        String phoneNumber = null;
        try {
            ZenophSMS zsms = smsService.extractParams();
            zsms.setMessage("Thanks for visiting Dolphin Doors, we're happy to see you. We'll be looking forward to seeing you again!. \n"
                    + "Contact us: \n "
                    + "Website: https://dolphindoors.com/ \n"
                    + "Tel: +233 302 986 345/+233 302 252 027 \n"
                    + "Email: info@dolphindoors.com");

            if (paymentData.getProformaInvoice() != null) {
                if (paymentData.getProformaInvoice().getClient() != null) {
                    phoneNumber = paymentData.getProformaInvoice().getClient().getPhone();
                }
            }
            log.debug("phoneNumber: {} ", phoneNumber);
            List<String> numbers = zsms.extractPhoneNumbers(phoneNumber);

            for (String number : numbers) {
                zsms.addRecipient(number);
            }

            zsms.setSenderId(ds.getSenderId() != null ? ds.getSenderId() : ds.getConfigValue("sms.sender.id"));
            zsms.setMessageType(MSGTYPE.TEXT);
            
            List<String[]> response = zsms.submit();
            for (String[] destination : response) {
                REQSTATUS reqstatus = REQSTATUS.fromInt(Integer.parseInt(destination[0]));
                if (reqstatus == null) {
                    Msg.error("failed to send message");
                    break;
                } else {
                    switch (reqstatus) {
                        case SUCCESS:
                            log.info("SMS Sent");
                            break;
                        case ERR_INSUFF_CREDIT:
                            Msg.error("Insufficeint Credit");
                        default:
                            Msg.error("Failed to send message");
                            return;
                    }
                }
            }
        } catch (Exception e) {
        }
    }

    public PaymentDataDto getPaymentDataByInvoiceId(String invoiceId) {
        log.debug("fetching payment info: {} ", invoiceId);
       PaymentDataDto dto = null;
       PaymentData infoData = invoiceService.invoiceRecord(ds.getInvoiceById(invoiceId));
       if(infoData !=  null){
           dto = pm.toDto(infoData);
       }
       return dto;
    }
    
    public byte[] generateInvoice(String invoiceId) {
        List<ProformaInvoiceDto> proformaInvoiceDtoList = new LinkedList<>();
        List<ProformaInvoiceDto> coverDataList = new LinkedList<>();
        
        reportManager.param.put("logo", ReportFiles.LOGO);
        ProformaInvoice proformaInvoice = ds.getInvoiceById(invoiceId);
        
        ProformaInvoiceDto coverData = xtractService.extractToProformaInvoiceCover(proformaInvoice);
        coverDataList.add(coverData);
        byte[] coverPrint = reportManager.createByteReport(coverDataList, ReportFiles.PRO_INVOICE_COVER, reportManager.param);
        
        ProformaInvoiceDto proformaInvoiceDto = xtractService.extractToProformaInvoice(proformaInvoice);
        proformaInvoiceDtoList.add(proformaInvoiceDto);
        byte[] invoicePrint = reportManager.createByteReport(proformaInvoiceDtoList, ReportFiles.PRO_INVOICE_FILE, reportManager.param);
        
        byte[] bytes = Bytes.concat(coverPrint,invoicePrint);
        
        log.debug("bytes: {} ",bytes);
        System.out.println("Done!");
        return bytes;
    } 
    
    public byte[] generateReceipt(String invoiceId) {
        List<Receipt> receiptList = new LinkedList<>();
        ProformaInvoice proformaInvoice = ds.getInvoiceById(invoiceId);
        Receipt receipt = xtractService.extractToReceipt(proformaInvoice);

        receiptList.add(receipt);
        reportManager.param.put("logo", ReportFiles.LOGO);
        return reportManager.createByteReport(receiptList, ReportFiles.RECEIPT_FILE, reportManager.param);
    } 
    
    public String reverseApproval(AppParam param,String invoiceId){
        String msg = "";
        ProformaInvoice proformaInvoice = ds.getInvoiceById(invoiceId);
        UserAccount userAccount = as.getUser(param.getUserAccountId());
        String url = "http://185.218.125.78:8080/invoicemaster/secured/templates/reverse-sale.xhtml?id="+proformaInvoice.getQuotationNumber();
        StringBuilder sb = new StringBuilder();
        sb.append("Request from ");
        sb.append(userAccount.getFullname()).append(" - ");
        sb.append(userAccount.getCompanyBranch().getBranchName());
        sb.append(" to reverse Invoice No.: ");
        sb.append(proformaInvoice.getQuotationNumber());
        sb.append("\n").append("Click the link below to complete the reversal.").append("\n");
        sb.append(url);
        System.out.println("Msg: "+sb.toString());
        sendMsg(sb.toString());
         
        boolean sentMail = invoiceService.processMail(sb.toString(), userAccount.getEmail());
        if(sentMail){
            log.debug("Email sent");
            msg = "Reversal email request sent, admin will notify you shortly!";
        }
        return msg;
    }
    public void sendMsg(String msg){
        String senderId = ds.getConfigValue("sms.sender.id");
        String adminNumber = ds.getConfigValue("admin.number");
        try 
        {
            ZenophSMS zsms = smsService.extractParams();
            zsms.setMessage(msg);
            zsms.addRecipient(adminNumber);
            zsms.setSenderId(senderId != null && senderId.isEmpty() || senderId == null ? ds.getSenderId() : senderId);
            zsms.setMessageType(MSGTYPE.TEXT);

            List<String[]> response = zsms.submit();
            for (String[] destination : response) {
                REQSTATUS reqstatus = REQSTATUS.fromInt(Integer.parseInt(destination[0]));
                if (reqstatus == null) {
                    Msg.error("failed to send message");
                    break;
                } else {
                    switch (reqstatus) {
                        case SUCCESS:
                           log.info("Reversal SMS request sent, admin will notify you shortly!");
                            break;
                        case ERR_INSUFF_CREDIT:
                            log.error("Insufficeint Credit");
                        default:
                            log.error("Failed to send message");
                            return;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
//   public byte[] appendData(byte[] firstObject,byte[] secondObject){
//    ByteArrayOutputStream outputStream = new ByteArrayOutputStream( );
//    try {
//        if (firstObject!=null && firstObject.length!=0)
//            outputStream.write(firstObject);
//        if (secondObject!=null && secondObject.length!=0)   
//            outputStream.write(secondObject);
//    } catch (IOException e) {
//        e.printStackTrace();
//    }
//    return outputStream.toByteArray();
//}
//    public byte[] createByteReport(List<JasperPrint> jasperPrints){
//        byte[] a = null;
//        byte[] b = null;
//        byte[] combined = null;
//                
//        try {
//            for (int i = 0; i <= jasperPrints.size() - 1; i++) {
//                System.out.println("report: "+i);
//                byte[] printByte = JasperExportManager.exportReportToPdf(jasperPrints.get(i));
//                append(printByte);
//            }
//            System.out.println("a: "+a +"\t b: "+b);
//            combined = ByteBuffer.allocate(a.length+b.length).put(a).put(b).array();
//        } catch (JRException e) {
//        }
//        return combined;
//    }
//    
//    public static final byte[] append(final byte[]... arrays) {
//    final ByteArrayOutputStream out = new ByteArrayOutputStream();
//    if (arrays != null) {
//        for (final byte[] array : arrays) {
//            if (array != null) {
//                out.write(array, 0, array.length);
//            }
//        }
//    }
//    return out.toByteArray();
//}
}
