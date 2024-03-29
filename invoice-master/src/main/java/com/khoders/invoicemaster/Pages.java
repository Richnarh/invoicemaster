package com.khoders.invoicemaster;

import javax.ws.rs.core.Application;
import org.omnifaces.util.Faces;

/**
 * Configures JAX-RS for the application.
 *
 * @author Juneau
 */
public class Pages extends Application
{
    public static String index = Faces.getRequestBaseURL() + "secured/index.xhtml?faces-redirect=true";
    public static String quickInvoice = Faces.getRequestBaseURL() + "secured/templates/quick-invoice.xhtml";
    public static String waybill = Faces.getRequestBaseURL() + "secured/templates/waybill.xhtml";
    public static String login = Faces.getRequestBaseURL();
}
