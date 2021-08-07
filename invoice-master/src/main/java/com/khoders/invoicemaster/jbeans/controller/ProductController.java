/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.khoders.invoicemaster.jbeans.controller;

import com.khoders.invoicemaster.entites.Product;
import com.khoders.invoicemaster.listener.AppSession;
import com.khoders.invoicemaster.service.InventoryService;
import com.khoders.resource.jpa.CrudApi;
import com.khoders.resource.utilities.CollectionList;
import com.khoders.resource.utilities.Msg;
import com.khoders.resource.utilities.SystemUtils;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.Serializable;
import java.util.Base64;
import java.util.LinkedList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.file.UploadedFile;
import org.primefaces.util.SerializableSupplier;

/**
 *
 * @author khoders
 */
@Named(value = "productController")
@SessionScoped
public class ProductController implements Serializable{
    @Inject CrudApi crudApi;
    @Inject AppSession appSession;
    @Inject InventoryService inventoryService;
    
    private String optionText;
    
    private Product product = new Product();
    private List<Product> productList = new LinkedList<>();
    private StreamedContent productImge = null;
    
    private UploadedFile file = null;
    
    @PostConstruct
    private void init()
    {
        productList = inventoryService.getProductList();
    
        productList.forEach(prdt ->
        {
            getImages(prdt.getId());
            System.out.println("Image Id => "+prdt.getId());
        });
        clearProduct();
        
    }
    
    public void getImages(String id)
    {
       try
        {
          product = inventoryService.getsingleImage(id);
          
          createStreamContent(product);
          
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    

        
    public void createStreamContent(Product product)
    {
        try
        {
            productImge = DefaultStreamedContent.builder().stream(new SerializableSupplier<InputStream>()
            {
                @Override
                public InputStream get()
                {
                    if(product.getProductImage() == null)
                    {
                        return null;
                    }
                    return new ByteArrayInputStream(product.getProductImage());
                }
            }).build();
            
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public boolean validateImage(String imageContentType){
        
        try {
            
            if(imageContentType == null){
                return false;
            }
            
            imageContentType = imageContentType.toLowerCase();
            
            if(imageContentType.contains("image/jpeg") || imageContentType.contains("image/jpg")
                    || imageContentType.contains("image/png") || imageContentType.contains("image/gif")
                    || imageContentType.contains("image/tiff"))
            {
                return true;
            }
            
        } catch (Exception e) {
            
        }
        
        return false;
    }
    
    public void selectedProductImage(String id)
    {
        try
        {
          product = inventoryService.getsingleImage(id);
          
          createStreamContent(product);
          
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    
   public void saveProduct()
    {
       try 
       {
           if (file.getSize() > 0)
           {
               if (!validateImage(file.getContentType()))
               {
                   FacesContext.getCurrentInstance().addMessage(null,
                           new FacesMessage(FacesMessage.SEVERITY_INFO, Msg.setMsg("Upload An Image File: jpeg/jpg/png/gif/tiff"), null));
                   return;
               }
               byte[] bytes = new byte[(int) file.getSize()];
               file.getInputStream().read(bytes);
               String encoded = new String(Base64.getEncoder().encode(bytes), "UTF-8");

               product.setProductImage(Base64.getDecoder().decode(encoded));
               product.setImageFormat("data:" + file.getContentType() + ";base64");
           }

           product.genCode();
          if(crudApi.save(product) != null)
          {
              productList = CollectionList.washList(productList, product);
              
              FacesContext.getCurrentInstance().addMessage(null, 
                        new FacesMessage(FacesMessage.SEVERITY_INFO, Msg.SUCCESS_MESSAGE, null)); 
              
              createStreamContent(product);
          }
          else
          {
              FacesContext.getCurrentInstance().addMessage(null, 
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, Msg.setMsg("Oops! failed to save product"), null));
          }
           clearProduct();
        } catch (Exception e) 
        {
            e.printStackTrace();
        }
    }
   
   
    public void editProduct(Product product)
    {
       optionText = "Update";
       this.product=product;
    }
    
    public void deleteProduct(Product product)
    {
        try
        {
          if(crudApi.delete(product))
          {
              productList.remove(product);
              
              FacesContext.getCurrentInstance().addMessage(null, 
                        new FacesMessage(FacesMessage.SEVERITY_INFO, Msg.DELETE_MESSAGE, null)); 
          }
          else
          {
              FacesContext.getCurrentInstance().addMessage(null, 
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, Msg.FAILED_MESSAGE, null));
          }
        } catch (Exception e) 
        {
            e.printStackTrace();
        }
    }
    
    public void clearProduct() {
        product = new Product();
        file = null;
        product.setUserAccount(appSession.getCurrentUser());
        optionText = "Save Changes";
        SystemUtils.resetJsfUI();
    }

    public String getOptionText() {
        return optionText;
    }

    public void setOptionText(String optionText) {
        this.optionText = optionText;
    }

    public Product getProduct()
    {
        return product;
    }

    public void setProduct(Product product)
    {
        this.product = product;
    }

    public List<Product> getProductList()
    {
        return productList;
    }

    public UploadedFile getFile()
    {
        return file;
    }

    public void setFile(UploadedFile file)
    {
        this.file = file;
    }

    public StreamedContent getProductImge()
    {
        return productImge;
    }
    
}
