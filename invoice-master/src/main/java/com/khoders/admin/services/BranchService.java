/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.khoders.admin.services;

import com.khoders.invoicemaster.dto.CompanyBranchDto;
import com.khoders.admin.mapper.CompanyMapper;
import com.khoders.invoicemaster.dto.CompanyProfileDto;
import com.khoders.invoicemaster.entities.system.CompanyBranch;
import com.khoders.invoicemaster.entities.system.CompanyProfile;
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
public class BranchService {
    @Inject private CrudApi crudApi;
    @Inject private CompanyService companyService;
    @Inject private CompanyMapper mapper;
    
    public CompanyBranchDto save(CompanyBranchDto dto){
        CompanyBranchDto branchDto = null;
        CompanyBranch branch = mapper.toEntity(dto);
        if(branch != null){
            branch = crudApi.save(branch);
            if(branch != null){
                branchDto = mapper.toDto(branch);
            }
        }
        return branchDto;
    }
    
    public boolean delete(String branchId){
        CompanyBranch branch = crudApi.find(CompanyBranch.class, branchId);
        return branch != null && crudApi.delete(branch);
    }

    public List<CompanyBranchDto> fetchAllBranches() {
        List<CompanyBranch> branches = companyService.getCompanyBranchList();
        List<CompanyBranchDto> dtoList = new LinkedList<>();
        branches.forEach(branch -> {
            dtoList.add(mapper.toDto(branch));
        });
        return dtoList;
    }

    public CompanyBranchDto findById(String companyBranchId) {
        CompanyBranch branch = crudApi.find(CompanyBranch.class, companyBranchId);
        return mapper.toDto(branch);
    }

    // Company Profile
    public CompanyProfileDto saveProfile(CompanyProfileDto dto) {
        CompanyProfileDto profileDto = null;
        CompanyProfile profile = mapper.toEntity(dto);
        if(crudApi.save(profile) != null){
            profileDto = mapper.toDto(profile);
        }
        return profileDto;
    }

    public CompanyProfileDto findProfileById(String profileId) {
        CompanyProfile profile = crudApi.find(CompanyProfile.class, profileId);
        return mapper.toDto(profile);
    }

    public List<CompanyProfileDto> fetchAllProfiles() {
        List<CompanyProfile> profiles = companyService.getCompanyProfileList();
        List<CompanyProfileDto> dtoList = new LinkedList<>();
        profiles.forEach(profile -> {
            dtoList.add(mapper.toDto(profile));
        });
        return dtoList;
    }

    public boolean deleteProfile(String profileId) {
        CompanyProfile companyProfile = crudApi.find(CompanyProfile.class, profileId);
        return companyProfile != null ? crudApi.delete(companyProfile) : false;
    }
}
