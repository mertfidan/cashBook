package com.example.application.data.services;

import com.example.application.data.entity.SystemUser;
import com.example.application.data.repositories.SystemUserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SystemUserImpl implements com.example.application.data.services.SystemUserService {

    private final SystemUserRepository systemUserRepository;

    public SystemUserImpl(SystemUserRepository systemUserRepository) {
        this.systemUserRepository = systemUserRepository;
    }

    @Override
    public SystemUser login(String email, String password) {
        List<SystemUser> result = systemUserRepository.findByEmailAndPassword(email,password);
        if (result.size()==0){
            return new SystemUser();
        }
        return result.get(0);

    }

    @Override
    public SystemUser save(SystemUser systemUser) {
        return systemUserRepository.save(systemUser);
    }
}