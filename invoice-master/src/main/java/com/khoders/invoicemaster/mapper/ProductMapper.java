/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.khoders.invoicemaster.mapper;

import com.khoders.invoicemaster.dto.InventoryDto;
import com.khoders.invoicemaster.dto.ProductDto;
import com.khoders.invoicemaster.dto.ProductTypeDto;
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
import java.util.Base64;
import javax.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Pascal
 */
public class ProductMapper {
    private static final Logger log = LoggerFactory.getLogger(ProductMapper.class);
    @Inject private CrudApi crudApi;
    @Inject private AppService as;
    @Inject private InventoryService inventoryService;
    
    public Inventory toEntity(InventoryDto dto){
        log.info("mapping inventory dto to entity");
        Inventory inventory = new Inventory();
        if(dto.getId() != null){
            inventory.setId(dto.getId());
        }
        
        if(dto.getProductId() == null){
            throw new DataNotFoundException("Product Id Required");
        }
        inventory.setDescription(dto.getDescription());
        inventory.setFrameSize(dto.getFrameSize());
        inventory.setHeight(dto.getHeight());
        inventory.setWidth(dto.getWidth());
        inventory.setInventoryCode(SystemUtils.generateCode());
        inventory.setQuantity(dto.getQuantity());
        inventory.setProduct(inventoryService.getProduct(dto.getProductId()));
        inventory.setSellingPrice(dto.getSellingPrice());
        inventory.setUnitPrice(dto.getUnitPrice());
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
        if(inventory.getId() == null)return null;
        dto.setId(inventory.getId());
        if(inventory.getCompanyBranch() != null){
            dto.setCompanyBranchname(inventory.getCompanyBranch().getBranchName());
            dto.setCompanyBranchId(inventory.getCompanyBranch().getId());
        }
        if(inventory.getUserAccount()!= null){
            dto.setUserAccountName(inventory.getUserAccount().getEmail());
            dto.setUserAccountId(inventory.getUserAccount().getId());
        }
        
        dto.setDescription(inventory.getDescription());
        dto.setFrameSize(inventory.getFrameSize());
        dto.setHeight(inventory.getHeight());
        dto.setWidth(inventory.getWidth());
        dto.setInventoryCode(inventory.getInventoryCode());
        dto.setQuantity(inventory.getQuantity());
        if(inventory.getProduct() != null){
            dto.setProduct(inventory.getProduct().getProductName());
            dto.setProductId(inventory.getProduct().getId());
            dto.setProductCode(inventory.getProduct().getProductCode());
            dto.setProductType(inventory.getProduct().getProductType()+"");
        }
        dto.setSellingPrice(inventory.getSellingPrice());
        dto.setUnitPrice(inventory.getUnitPrice());
        return dto;
    }
    
    // Products
    public Product toEntity(ProductDto dto, AppParam param){
        log.debug("mapping product dto to product entity");
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
        String before = dto.getProductImage().substring(0, dto.getProductImage().indexOf(","));
        String after = dto.getProductImage().substring(dto.getProductImage().indexOf(",")+1);
        log.debug("before: {}",before);
        product.setProductImage(Base64.getDecoder().decode(after));
        product.setImageFormat(before);
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
        if(product.getProductImage() != null){
            String base64 = product.getImageFormat()+","+ Base64.getEncoder().encodeToString(product.getProductImage());
            dto.setProductImage(base64);
        }
        return dto;
    }

    public ProductType toEntity(ProductTypeDto typeDto, AppParam param) {
        ProductType productType = new ProductType();
        if(typeDto.getId() != null){
            productType.setId(typeDto.getId());
        }
        productType.setUserAccount(as.getUser(param.getUserAccountId()));
        productType.setCompanyBranch(as.getBranch(param.getCompanyBranchId()));
        productType.setProductTypeName(typeDto.getProductTypeName());
        return productType;
    }

    public ProductTypeDto toDto(ProductType productType) {
        ProductTypeDto dto = new ProductTypeDto();
        if(productType.getId() == null)return null;
        dto.setId(productType.getId());
        dto.setProductTypeName(productType.getProductTypeName());
        return dto;
    }
}
