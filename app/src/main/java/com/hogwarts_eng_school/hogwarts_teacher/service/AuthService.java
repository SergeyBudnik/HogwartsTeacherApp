package com.hogwarts_eng_school.hogwarts_teacher.service;

import com.annimon.stream.Optional;
import com.hogwarts_eng_school.hogwarts_teacher.dao.AuthDao;
import com.hogwarts_eng_school.hogwarts_teacher.data.AppUserInfo;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;

@EBean
public class AuthService {
    @Bean
    AuthDao authDao;

    public Optional<AppUserInfo> getUserInfo() {
        return Optional.ofNullable(authDao.getUserInfo());
    }

    public void setUserInfo(AppUserInfo appUserInfo) {
        authDao.setUserInfo(appUserInfo);
    }

    public void clearAuthInfo() {
        authDao.setUserInfo(null);
    }
}
