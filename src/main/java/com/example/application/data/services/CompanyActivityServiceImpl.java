package com.example.application.data.services;

import com.example.application.data.entity.Company;
import com.example.application.data.entity.CompanyActivity;
import com.example.application.data.repositories.CompanyActivityRepository;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class CompanyActivityServiceImpl implements CompanyActivityService {

    CompanyActivityRepository companyActivityRepository;

    public CompanyActivityServiceImpl(CompanyActivityRepository companyActivityRepository) {
        this.companyActivityRepository = companyActivityRepository;
    }

    @Override
    public Set<CompanyActivity> getList() {
        Set<CompanyActivity> companyActivitySet =new HashSet<>();
        companyActivityRepository.findAll().iterator().forEachRemaining((companyActivitySet::add)); // repodaki tüm personları listeliyor
        return companyActivitySet;
    }

    @Override
    public Set<CompanyActivity> getList(Company company) { //getlist methoduyla personseti elde ettik
        Set<CompanyActivity> companyActivitySet =new HashSet<>();
        companyActivityRepository.findByCompanyContaining(company).iterator().forEachRemaining((companyActivitySet::add)); // repodaki tüm personları listeliyor
        return companyActivitySet;
    }

    @Override
    public Set<CompanyActivity> getList(Long userId) { // userid ile çekme
        Set<CompanyActivity> companyActivitySet =new HashSet<>();
        companyActivityRepository.listByUserId(userId).iterator().forEachRemaining((companyActivitySet::add)); // repodaki tüm personları listeliyor
        return companyActivitySet;
    }

    @Override
    public CompanyActivity save(CompanyActivity p) {
        return companyActivityRepository.save(p);
    }

    @Override
    public void delete(CompanyActivity p) {

        companyActivityRepository.delete(p);


    }


}
