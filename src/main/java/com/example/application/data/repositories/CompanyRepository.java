package com.example.application.data.repositories;

import com.example.application.data.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CompanyRepository extends CrudRepository <Company,Long>, JpaRepository<Company,Long> {
    List<Company> findByFirmaAdiContainingAndSystemUserId(String firmaAdi,Long systemUserId);

    List<Company> findBySystemUserId(Long systemUserId);
    //List<Company> findByFirmaAdiContainingOrAdresContainingOrVergiDairesiContainingOrVergiNoContainingOrPhoneNumberContainingAndSystemUserId(String firmaAdi,String adress,String vergiDairesi,String vergiNo,String phoneNumber ,Long systemUserId);

    //List<Person> findByNameContainingOrSurnameContainingAndSystemUserId(String name,String surname,Long systemUserId);

}
