package com.example.application.data.services;

import com.example.application.data.entity.Company;
import com.example.application.data.entity.CompanyActivity;

import java.util.Set;

public interface CompanyActivityService {


    Set<CompanyActivity> getList();
    Set<CompanyActivity> getList(Company company);

    Set<CompanyActivity> getList(Long userId);

    CompanyActivity save (CompanyActivity p);


    default void delete(CompanyActivity p) {

    }

}
