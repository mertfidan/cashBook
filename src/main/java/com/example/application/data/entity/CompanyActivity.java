package com.example.application.data.entity;

import com.example.application.data.entity.AbstractEntity;
import com.example.application.data.entity.Company;
import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
@Data
public class CompanyActivity extends AbstractEntity {
    //@Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    //private Long id;


    private LocalDate tarih;
    @NotNull
    @NotEmpty
    private String aciklama;
    @NotNull
    private Integer borc;
    @NotNull
    private Integer alacak;
    //private String guncelBakiyeToplam;



    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Company company;

    public CompanyActivity(){

    }

    public CompanyActivity(Integer borc,Integer alacak,Company company,LocalDate localDate){

        this.alacak=borc;
        this.borc=alacak;
        this.company=company;
        this.tarih=localDate;


    }

    public Integer getTotalAmount() {
        return alacak - borc;
    }

}
