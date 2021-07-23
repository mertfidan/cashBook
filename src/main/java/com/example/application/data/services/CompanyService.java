package com.example.application.data.services;

import com.example.application.data.entity.Company;

import java.util.Set;

public interface CompanyService {

    Set<Company> getList();
    Set<Company> getList(String firmaAdi, String adres,String vergiDairesi,String vergiNo,String phoneNumber,Long systemUserId);
    Set<Company> getList(Long systemUserId);

    //Set<Company> search(String search, Long systemUserId);

    Company save (Company p);

    void delete(Company p);

}
