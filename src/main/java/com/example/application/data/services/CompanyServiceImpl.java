package com.example.application.data.services;

import com.example.application.data.repositories.CompanyActivityRepository;
import com.example.application.data.entity.Company;
import com.example.application.data.repositories.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class CompanyServiceImpl implements com.example.application.data.services.CompanyService {

    private final CompanyRepository companyRepository;
    private final CompanyActivityRepository companyActivityRepository;

    @Autowired
    public CompanyServiceImpl(CompanyRepository companyRepository, CompanyActivityRepository companyActivityRepository) {
        this.companyRepository = companyRepository;
        this.companyActivityRepository = companyActivityRepository;
    }

    @Override
    public Set<Company> getList() { //getlist methoduyla personseti elde ettik
        Set<Company> companySet =new HashSet<>();
        companyRepository.findAll().iterator().forEachRemaining((companySet::add)); // repodaki tüm personları listeliyor
        return companySet;
    }


    @Override
    public Set<Company> getList(String firmaAdi, String adres, String vergiDairesi, String vergiNo, String phoneNumber, Long systemUserId) { //getlist methoduyla personseti elde ettik
        Set<Company> companySet =new HashSet<>();
        companyRepository.findByFirmaAdiContainingAndSystemUserId(firmaAdi,systemUserId).iterator().forEachRemaining((companySet::add)); // repodaki tüm personları listeliyor

        //companyRepository.findByFirmaAdiContainingOrAdresContainingOrVergiDairesiContainingOrVergiNoContainingOrPhoneNumberContainingAndSystemUserId(filter,adres,vergiDairesi,vergiNo,phoneNumber,systemUserId).iterator().forEachRemaining((companySet::add));
        return companySet;
    }

    @Override
    public Set<Company> getList(Long systemUserId) {
        Set<Company> companySet = new HashSet<>();
        companyRepository.findBySystemUserId(systemUserId).iterator().forEachRemaining(companySet::add);

        return companySet;
    }

    /*
    @Override
    public Set<Company> search(String search, Long systemUserId) {
        Set<Company> companySet = new HashSet<>();
        companyRepository.search(search, systemUserId).iterator().forEachRemaining(companySet::add);

        return companySet;
    }

     */

    @Override
    public Company save(Company p) { //repoya person ekliyo
        return companyRepository.save(p);
    }

    @Override
    public void delete(Company p) {

        companyActivityRepository.deleteByCompany(p);
        companyRepository.delete(p);

    }





}
