/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.khoders.invoicemaster.jbeans.converter;

import com.khoders.invoicemaster.sms.MessageTemplate;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.FacesConverter;
import org.omnifaces.converter.SelectItemsConverter;

/**
 *
 * @author richa
 */
@FacesConverter(forClass = MessageTemplate.class)
public class MessageTemplateConverter extends SelectItemsConverter
{
     @Override
    public String getAsString(FacesContext context, UIComponent component, Object value)
    {
        if (value == null)
        {
            return null;
        }
        return ((MessageTemplate) value).getId();
    }
}
