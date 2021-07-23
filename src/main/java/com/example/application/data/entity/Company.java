package com.example.application.data.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Formula;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Company extends AbstractEntity {

    //@Id
    //@GeneratedValue(strategy = GenerationType.AUTO)
    //private Long id;

    @NotNull
    @NotEmpty
    private String firmaAdi;
    @NotNull
    @NotEmpty
    private String adres;
    @NotNull
    @NotEmpty
    private String vergiDairesi;
    @NotNull
    private Integer vergiNo;

    @NotNull
    private Integer phoneNumber;

    @Formula(" (select sum(company_activity.alacak - company_activity.borc) from company_activity where company_activity.company_id = id) ")
    private Integer bakiye = 0;

    @Formula(" (select sum(company_activity.borc) from company_activity where company_activity.company_id = id) ")
    private Integer toplamBorc=0;

    @Formula(" (select sum(company_activity.alacak) from company_activity where company_activity.company_id = id) ")
    private Integer toplamAlacak=0;


    @ManyToOne
    private SystemUser systemUser;

    @Override
    public String toString() {
        return "Company{" +
                // "id=" + id +
                ", firmaAdi='" + firmaAdi + '\'' +
                ", adres='" + adres + '\'' +
                ", vergiDairesi='" + vergiDairesi + '\'' +
                ", vergiNo='" + vergiNo + '\'' +
                ", bakiye=" + bakiye +
                ", toplamBorc=" + toplamBorc +
                ", toplamAlacak=" + toplamAlacak +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", systemUser=" + systemUser +
                '}';
    }
}
