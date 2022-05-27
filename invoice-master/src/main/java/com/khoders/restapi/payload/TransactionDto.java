/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.khoders.restapi.payload;

import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author richa
 */
public class TransactionDto extends BaseDto
{
    private String firstname;
    private String lastname;
    private String streetAddress;
    private double cartTotal;
    private double cartQty;
    private double paymentStatus;
    private double deliveryStatus;
    private List<SaleItemDto> saleItemList = new LinkedList<>();
}