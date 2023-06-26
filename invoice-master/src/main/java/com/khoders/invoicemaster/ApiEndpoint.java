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
}
