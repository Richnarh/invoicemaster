/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.khoders.im.admin.dto;

import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Pascal
 */
public class SmsData {
    private String text;
    private int type;
    private String sender;
    private List<Destination> destinations = new LinkedList<>();

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
    
    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public List<Destination> getDestinations() {
        return destinations;
    }

    public void setDestinations(List<Destination> destinations) {
        this.destinations = destinations;
    }
    
    public static class Destination{
        private String to;

        public String getTo() {
            return to;
        }

        public void setTo(String to) {
            this.to = to;
        }
        
    }
}
