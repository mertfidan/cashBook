package com.example.application.data.repositories;

import com.example.application.data.entity.SystemUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface SystemUserRepository extends CrudRepository<SystemUser,Long>, JpaRepository<SystemUser,Long> {

    List<SystemUser> findByEmailAndPassword(String email, String password);

}
