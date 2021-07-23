package com.example.application.data.services;

import com.example.application.data.entity.SystemUser;

public interface SystemUserService {
    SystemUser login(String email,String password);
    SystemUser save(SystemUser systemUser);

}
