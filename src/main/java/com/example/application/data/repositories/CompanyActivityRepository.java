package com.example.application.data.repositories;

import com.example.application.data.entity.Company;
import com.example.application.data.entity.CompanyActivity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CompanyActivityRepository extends CrudRepository<CompanyActivity,Long>, JpaRepository<CompanyActivity,Long> {
    List<CompanyActivity> findByCompanyContaining(Company company);

    @Query(value = "SELECT * " +
            "FROM COMPANY " +
            "INNER JOIN COMPANY_ACTIVITY " +
            "ON COMPANY.SYSTEM_USER_ID  = :userId AND COMPANY_ACTIVITY.COMPANY_ID = COMPANY.ID" , nativeQuery = true)
    List<CompanyActivity> listByUserId(@Param("userId") Long userId);

    void deleteByCompany(Company company);


   /* @Query("select c from CompanyActivity c " +
            "where lower(c.company) like lower(concat('%', :searchTerm, '%')) " +
            "or lower(c.company) like lower(concat('%', :searchTerm, '%'))")
    List<CompanyActivity> search(@Param("searchTerm") String searchTerm);
    */

}
