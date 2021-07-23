package com.example.application.bootstrap;

import com.example.application.data.entity.Company;
import com.example.application.data.entity.CompanyActivity;
import com.example.application.data.entity.SystemUser;
import com.example.application.data.services.CompanyActivityService;
import com.example.application.data.services.CompanyService;
import com.example.application.data.services.SystemUserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class BootStrapData implements CommandLineRunner {


    private final CompanyService companyService;
    private final SystemUserService systemUserService;
    private final CompanyActivityService companyActivityService;

    public BootStrapData(CompanyService companyService, SystemUserService systemUserService, CompanyActivityService companyActivityService) {
        this.companyService = companyService;
        this.systemUserService= systemUserService;
        this.companyActivityService = companyActivityService;
    }

    @Override
    public void run(String... args) throws Exception {

        System.out.println("Bootstrap Run!");

        //SystemUser systemUser1 = new SystemUser("user1", "123");
        SystemUser systemUser2 = new SystemUser("mertfidan2@trakya.edu.tr", "123");

        //systemUserService.save(systemUser1);
        systemUserService.save(systemUser2);



        Company company1 = new Company();
        company1.setFirmaAdi("ÖRNEK DATA1");
        company1.setAdres("ÖRNEK DATA");
        company1.setVergiDairesi("ÖRNEK DATA");
        company1.setVergiNo(123213);
        company1.setPhoneNumber(123213);
        company1.setSystemUser(systemUser2);

        /*
        Company company2 = new Company();
        company2.setFirmaAdi("ÖRNEK DATA2");
        company2.setAdres("ÖRNEK DATA");
        company2.setVergiDairesi("ÖRNEK DATA");
        company2.setVergiNo(123213);
        company2.setPhoneNumber(123213);
        company2.setSystemUser(systemUser2);

        Company company3 = new Company();
        company3.setFirmaAdi("ÖRNEK DATA3");
        company3.setAdres("ÖRNEK DATA");
        company3.setVergiDairesi("ÖRNEK DATA");
        company3.setVergiNo(123213);
        company3.setPhoneNumber(123213);
        company3.setSystemUser(systemUser2);


        Company company4 = new Company();
        company4.setFirmaAdi("ÖRNEK DATA4");
        company4.setAdres("ÖRNEK DATA");
        company4.setVergiDairesi("ÖRNEK DATA");
        company4.setVergiNo(123213);
        company4.setPhoneNumber(123213);
        company4.setSystemUser(systemUser2);


*/
        companyService.save(company1);
        //companyService.save(company2);
        //companyService.save(company3);
        //companyService.save(company4);


        CompanyActivity companyActivity1 = new CompanyActivity();
        companyActivity1.setBorc(300);
        companyActivity1.setAlacak(200);
        companyActivity1.setTarih(LocalDate.of(2021, 2, 21));
        companyActivity1.setAciklama("Örnek Açıklama");
        companyActivity1.setCompany(company1);
        companyActivityService.save(companyActivity1);
/*
        CompanyActivity companyActivity2 = new CompanyActivity();
        companyActivity2.setBorc(370);
        companyActivity2.setAlacak(410);
        companyActivity2.setTarih(LocalDate.of(2021, 3, 25));
        companyActivity2.setAciklama("Örnek Açıklama");
        companyActivity2.setCompany(company2);
        companyActivityService.save(companyActivity2);


        CompanyActivity companyActivity3 = new CompanyActivity();
        companyActivity3.setBorc(1000);
        companyActivity3.setAlacak(1000);
        companyActivity3.setTarih(LocalDate.of(2021, 5, 12));
        companyActivity3.setAciklama("Örnek Açıklama");
        companyActivity3.setCompany(company3);
        companyActivityService.save(companyActivity3);

        CompanyActivity companyActivity4 = new CompanyActivity();
        companyActivity4.setBorc(100);
        companyActivity4.setAlacak(100);
        companyActivity4.setTarih(LocalDate.of(2021, 6, 1));
        companyActivity4.setAciklama("Örnek Açıklama");
        companyActivity4.setCompany(company4);
        companyActivityService.save(companyActivity4);
*/
    }
}