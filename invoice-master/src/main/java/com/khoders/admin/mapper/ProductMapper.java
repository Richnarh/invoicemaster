/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.khoders.admin.mapper;

import com.khoders.invoicemaster.dto.InventoryDto;
import com.khoders.invoicemaster.dto.ProductDto;
import com.khoders.invoicemaster.entities.Inventory;
import com.khoders.invoicemaster.entities.Product;
import com.khoders.invoicemaster.entities.ProductType;
import com.khoders.invoicemaster.entities.UserAccount;
import com.khoders.invoicemaster.entities.system.CompanyBranch;
import com.khoders.invoicemaster.service.AppService;
import com.khoders.invoicemaster.service.InventoryService;
import com.khoders.resource.exception.DataNotFoundException;
import com.khoders.resource.jpa.CrudApi;
import com.khoders.resource.utilities.SystemUtils;
import javax.inject.Inject;

/**
 *
 * @author Pascal
 */
public class ProductMapper {
    @Inject private CrudApi crudApi;
    @Inject private AppService as;
    @Inject private InventoryService inventoryService;
    
    public Inventory toEntity(InventoryDto dto){
        Inventory inventory = new Inventory();
        if(dto.getId() != null){
            inventory.setId(dto.getId());
        }
        if(dto.getCompanyBranchId() == null){
            throw new DataNotFoundException("Company Branch Id Required");
        }
        if(dto.getProductId() == null){
            throw new DataNotFoundException("Product Id Required");
        }
        inventory.setCompanyBranch(as.getBranch(dto.getCompanyBranchId()));
        inventory.setDescription(dto.getDescription());
        inventory.setFrameSize(dto.getFrameSize());
        inventory.setHeight(dto.getHeight());
        inventory.setWidth(dto.getWidth());
        inventory.setInventoryCode(SystemUtils.generateCode());
        inventory.setQuantity(dto.getQuantity());
        inventory.setProduct(inventoryService.getProduct(dto.getProductId()));
        inventory.setSellingPrice(dto.getSellingPrice());
        inventory.setUnitPrice(dto.getUnitPrice());
        inventory.setUserAccount(as.getUser(dto.getUserAccountId()));
        return inventory;
    }
    
    public Inventory toParam(AppParam param){
        Inventory inventory = new Inventory();
        UserAccount userAccount = as.getUser(param.getUserAccountId());
        CompanyBranch companyBranch = as.getBranch(param.getCompanyBranchId());
        inventory.setUserAccount(userAccount);
        inventory.setCompanyBranch(companyBranch);
        return inventory;
    }

    public InventoryDto toDto(Inventory inventory) {
        InventoryDto dto = new InventoryDto();
        if(inventory.getCompanyBranch() != null){
            dto.setCompanyBranchname(inventory.getCompanyBranch().getBranchName());
            dto.setCompanyBranchId(inventory.getCompanyBranch().getId());
        }
        if(inventory.getUserAccount()!= null){
            dto.setUserAccountName(inventory.getUserAccount().getEmail());
            dto.setUserAccountId(inventory.getUserAccount().getId());
        }
        
        dto.setDescription(dto.getDescription());
        dto.setFrameSize(dto.getFrameSize());
        dto.setHeight(dto.getHeight());
        dto.setWidth(dto.getWidth());
        dto.setInventoryCode(SystemUtils.generateCode());
        dto.setQuantity(dto.getQuantity());
        dto.setProduct(inventory.getProduct().getProductName());
        dto.setSellingPrice(dto.getSellingPrice());
        dto.setUnitPrice(dto.getUnitPrice());
        return dto;
    }
    
    // Products
    public Product toEntity(ProductDto dto, AppParam param){
        Product product = new Product();
        if(dto.getId() != null){
            product.setId(dto.getId());
        }
        product.setProductCode(dto.getProductCode());
        product.setProductName(dto.getProductName());
        product.setDescription(dto.getDescription());
        if(dto.getProductTypeId() == null){
            throw new DataNotFoundException("ProductType Id is required");
        }
        ProductType productType = crudApi.find(ProductType.class, dto.getProductTypeId());
        product.setProductType(productType);
        product.setReorderLevel(dto.getReorderLevel());
        product.setUserAccount(as.getUser(param.getUserAccountId()));
        product.setCompanyBranch(as.getBranch(param.getCompanyBranchId()));
        return product;
    }
    
    public ProductDto toDto(Product product){
        ProductDto dto = new ProductDto();
        if(product.getId() == null)return null;
        dto.setId(product.getId());
        dto.setProductCode(product.getProductCode());
        dto.setProductName(product.getProductName());
        dto.setDescription(product.getDescription());
        dto.setProductType(product.getProductType() != null ? product.getProductType().getProductTypeName() : null);
        dto.setProductTypeId(product.getProductType() != null ? product.getProductType().getId() : null);
        dto.setReorderLevel(product.getReorderLevel());
        return dto;
    }
}
