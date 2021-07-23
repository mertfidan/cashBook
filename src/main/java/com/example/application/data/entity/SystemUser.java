package com.example.application.data.entity;


import com.example.application.data.entity.AbstractEntity;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;

@Entity
@Data
@NoArgsConstructor
//@AllArgsConstructor
public class SystemUser extends AbstractEntity {

    //@Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    //private Long id;
    private String email;
    private String password;

    public SystemUser(String email,String password){

        this.email=email;
        this.password=password;
    }

}
