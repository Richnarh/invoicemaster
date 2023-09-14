/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.khoders.invoicemaster.service;

import com.khoders.invoicemaster.dto.InventoryDto;
import com.khoders.invoicemaster.mapper.AppParam;
import com.khoders.invoicemaster.mapper.ProductMapper;
import com.khoders.invoicemaster.dto.ProductDto;
import com.khoders.invoicemaster.dto.ProductTypeDto;
import com.khoders.invoicemaster.entities.Inventory;
import com.khoders.invoicemaster.entities.Product;
import com.khoders.invoicemaster.entities.ProductType;
import com.khoders.invoicemaster.service.InventoryService;
import com.khoders.resource.jpa.CrudApi;
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
public class ProductService {
    private static final Logger log = LoggerFactory.getLogger(ProductService.class);
    @Inject private CrudApi crudApi;
    @Inject private InventoryService inventoryService;
    @Inject private ProductMapper mapper;
    
    public InventoryDto save(InventoryDto inventoryDto, AppParam param){
        log.debug("Saving inventory");
        Inventory i = mapper.toParam(param);
        Inventory inventory = mapper.toEntity(inventoryDto);
        inventory.setCompanyBranch(i.getCompanyBranch());
        inventory.setUserAccount(i.getUserAccount());
        
        InventoryDto dto = null;
        if(crudApi.save(inventory) != null){
            dto = mapper.toDto(inventory);
        }
        return dto;
    }
    
    public List<InventoryDto> findAll(){
        List<Inventory> inventoryList = inventoryService.getInventoryList();
        List<InventoryDto> dtoList = new LinkedList<>();
        inventoryList.forEach(item -> {
            dtoList.add(mapper.toDto(item));
        });
        return dtoList;
    }
    
    public InventoryDto findById(String inventoryId){
        Inventory inventory = inventoryService.getInventory(inventoryId);
        return mapper.toDto(inventory);
    }
    
    public boolean delete(String inventoryId){
        Inventory inventory = inventoryService.getInventory(inventoryId);
        return inventory != null ? crudApi.delete(inventory) : false;
    }
    
    // Product
    public ProductDto save(ProductDto productDto, AppParam param){
        log.debug("saving products");
        Product product = mapper.toEntity(productDto, param);
        ProductDto dto = null;
        if(crudApi.save(product) != null){
            dto = mapper.toDto(product);
        }
        return dto;
    }
    public List<ProductDto> findAllProducts(){
        log.debug("Fetching all products");
        List<Product> productList = inventoryService.getProductList();
        List<ProductDto> dtoList = new LinkedList<>();
        productList.forEach(item -> {
            dtoList.add(mapper.toDto(item));
        });
        return dtoList;
    }
    
    public ProductDto findProductById(String productId){
        Product product = inventoryService.getProduct(productId);
        return mapper.toDto(product);
    }
    
    public boolean deleteProduct(String productId) {
        Product product = inventoryService.getProduct(productId);
        return product != null ? crudApi.delete(product) : false;
    }

    // product type
    public ProductTypeDto save(ProductTypeDto typeDto, AppParam param) {
        log.debug("saving product type");
        ProductType productType = mapper.toEntity(typeDto, param);
        ProductTypeDto dto = null;
        if(crudApi.save(productType) != null){
            dto = mapper.toDto(productType);
        }
        return dto;
    }
    
    public List<ProductTypeDto> findAllProductTypes(){
        log.debug("Fetching all product types");
        List<ProductType> productTypeList = inventoryService.getProductTypeList();
        List<ProductTypeDto> dtoList = new LinkedList<>();
        productTypeList.forEach(item -> {
            dtoList.add(mapper.toDto(item));
        });
        return dtoList;
    }
    
    public ProductTypeDto findByTypeId(String productTypeId) {
        ProductType productType = inventoryService.getProductType(productTypeId);
        return mapper.toDto(productType);
    }
    
    public boolean deleteType(String productTypeId) {
        ProductType productType = inventoryService.getProductType(productTypeId);
        return productType != null ? crudApi.delete(productType) : false;
    }
}
