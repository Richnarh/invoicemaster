/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.khoders.invoicemaster;

/**
 *
 * @author richa
 */
public class ApiEndpoint
{
    private static final String VERSION = "v1/";
    
    public static final String AUTH_ENDPOINT = VERSION + "auth";
    public static final String LOOKUP_ENDPOINT = VERSION + "data";
    public static final String SALES_ENDPOINT = VERSION + "sales";
    public static final String TRANSACTION_ENDPOINT = VERSION + "transactions";
    public static final String INVOICE_ENDPOINT = VERSION + "invoice";
    
    // Admin
    public static final String COMPANY_BRANCH_ENDPOINT = VERSION + "company-branch";
    public static final String COMPANY_PROFILE_ENDPOINT = VERSION + "company-profile";
    public static final String USER_ENDPOINT = VERSION + "users";
    public static final String APP_CONFIG_ENDPOINT = VERSION + "app-config";
    public static final String INVENTORY_ENDPOINT = VERSION + "inventory";
    public static final String SALES_LEAD_ENDPOINT = VERSION + "sales-lead";
    public static final String PRODUCT_ENDPOINT = VERSION + "product";
    public static final String PRODUCT_TYPE_ENDPOINT = VERSION + "product-type";
    public static final String PAYMENT_ENDPOINT = VERSION + "payment";
}
