/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.khoders.admin.services;

import com.khoders.invoicemaster.dto.InventoryDto;
import com.khoders.admin.mapper.AppParam;
import com.khoders.admin.mapper.ProductMapper;
import com.khoders.invoicemaster.dto.ProductDto;
import com.khoders.invoicemaster.entities.Inventory;
import com.khoders.invoicemaster.entities.Product;
import com.khoders.invoicemaster.service.InventoryService;
import com.khoders.resource.jpa.CrudApi;
import java.util.LinkedList;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author Pascal
 */
@Stateless
public class ProductService {
    @Inject private CrudApi crudApi;
    @Inject private InventoryService inventoryService;
    @Inject private ProductMapper mapper;
    
    public InventoryDto save(InventoryDto inventoryDto, AppParam param){
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
        Product product = mapper.toEntity(productDto, param);
        ProductDto dto = null;
        if(crudApi.save(product) != null){
            dto = mapper.toDto(product);
        }
        return dto;
    }
    public List<ProductDto> findAllProducts(){
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
}
